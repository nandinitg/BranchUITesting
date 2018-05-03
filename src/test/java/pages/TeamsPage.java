package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TeamsPage extends BasePage {

	// *********Constructor*********
	public TeamsPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	// *********Page Methods*********

	public List<WebElement> readTeamNames(String tagname) {
		List<WebElement> teamlist;
		teamlist = findEelementsByTagName(tagname);
		return teamlist;
	}

}
