
package tests;

//import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.TeamsPage;

//import utils.TestListener;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.log4j.xml.DOMConfigurator;

//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import utils.ExtentTestManager;
//import utils.TestListener;

//@Listeners({ TestListener.class })
public class TeamDataTest extends BaseTest {

	String teamBottomLink = "/html/body/div/footer/div/div/div[2]/div[1]/div[2]/ul/li[1]/ul/li[2]/a";
	boolean navigateToBranch = false;
	Logger Log = Logger.getLogger(Logger.class.getName());
	Map<String, LinkedHashMap<String, String>> depNameTitleMap = null;
	// LinkedHashMap<String, String> allEmployees = depNameTitleMap.get("ALL");

	@Test(priority = 0, description = "Verification of the number of employees in All tab against sum of the employees in departments")
	public void validateEmployeeCountsInAllandDepts() throws InterruptedException {

		// ExtentReports Description
		// ExtentTestManager.getTest().setDescription("Validation of the employees in
		// All tab against sum of the employees in departments.");

		if (depNameTitleMap == null)
			depNameTitleMap = getEmployeeDeptData();
		LinkedHashMap<String, String> allEmployees = depNameTitleMap.get("ALL");
		int numEmployees = 0;
		for (String teamName : depNameTitleMap.keySet()) {
			System.out.println(teamName + " : " + String.valueOf(depNameTitleMap.get(teamName).size()));
			if (!teamName.equals("ALL"))
				numEmployees += depNameTitleMap.get(teamName).size();
			else {
				System.out.println("ignoring ALL");
			}
		}
		System.out.println(numEmployees);
		System.out.println(allEmployees.size());

		Reporter.log("Verification of employees under All and sum of all the employees in individual departments");
		assertTrue(numEmployees == allEmployees.size());
	}

	@Test(priority = 1, description = "Finding the employees who are mentioned in All tab, but not present in any of the departments")
	public void findMissingEmployees() throws InterruptedException {
		LinkedHashMap<String, String> allEmployees = depNameTitleMap.get("ALL");
		for (String allEmployee : allEmployees.keySet()) {
			Boolean found = false;
			for (String teamName : depNameTitleMap.keySet()) {
				if (!teamName.equals("ALL")) {
					if (depNameTitleMap.get(teamName).containsKey(allEmployee)) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				Reporter.log(
						"The following employee is  missing  in the individual department tabs, but is present in All tab"
								+ allEmployee);
				System.out.println("missing employee : " + allEmployee);
				System.out.println("missing employee title : " + allEmployees.get(allEmployee));
			}
		}

	}

	@Test(priority = 2, description = "Verifying no employee is repeated under two or more departments")
	public void testNoCommonEmployeesBetweenDepartments() throws InterruptedException {

		// ExtentReports Description
		// ExtentTestManager.getTest().setDescription("Verifying no employee is repeated
		// under two or more departments");

		if (depNameTitleMap == null)
			depNameTitleMap = getEmployeeDeptData();

		for (String teamName : depNameTitleMap.keySet()) {
			if (!teamName.equals("ALL")) {
				for (String otherTeamName : depNameTitleMap.keySet()) {
					if (!otherTeamName.equals("ALL") && !otherTeamName.equals(teamName)) {
						HashSet<String> tmp = new HashSet<String>(depNameTitleMap.get(otherTeamName).keySet());
						tmp.retainAll(depNameTitleMap.get(teamName).keySet());
						Reporter.log("Verifying if an employee is mentioned in two or more departments");
						assertTrue(tmp.isEmpty());
					}
				}
			}
		}
	}

	@Test(priority = 3, description = "Verifying that employee names and titles/departments match between All tab and individual departments")
	public void testEmployeeNamesAndTitleInEachDeptArePresentInAllEmployeesTab() throws InterruptedException {
		// ExtentReports Description
		// ExtentTestManager.getTest().setDescription("Verify that employee names and
		// titles/departments match between All tab and individual departments");

		if (depNameTitleMap == null)
			depNameTitleMap = getEmployeeDeptData();

		LinkedHashMap<String, String> allEmployees = depNameTitleMap.get("ALL");
		Set<String> allEmployeesNames = allEmployees.keySet();
		for (String teamName : depNameTitleMap.keySet()) {
			if (!teamName.equals("ALL")) {
				LinkedHashMap<String, String> teamMemberTitleMap = depNameTitleMap.get(teamName);
				for (String employeeName : teamMemberTitleMap.keySet()) {
					Reporter.log("Verifying that employee name matches between All tab and the department tab");
					assertTrue(allEmployeesNames.contains(employeeName));
					Reporter.log("Verifying that employee title/dept matches between All tab and the department tab");
					assertEquals(allEmployees.get(employeeName), teamMemberTitleMap.get(employeeName));
				}
			}
		}
	}

	private Map<String, LinkedHashMap<String, String>> getEmployeeDeptData() throws InterruptedException {
		TeamsPage teamsPage = new TeamsPage(driver, wait);
		HomePage homePage = new HomePage(driver, wait);
		homePage.navigateToBranchWebsite();

		driver.findElement(By.xpath(teamBottomLink)).click();
		// Maximize Window
		driver.manage().window().maximize();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// (departname,(employee name, title))
		Map<String, LinkedHashMap<String, String>> depNameTitleMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		for (int k = 1; k <= 8; k++) {
			WebElement teamlink = driver
					.findElement(By.xpath("/html/body/div/div/section[2]/div/div[1]/ul/li[" + k + "]"));
			String teamName = teamlink.getText();

			teamlink.click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			List<WebElement> teamList = teamsPage.readTeamNames("h2");

			int teamsize;
			teamsize = teamList.size();
			List<String> teamNamesList = new ArrayList<String>();
			int actualteamsize = 0;

			// employee dept
			List<WebElement> deptList = teamsPage.readTeamNames("h4");

			List<String> teamDepList = new ArrayList<String>();
			LinkedHashMap<String, String> nameDepMap = new LinkedHashMap<String, String>();
			int actualdepsize = 0;

			for (int i = 0; i < teamsize; i++) {
				WebElement teamperson = teamList.get(i);
				WebElement depperson = deptList.get(i);
				if (teamperson.getText().trim().length() > 0) {
					teamNamesList.add(teamperson.getText());
					teamDepList.add(depperson.getText());
					nameDepMap.put(teamperson.getText(), depperson.getText());
				}
			}
			depNameTitleMap.put(teamName, nameDepMap);
			actualteamsize = teamNamesList.size();
			System.out.println("The total teamsize is: " + actualteamsize);

			System.out.println("The team members are");
			System.out.println(teamNamesList);

			actualdepsize = teamDepList.size();
			System.out.println("The total depsize is: " + actualdepsize);

			System.out.println("The team depts are");
			System.out.println(teamDepList);
		}
		for (String teamName : depNameTitleMap.keySet()) {
			System.out.println(teamName);
		}
		return depNameTitleMap;
	}

}