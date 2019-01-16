package uk.gov.service.bluebadge.test.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class ManageLocalAuthoritiesSteps {

  @Autowired protected SiteSteps siteSteps;
  @Autowired protected DatabaseSteps databaseSteps;
  @Autowired protected SitePage sitePage;
  @Autowired protected ScenarioContext scenarioContext;

  @When("^I click on the first name link from local authorities table$")
  public void iClickOnTheFirstNameLinkFromUsersTable() throws Throwable {
    sitePage.findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(1)>a").click();
  }

  @And("^I type different service signpost url \"([^\"]+)\"$")
  public void andITypeDifferentServiceSignpostUrl(String differentServiceSignpostUrl)
      throws Throwable {
    sitePage.findPageElementById("differentServiceSignpostUrl").clear();
    sitePage
        .findPageElementById("differentServiceSignpostUrl")
        .sendKeys(differentServiceSignpostUrl);
  }
}
