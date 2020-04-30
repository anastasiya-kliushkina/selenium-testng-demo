package utilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class TestInit {
	
	public String buildTag = System.getenv("BUILD_TAG");

	public String key = System.getenv("TESTINGBOT_KEY");

	public String secret = System.getenv("TESTINGBOT_SECRET");

	//used to perform browser interactions
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	//contains the WebDriver SessionId
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();

	// sets the device combinations to be used
	@DataProvider(name = "listOfDevices", parallel = true)
	public static Object[][] testingbotDeviceDataProvider(Method testMethod) {
		return new Object[][]{
			//new Object[]{"android", "9.0", "Chrome", "Galaxy S9", "android" },
			new Object[]{"android", "7.1", "Chrome", "Pixel 2", "android" },
			new Object[]{"iOS", "12.2", "safari", "iPhone Xs Max", "iOS"},
		};
	}

	//returns the {@link WebDriver} for the current thread
	public WebDriver getWebDriver() {
		return webDriver.get();
	}
	
	//return the SessionId for the current thread
	public String getSessionId() {
		return sessionId.get();
	}

	/**
	 * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the browser,
	 * version and os parameters, and which is configured to run against hub.testingbot.com, using
	 * the key and secret populated by the {@link #authentication} instance.
	 *
	 * @param browser Represents the browser to be used as part of the test run.
	 * @param version Represents the version of the browser to be used as part of the test run.
	 * @param os Represents the operating system to be used as part of the test run.
	 * @param methodName Represents the name of the test case that will be used to identify the test on TestingBot
	 * @return
	 * @throws MalformedURLException if an error occurs parsing the url
	 */
	//@BeforeMethod
	protected void createDriver(String platform, String platformVersion, String browserName, String deviceName, String platformName, String methodName)
			throws MalformedURLException, UnexpectedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("platform", platform); // iOS
		capabilities.setCapability("version", platformVersion); // 12.1
		capabilities.setCapability("browserName", browserName); // safari
		capabilities.setCapability("deviceName", deviceName); // iPhone X
		capabilities.setCapability("platformName", platformName); // iOS
		capabilities.setCapability("name", methodName); // @Test method name		
		
		if (buildTag != null) {
			capabilities.setCapability("build", buildTag);
		}
		// Launch remote browser and set it as the current thread
		
		webDriver.set(new RemoteWebDriver(
				new URL("https://" + key + ":" + secret + "@hub.testingbot.com/wd/hub"),
				capabilities));
		
		// set current sessionId
		String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
		sessionId.set(id);	
		
		// open URL
		String testURL = System.getProperty("testURL");
		WebDriver driver = getWebDriver();		
		driver.get(testURL);
				
	}
	
	// dumps browser log and closes the browser
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception {
		
		if (webDriver.get() != null) {
			try {
				((JavascriptExecutor) webDriver.get()).executeScript("tb:test-result=" + (result.isSuccess() ? "passed" : "failed"));
			} catch (Exception e) {
			//	System.out.println(e.toString());
			}
			
			System.out.println(result.isSuccess());
			webDriver.get().quit();	
		}
	}

	protected void annotate(String text) {
		if (webDriver.get() != null) {
			((JavascriptExecutor) webDriver.get()).executeScript("tb:test-context=" + text);
		}
	}

}
