package tests;

//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;

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

public class HomePageTest extends TestInit {
	
	SoftAssert sa = new SoftAssert();	

	@Test(dataProvider = "listOfDevices")
	public void testRevenueCentersList(String platform, String version, String browserName, String deviceName, String platformName, Method method) throws Exception {
		
		this.createDriver(platform, version, browserName, deviceName, platformName, method.getName());
		
		WebDriver driver = this.getWebDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);	
				
		// Home page - list of RVCs
		HomePage homePage = new HomePage(driver);
		RevenueCenterPage revenueCenterPage = new RevenueCenterPage(driver); // list of RVC menus	
		
		// Skip the lines is displayed
		WebElement headerH2 = HomePage.headerH2;
		wait.until(ExpectedConditions.visibilityOf(headerH2));
		sa.assertTrue(homePage.isInitialized()); 

		// Test Stand is visible
		WebElement testStand = homePage.getTestStand();
		wait.until(ExpectedConditions.visibilityOf(testStand));
		sa.assertTrue(testStand.isDisplayed()); 
		
		// Test stand menus page is displayed after click on Test stand
		homePage.clickLocationStand(testStand);
		WebElement backToAllStandsBtn = revenueCenterPage.backToAllStandsBtn;
		wait.until(ExpectedConditions.visibilityOf(backToAllStandsBtn));
		sa.assertTrue(revenueCenterPage.isInitialized());
		
		// Stand description is displayed properly
		revenueCenterPage.clickBackToAllStandsBtn();
		wait.until(ExpectedConditions.visibilityOf(testStand));
		sa.assertTrue(testStand.isDisplayed());
		
		WebElement standShortDescription = homePage.getStandShortDescription("5");
		sa.assertEquals(standShortDescription.getText().toLowerCase(), "pickup near section 110 • delivery to sections 110-115");
		
		
		sa.assertAll();
	}

}
