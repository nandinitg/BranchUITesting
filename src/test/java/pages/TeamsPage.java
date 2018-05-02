package pages;

import java.lang.reflect.Array;
	import java.util.List;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;

	public class TeamsPage extends BasePage{

	    //*********Constructor*********
	    public TeamsPage(WebDriver driver, WebDriverWait wait) {
	        super(driver, wait);
	    }

	    //*********Web Elements*********
	    String usenameId = "email";
	    String passwordId = "password";
	    String loginButtonId = "loginButton";
	    String errorMessageUsernameXpath = "//*[@id=\"loginForm\"]/div[1]/div/div";
	    String errorMessagePasswordXpath = "//*[@id=\"loginForm\"]/div[2]/div/div ";
	    

	    //*********Page Methods*********

	    public List<WebElement> readTeamNames(String tagname) {
	    	List<WebElement> teamlist;
	    	teamlist = findEelementsByTagName(tagname);
	    	return teamlist;
	    }

	    

		
	    }





