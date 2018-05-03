package pages;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import pages.BasePage;

public class HomePage extends BasePage{

   //*********Constructor*********
    public HomePage (WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    
   
    //*********Page Variables*********
    String baseURL = "https://branch.io/";

    //*********Web Elements*********
    //String signInButtonClass = "btnSignIn";
    String googleSearchTextBoxId = "q";
    String googleResultsstats = "resultStats";
    String branchLink = "https://branch.io/";
    String branchWebsiteTitle="Branch - A mobile linking platform powering deep links and mobile attribution.";
    String branchWebsiteText= "The mobile marketing and linking platform to supercharge your app growth and seamlessly plug into your marketing stack.";
    String branchWebsiteSectionId = "hp-solutions";
    String cookieAlertId = "CybotCookiebotDialogBodyButtonAccept";

    String pricingLinkXpath = "/html/body/div/header/nav/div[2]/header/div[1]/ul/li[2]/a[1]";
    String pricingTitle = "Pricing - Deep Linking Products - Branch Metrics";
    String whyBranchLinkXpath = "/html/body/div/header/nav/div[2]/header/div[1]/ul/li[2]/a[2]";
    String whyBranchTitle = "How Branch links work";
    String signInLinkXpath = "/html/body/div/header/nav/div[2]/header/div[1]/ul/li[1]/a[1]/p";
    String signInPageTitle = "Dashboard - Branch Metrics";
    String developersLinkXpath = "/html/body/div/header/nav/div[2]/header/div[1]/ul/li[1]/a[2]";
    String developersTitle = "Branch documentation";
    String learnMoreAttributionXpath = "//*[@id=\"hp-solutions\"]/div[1]/div[3]/div[2]/p[3]/a";
    String learnMoreAttributionTitle = "People-Based Attribution - More than Measurement | Branch";
    String learnMoreDeeplinksLinkXpath = "//*[@id=\"hp-solutions\"]/div[1]/div[2]/div[1]/p[3]/a";
    String learnMoreDeeplinksTtitle="How Branch links work";
 
    //*********Page Methods*********

    //Go to Branch website
    public boolean navigateToBranchWebsite () throws InterruptedException {
    	BasePage basePage = new BasePage(driver, wait);
    	driver.get("https://www.google.com");
    	Logger Log = Logger.getLogger(Logger.class.getName());
    	 
    	basePage.writeText(By.name(googleSearchTextBoxId), "branch.io website");
    	basePage.pressEnterKey(By.name(googleSearchTextBoxId));
    	basePage.waitUntilElementLoads(googleResultsstats);
    	
    	 driver.findElement(By.linkText("Branch.io")).click();
    	 String actualTitle = driver.getTitle();
    		System.out.println("TITLE IS "+actualTitle);
    		assertEquals(actualTitle, branchWebsiteTitle);
    		
    		basePage.waitUntilElementLoads(branchWebsiteSectionId);
    		Thread.sleep(20000);
    		
    		
    		//handle cookie popup
    	  	boolean cookiepresent =  checkForCookiePopup(cookieAlertId);
    		if (cookiepresent)
    			driver.findElement(By.id(cookieAlertId)).click();
    	
    		boolean isTheTextPresent = driver.getPageSource().contains(branchWebsiteText);
    		if (actualTitle==branchWebsiteTitle && isTheTextPresent) {
    		   System.out.println("Branch website launched");
    		   return true;
    		}
    		else 
    			return false;
        }
    
	private boolean checkForCookiePopup(String cookieAlertId) throws InterruptedException {
		try {
			if (driver.findElement(By.id(cookieAlertId)).isDisplayed())
				return true;
			else
				return false;
		} catch (NoSuchElementException exception) {
			exception.printStackTrace();
			return false;
		} catch (StaleElementReferenceException exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
     private void verifyNavigation(String linkXpath, String expectedTitle) throws InterruptedException {
    	 BasePage basePage = new BasePage(driver, wait);
    	 driver.findElement(By.xpath(linkXpath)).click();
   	  	String actualTitle = driver.getTitle();
   	  	assertEquals(actualTitle, expectedTitle);
 		driver.get("https://branch.io/");
 		basePage.waitUntilElementLoads(branchWebsiteSectionId);
 	 }
    
     public void verifyLinks () throws InterruptedException {
    	 verifyNavigation(pricingLinkXpath,pricingTitle);
    	 verifyNavigation(whyBranchLinkXpath,whyBranchTitle);
    	 verifyNavigation(signInLinkXpath,signInPageTitle);
    	 verifyNavigation(developersLinkXpath,developersTitle);
    	 verifyNavigation(learnMoreAttributionXpath,learnMoreAttributionTitle);
    	 verifyNavigation(learnMoreDeeplinksLinkXpath,learnMoreDeeplinksTtitle);
     }
     
    }
    	
    	
    
    	
    
    	
    
    
    
   
   


