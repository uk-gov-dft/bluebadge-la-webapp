package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class CancelBadgeSiteSteps {
  private static final Logger log = getLogger(FindABadgeSiteSteps.class);

  @Autowired protected SitePage sitePage;

  @Autowired protected ScenarioContext scenarioContext;

  @And("^I type the badge number of the applicant who previously ordered a badge$")
  public void iTypeTheBadgeNumberOfTheBadgeApplicantWhoPreviouslyAOrdered() {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    log.debug("Badge number to be typed: [()]", badgeNumber);
    sitePage.findElementWithUiPath("searchTerm.field").sendKeys(badgeNumber);
  }

  @Then("^I should see the page title for Cancel badge for that particular badge number$")
  public void iShouldSeeThePageTitleForCancelBadgeForThatParticularBadgeNumber() throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String pageTitle = "Cancel badge " + badgeNumber + " - GOV.UK Manage Blue Badges";
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @When("^I select the \"([^\"]*)\" option$")
  public void iSelectTheOption(String arg0) throws Throwable {
    sitePage.findElementWithUiPath(arg0).click();
  }

  @Then("^I should see the badge cancelled page$")
  public void iShouldSeeTheBadgeCancelledPage() throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String pageTitle = "Badge cancelled " + badgeNumber + " - GOV.UK Manage Blue Badges";

    String uiBadgeNumber = sitePage.findElementWithUiPath("badge.cancelled.num").getText();
    assertThat(uiBadgeNumber, is(badgeNumber));

    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @Then("^I should see the same page with validation errors$")
  public void iShouldSeeTheSamePageWithValidationErrors() throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String pageTitle = "Cancel badge " + badgeNumber + " - GOV.UK Manage Blue Badges";

    Boolean errorExists = sitePage.findElementWithUiPath("reason.summary-error").isDisplayed();

    assertThat(errorExists, is(true));
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @Then("^I should see the badge details page without the \"([^\"]*)\" link button$")
  public void iShouldSeeTheBadgeDetailsPageWithoutTheLinkButton(String arg0) throws Throwable {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    String pageTitle = "Badge details " + badgeNumber + " - GOV.UK Manage Blue Badges";

    assertEquals(sitePage.findElementWithText("Cancel badge"), null);
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }
}
