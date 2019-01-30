package uk.gov.service.bluebadge.test.acceptance.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class OrderBadgeFromApplicationSteps extends AbstractSpringSteps {

  private static final Logger log = getLogger(SiteSteps.class);

  @Autowired protected SitePage sitePage;
  @Autowired protected DatabaseSteps databaseSteps;

  @And("^I can see the \"Order badge\" button$")
  public void iCanSeeOrderBadgeButton() throws Throwable {
      assertNotNull("Can see element", sitePage.findElementWithUiPath("order"));
  }

    @When("^I click the \"Order badge\" button$")
    public void iClickOrderBadgeButton() throws Throwable {
        sitePage.findElementWithUiPath("order").click();
    }

    @Then("^I should see the \"Personal Details\" page where the form is pre-filled with the application's relevant data$")
    public void iShouldSeePersonalDetailsPagePrefilledWithApplicationDetails() throws Throwable {
        assertThat("I should see page titled.", sitePage.getDocumentTitle(), is("Personal Details - GOV.UK Manage Blue Badges"));
    }

  @Before("@OrderBadgeFromApplicationScripts")
  public void executeInsertApplicationsDBScript() throws SQLException {
    databaseSteps.runScript("scripts/new-applications/details/create-application-details.sql");
  }

  @After("@OrderBadgeFromApplicationScripts")
  public void executeDeleteApplicationsDBScript() throws SQLException {
    databaseSteps.runScript("scripts/new-applications/details/delete-application-details.sql");
  }
}
