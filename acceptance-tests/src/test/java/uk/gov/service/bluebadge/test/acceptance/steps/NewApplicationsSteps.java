package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.DbUtils;

public class NewApplicationsSteps extends AbstractSpringSteps {

	private static final Logger log = getLogger(SiteSteps.class);

	@Autowired protected SitePage sitePage;

	@When("^I click on application with name \"([^\"]*)\"$")
	public void iClickOnApplication(String name) throws Throwable {
		List<WebElement> records = sitePage.findElementWithUiPath("table.body")
										  .findElements(By.className("govuk-table__row"));

		List<String> recordsNames = records.stream()
										  .map(r -> r.findElement(By.tagName("a")).getText())
										  .collect(Collectors.toList());

		int recordNumber = recordsNames.indexOf(name);

		WebElement nameLink = sitePage.findElementWithUiPath("table.body")
									 .findElements(By.className("govuk-table__row"))
									 .get(recordNumber)
									 .findElement(By.tagName("a"));
		
		log.debug("Name found: " + nameLink);
		
		nameLink.click();
		
	}

	@Then("^I should see the page title for Application Details for that particular \"([^\"]+)\"$") 
	public void iShouldSeeApplicationDetails (String appId){
		 String displayedId = sitePage.findElementWithUiPath("title").findElement(By.tagName("span")).getText();
		 assertEquals(appId, displayedId);
	}
	
	// hooks
	private Map<String, Object> settings() {
		Map<String, Object> settings = new HashMap<>();

		settings.put("username", "developer");
		settings.put(" ***REMOVED***);
		settings.put("url", "jdbc:postgresql://localhost:5432/bb_dev?currentSchema=applicationmanagement");
		settings.put("driverClassName", "org.postgresql.Driver");

		return settings;
	}

	@Before("@NewApplicationDetailsScripts")
	public void executeInsertApplicationsDBScript() throws SQLException {
		DbUtils db = new DbUtils(settings());
		db.runScript("scripts/new-applications/details/create-application-details.sql");
	}

	@After("@NewApplicationDetailsScripts")
	public void executeDeleteApplicationsDBScript() throws SQLException {
		DbUtils db = new DbUtils(settings());
		db.runScript("scripts/new-applications/details/delete-application-details.sql");
	}
}
