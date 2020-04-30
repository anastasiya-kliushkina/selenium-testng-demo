package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utilities.PageObject;

public class RevenueCenterPage extends PageObject{

	//@FindBy(xpath="//button[@class='navigation-bar back']")
	@FindBy(how = How.CSS, using="div:nth-child(2) div:nth-child(1) div:nth-child(1) div.navigation-bar.back > button.navigation-bar.back")
	public WebElement backToAllStandsBtn;

	WebElement selectedBtn;
	WebElement unselectedBtn;

	@FindBy(how = How.CSS, using="div:nth-child(2) div:nth-child(1) div:nth-child(1) div.menu-header > p.description")
	WebElement standLongDescription;


	public RevenueCenterPage(WebDriver driver) {
		super(driver);
	}

	public boolean isInitialized() {
		return backToAllStandsBtn.isDisplayed();
	}

	public WebElement getSelectedBtn(String btnNumber) {
		String selectedMenuTypeCSS = "div:nth-child(1) div.menu-header div.header div.segmented-control:nth-child(2) > span.segment.selected:nth-child(" + btnNumber + ")";
		selectedBtn = driver.findElement(By.cssSelector(selectedMenuTypeCSS));
		return selectedBtn;
	}

	public WebElement getUnselectedBtn(String btnNumber) {
		String unselectedMenuTypeCSS = "div:nth-child(1) div.menu-header div.header div.segmented-control:nth-child(2) > span.segment:nth-child("+ btnNumber + ")";
		unselectedBtn = driver.findElement(By.cssSelector(unselectedMenuTypeCSS));		
		return unselectedBtn;
	}
	
	public WebElement getstandLongDescription() {
		return standLongDescription;
	}
	
	public void clickBackToAllStandsBtn() {
		backToAllStandsBtn.click();
	}
}
