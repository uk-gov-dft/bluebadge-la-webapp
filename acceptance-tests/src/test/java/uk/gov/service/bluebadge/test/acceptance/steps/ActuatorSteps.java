package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.*;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.ActuatorPage;

public class ActuatorSteps {

  @Autowired private ActuatorPage actuatorPage;

  @Given("^I navigate to the actuator's \"([^\"]*)\" page$")
  public void iNavigateToTheActuatorSPage(String pageName) throws Throwable {
    actuatorPage.openByPageName(pageName);
  }

  @Then("^I should not see actuator's error page$")
  public void iShouldNotSeeActuatorSErrorPage() throws Throwable {
    assert (!actuatorPage.getPageContent().contains("\"status\":404,\"error\":\"Not Found\""));
  }

  @And("^I should see status as \"([^\"]*)\"$")
  public void iShouldSeeStatusAs(String status) throws Throwable {
    assert (actuatorPage.getPageContent().contains("{\"status\":\"UP\"}"));
  }

  @And("^I should see status code as (\\d+)$")
  public void iShouldSeeStatusCodeAs(int status) throws Throwable {
    assertTrue("Invalid Status code", (status == actuatorPage.getStatusCode()));
  }
}
