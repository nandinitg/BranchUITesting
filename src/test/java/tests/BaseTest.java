package tests;

import java.net.MalformedURLException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import java.net.URL;
import java.util.HashMap;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;

	@BeforeClass // Change this to @BeforeMethod for parallel execution of test cases with
					// selenium grid
	@Parameters({ "browsername" })
	public void setupTest(String browser) throws MalformedURLException {
		
	//Configs for executing the test suite on different platform like Mac/Linux and different browsers like Chrome/safari
		
				/*
				 * String Node = "http://192.168.0.15:5555/wd/hub"; //Desired Capabilities
				 * DesiredCapabilities caps = DesiredCapabilities.chrome();
				 * caps.setCapability("browserName", browser); caps.setCapability("platform",
				 * Platform.MAC); caps.setCapability("version", "66.0.3359.139"); driver = new
				 * RemoteWebDriver(new URL(Node),caps);
				 */
		

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		HashMap<String, Integer> prefs = new HashMap<String, Integer>();
		prefs.put("profile.default_content_settings.cookies", 2);
		prefs.put("profile.block_third_party_cookies", 2);

		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);

		// Create a wait. All test methods use this.
				wait = new WebDriverWait(driver, 15);
	    // Maximize Window
				driver.manage().window().maximize();
	}

	@AfterClass // Change this to @AfterMethod for parallel execution of test cases with
					// selenium grid
	public void teardown() {
		driver.quit();
	}

	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver", "/Users/Nandini/Documents/Workspace-MyTest/Drivers/chromedriver");
	}

}
