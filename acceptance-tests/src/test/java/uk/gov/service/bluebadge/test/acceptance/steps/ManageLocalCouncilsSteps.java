package uk.gov.service.bluebadge.test.acceptance.steps;

import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class ManageLocalCouncilsSteps {

  @Autowired protected SitePage sitePage;
  @Autowired protected ScenarioContext scenarioContext;

  @When("^I click on the first name link from local councils table$")
  public void iClickOnTheFirstNameLinkFromLocalCouncilsTable() {
    sitePage.findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(1)>a").click();
  }
}
