package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import java.sql.SQLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class ApplicationSiteSteps {

  private static final Logger log = getLogger(BadgeDetailsSiteSteps.class);

  @Autowired protected SitePage sitePage;
  @Autowired protected DatabaseSteps databaseSteps;

  @Autowired protected ScenarioContext scenarioContext;

  @Given("^I click on the first application of the table$")
  public void iClickOnTheApplicationNumberOfTheFirstResult() {
    WebElement itemLink =
        sitePage
            .findElementWithUiPath("table.body")
            .findElement(By.cssSelector("tr.govuk-table__row td.govuk-table__cell:first-child a"));
    String href = itemLink.getAttribute("href");
    String[] linkParts = href.split("/");
    String applicationId = linkParts[linkParts.length - 1];
    scenarioContext.setContext("applicationId", applicationId);
    log.debug("save to scenario context applicationId", applicationId);
    itemLink.click();
  }

  @And("^I should see the page title for Application Details for that particular application id$")
  public void iShouldSeeThePageTitleForApplicationDetailsForThatParticularApplicationId() {
    String applicationId = (String) scenarioContext.getContext("applicationId");
    log.debug("read from scenario context applicationId: ", applicationId);
    assertThat(
        "I should see page titled.",
        sitePage.getDocumentTitle(),
        is("Application details " + applicationId + " - GOV.UK Manage Blue Badges"));
  }

  @Before("@DeleteApplication")
  public void executeInsertApplicationsForDeletionDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/create-application-details-for-deletion.sql");
  }

  @After("@DeleteApplication")
  public void executeDeleteApplicationsForDeletionDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/delete-application-details-for-deletion.sql");
  }

  @Before("@UpdateApplication")
  public void executeInsertApplicationsForUpdateDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/create-application-details-for-update.sql");
  }

  @After("@UpdateApplication")
  public void executeDeleteApplicationsForUpdateDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/delete-application-details-for-update.sql");
  }

  @Before("@TransferApplication")
  public void executeInsertApplicationsForTransferDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/create-application-details-for-transfer.sql");
  }

  @After("@TransferApplication")
  public void executeDeleteApplicationsForTransferDBScript() throws SQLException {
    databaseSteps.runScript(
        "scripts/new-applications/details/delete-application-details-for-transfer.sql");
  }
}
