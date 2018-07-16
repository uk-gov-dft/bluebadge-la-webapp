package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class FindABadgeSiteSteps {

  private static final Logger log = getLogger(FindABadgeSiteSteps.class);

  @Autowired protected SitePage sitePage;

  @Autowired protected ScenarioContext scenarioContext;

  @And("^I type the badge number of the badge previously ordered$")
  public void iTypeTheBadgeNumberOfTheBadgePreviouslyOrdered() {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    log.debug("Badge number to be typed: [()]", badgeNumber);
    sitePage.findElementWithUiPath("searchTerm.field").sendKeys(badgeNumber);
  }
}
