package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class ReplaceBadgeSiteSteps {
  @Autowired protected SitePage sitePage;
  @Autowired protected DatabaseSteps databaseSteps;
  @Autowired protected ScenarioContext scenarioContext;

  @And("^I type the badge number \"([^\"]+)\"$")
  public void iTypeTheBadgeNumber(String badgeNumber) {
    scenarioContext.setContext("badgeNumber", badgeNumber);
    sitePage.findElementWithUiPath("searchTermBadgeNumber.field").sendKeys(badgeNumber);
  }

  @Then("^I should see the page title for Replace badge for that particular badge number$")
  public void iShouldSeeThePageTitleForReplaceBadge() throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String pageTitle = "Replace badge " + badgeNumber + " - GOV.UK Manage Blue Badges";
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @Then("^I should see the badge replaced page$")
  public void iShouldSeeTheBadgeReplacedPage() throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String uiBadgeNumber = sitePage.findElementWithUiPath("badge.replaced.num").getText();
    String pageTitle = "Replacement Ordered " + uiBadgeNumber + " - GOV.UK Manage Blue Badges";

    assertThat(uiBadgeNumber, not(badgeNumber));

    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }
}
