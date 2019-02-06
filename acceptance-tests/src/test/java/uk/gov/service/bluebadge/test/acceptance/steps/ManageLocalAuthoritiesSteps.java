package uk.gov.service.bluebadge.test.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class ManageLocalAuthoritiesSteps {

  @Autowired protected SitePage sitePage;
  @Autowired protected ScenarioContext scenarioContext;

  @When("^I click on the first name link from local authorities table$")
  public void iClickOnTheFirstNameLinkFromLocalAuthoritiesTable() {
    sitePage.findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(1)>a").click();
  }

  @And("^I type on field \"([^\"]+)\" value \"([^\"]*)\"$")
  public void andITypeOnFieldValue(String fieldName, String value) {
    sitePage.findPageElementById(fieldName).clear();
    sitePage.findPageElementById(fieldName).sendKeys(value);
  }
}
