package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.HomePage;
import pages.RevenueCenterPage;
import utilities.TestInit;

public class RevenueCenterTest extends TestInit {

	SoftAssert sa = new SoftAssert();

	@Test(dataProvider = "listOfDevices")
	public void testRevenueCenterFBMerchMenu(String platform, String version, String browserName, String deviceName, String platformName, Method method) throws Exception {
		
		this.createDriver(platform, version, browserName, deviceName, platformName, method.getName());
		
		WebDriver driver = this.getWebDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);		

		HomePage homePage = new HomePage(driver); // Home page - list of RVCs
		RevenueCenterPage revenueCenterPage = new RevenueCenterPage(driver); // list of RVC menus	

		WebElement testStand = homePage.getTestStand();
		wait.until(ExpectedConditions.visibilityOf(testStand));
		homePage.clickLocationStand(testStand);

		// Pickup and Delivery buttons are displayed, pickup button is selected by default

		WebElement pickupBtn = revenueCenterPage.getSelectedBtn("1");
		WebElement deliveryBtn = revenueCenterPage.getUnselectedBtn("3");

		sa.assertEquals(pickupBtn.getText().toLowerCase(),"pickup");
		sa.assertEquals(deliveryBtn.getText().toLowerCase(),"delivery");

		// click on Delivery menu type makes Delivery selected
		deliveryBtn.click();
		WebElement deliveryBtn1 = revenueCenterPage.getSelectedBtn("3");
		WebElement pickupBtn1 = revenueCenterPage.getUnselectedBtn("1");

		sa.assertEquals(deliveryBtn1.getText().toLowerCase(),"delivery");

		// verify Long description
		WebElement standLongDescription = revenueCenterPage.getstandLongDescription();
		String text = standLongDescription.getText().toLowerCase();
		sa.assertEquals(text.substring(0, text.indexOf(".")), "delivery to sections 110-115");

		// Long description changes after changing menu type
		pickupBtn1.click();
		WebElement standLongDescription1 = revenueCenterPage.getstandLongDescription();
		String text1 = standLongDescription1.getText().toLowerCase();
		sa.assertEquals(text1.substring(0, text1.indexOf(".")), "pickup near section 110");

		// check pickup menu items


		sa.assertAll();

	}

	@Test(dataProvider = "listOfDevices")
	public void testRevenueCenterPickupMenu(String platform, String version, String browserName, String deviceName, String platformName, Method method) throws Exception {
		this.createDriver(platform, version, browserName, deviceName, platformName, method.getName());

	}

	@Test(dataProvider = "listOfDevices")
	public void testRevenueCenterDeliveryMenu(String platform, String version, String browserName, String deviceName, String platformName, Method method) throws Exception {
		this.createDriver(platform, version, browserName, deviceName, platformName, method.getName());

	}


}
