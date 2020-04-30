package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utilities.PageObject;

public class HomePage extends PageObject {

//	@FindBy(xpath="//h2[contains(text(),'SKIP THE LINES!')]") 
//	public static WebElement headerH2;
	
//	@FindBy(css="div.stands div.menus-list a:nth-child(5) div.menu > div.image:nth-child(2)")
//	WebElement testStand;
	
	@FindBy(how = How.CSS, using="div:nth-child(2) div:nth-child(1) div.stands div.header > h2:nth-child(2)") 
	public static WebElement headerH2;	
	@FindBy(how = How.CSS, using="div.stands div.menus-list a:nth-child(5) div.menu > div.image:nth-child(2)") 
	WebElement testStand;
	@FindBy(how = How.CSS, using="div.stands div.menus-list a:nth-child(5) div.menu > p:nth-child(3)") 
	WebElement standShortDescription;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isInitialized() {
		return headerH2.isDisplayed();
	}
	
	public WebElement getTestStand() {	
		return testStand;
	}
	
//	public WebElement getStandShortDescription() {
//		return standShortDescription;
//	}
	
	public WebElement getStandShortDescription(String standNumber) {
		String standCSS = "div.stands div.menus-list a:nth-child("+ standNumber+ ") div.menu > p:nth-child(3)";
		standShortDescription = driver.findElement(By.cssSelector(standCSS));
		return standShortDescription;
	}
	
	public void clickLocationStand(WebElement stand) { 
		stand.click();
	}
	
}
