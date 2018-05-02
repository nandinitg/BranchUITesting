package pages;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
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

    //*********Page Methods*********

    //Go to Homepage
    public void openUrl (String url){
        driver.get(url);
        }

    //Go to Branch website
    public boolean navigateToBranchWebsite () throws InterruptedException {
    	BasePage basePage = new BasePage(driver, wait);
    	driver.get("https://www.google.com");
    	Logger Log = Logger.getLogger(Logger.class.getName());
    	 
    	basePage.writeText(By.name(googleSearchTextBoxId), "branch.io website");
    	basePage.pressEnterKey(By.name(googleSearchTextBoxId));
    	basePage.waitUntilElementLoads(googleResultsstats);
    	
    	 driver.findElement(By.linkText("Branch.io")).click();
    			 
     	/*List<WebElement> searchResults = basePage.findEelementsByTagName("a");
    	
    	for (WebElement webElement : searchResults)
        {
            
    	
    		System.out.println(webElement.getAttribute("href"));
    		if((String)(webElement.getAttribute("href")) == branchLink)
    		  {
    			System.out.println("FOUND THE BRANCH LINK");
    			webElement.click();}*/
    		//Thread.sleep(10000);
    		
    		String actualTitle = driver.getTitle();
    		System.out.println("TITLE IS "+actualTitle);
    		assertEquals(actualTitle, branchWebsiteTitle);
    		
    		basePage.waitUntilElementLoads(branchWebsiteSectionId);
    		Thread.sleep(50000);
    		
    		
    		//handle cookie popup
    	  	boolean cookiepresent =  checkForCookiePopup(cookieAlertId);
    		if (cookiepresent)
    			driver.findElement(By.id(cookieAlertId)).click();
    		
    	
    		
    		
    		
    	/*	try {
    		   if(driver.findElement(By.id(cookieAlertId)).isDisplayed())
    			  driver.findElement(By.id(cookieAlertId)).click();
    		   System.out.println("inside try");
    		  
    		} catch (NoSuchElementException e) {
    			e.printStackTrace();
    		}*/
  
    		
    		
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
	         if(driver.findElement(By.id(cookieAlertId)).isDisplayed())
	            return true;
	         else return false;
	      }
	      catch (NoSuchElementException exception) {
	    	System.out.println("INSIDE CATCH");
	        return false;
	      }
	      catch (StaleElementReferenceException exception) {
	    	  System.out.println("INSIDE CATCH");
	        return false;
	      }
		}
		
    }
    	
    	
    
    	
    
    	
    
    
    
   
   


