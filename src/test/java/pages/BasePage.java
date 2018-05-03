package pages;

import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public static WebDriver driver;
	public WebDriverWait wait;

	// Constructor
	public BasePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// Click Method
	public void click(By elementLocation) {
		driver.findElement(elementLocation).click();
	}

	// Write Text
	public void writeText(By elementLocation, String text) {
		driver.findElement(elementLocation).sendKeys(text);
	}

	// Read Text
	public String readText(By elementLocation) {
		return driver.findElement(elementLocation).getText();
	}

	public void pressEnterKey(By elementLocation) {
		driver.findElement(elementLocation).sendKeys(Keys.RETURN);
	}

	// Read Text by tag name
	public List<WebElement> findEelementsByTagName(String tagname) {
		/*
		 * List<WebElement> links= driver.findElements(By.tagName("a")); for(int
		 * i=0;i<links.size();i++){ WebElement temp=links.get(i);
		 * System.out.println(temp.getText());
		 * 
		 * 
		 * }
		 */ return driver.findElements(By.tagName(tagname));
	}

	public void waitUntilElementLoads(String elementid) {
		// wait until the element loads
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementid)));
	}

	public WebElement findLinks(String linktext) {
		return driver.findElement(By.linkText(linktext));
	}

}
