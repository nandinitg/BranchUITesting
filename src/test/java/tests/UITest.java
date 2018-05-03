package tests;

import org.testng.annotations.Test;

import pages.HomePage;

public class UITest extends BaseTest {

	@Test(priority = 0, description = "Verify the links in the Homepage")
	public void testVerifyAllLinks() throws InterruptedException {

		HomePage homePage = new HomePage(driver, wait);
		homePage.navigateToBranchWebsite();
		homePage.verifyLinks();
	}

}