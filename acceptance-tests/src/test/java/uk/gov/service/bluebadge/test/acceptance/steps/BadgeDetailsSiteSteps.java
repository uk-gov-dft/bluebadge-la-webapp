package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class BadgeDetailsSiteSteps {

  private static final Logger log = getLogger(BadgeDetailsSiteSteps.class);

  @Autowired protected SitePage sitePage;

  @Autowired protected ScenarioContext scenarioContext;

  @Given("^I have ordered a badge and found it and it is listed in badge search results page$")
  public void IHaveOrderedABadgeAndFoundItAndItIsListedInBadgeSearchResultsPage() {}

  @Given("^I click on the first badge of the table$")
  public void iClickOnTheBadgeNumberOfTheFirstResult() {
    WebElement itemLink =
        sitePage
            .findElementWithUiPath("table.body")
            .findElement(By.cssSelector("tr.govuk-table__row td.govuk-table__cell:first-child a"));
    String href = itemLink.getAttribute("href");
    String[] linkParts = href.split("/");
    String badgeNumber = linkParts[linkParts.length - 1];
    scenarioContext.setContext("badgeNumber", badgeNumber);
    log.debug("save to scenario context badgeNumber", badgeNumber);
    itemLink.click();
  }

  @And("^I type the post code of the applicant who previously ordered a badge$")
  public void iTypeThePostCodeOfTheApplicantWhoPreviouslyOrderedABadge() {
    String postCode = (String) scenarioContext.getContext("postcode");
    log.debug("Post code to be typed: [()]", postCode);
    sitePage.findElementWithUiPath("searchTerm.field").sendKeys(postCode);
  }

  @And("^I should see the page title for Badge Details for that particular badge number$")
  public void iShouldSeeThePageTitleForBadgeDetailsForThatParticularBadgeNumber() {
    String badgeNumber = (String) scenarioContext.getContext("badgeNumber");
    log.debug("read from scenario context badgeNumber", badgeNumber);
    assertThat(
        "I should see page titled.",
        sitePage.getDocumentTitle(),
        is("Badge details " + badgeNumber + " - GOV.UK Manage Blue Badges"));
  }

  @And("^It is a badge for \"(ORGANISATION|PERSON)\"$")
  public void itIsABadgeForAnOrganisationOrPerson(String partyType) {
    if (null != partyType && (partyType.equals("PERSON") || partyType.equals("ORGANISATION"))) {
      scenarioContext.setContext("typeCode", partyType);
    }
  }

  @And("^I should see correct details for \"(ORGANISATION|PERSON)\" badge$")
  public void iShouldSeeCorrectDetailsForBadge(String partyType) throws Throwable {

    if (partyType.equalsIgnoreCase("PERSON")) {

      try {
        WebElement genderElement = sitePage.findElementByXpath("//*[text()='Eligibility']");
      } catch (Exception e) {
        assertThat("Check if View badge details is for person", "False", is("True"));
      }

    } else {

      try {
        WebElement orgElement = sitePage.findElementByXpath("//*[text()='Organisation details']");
      } catch (Exception e) {
        assertThat("Check if View badge details is for Organisation", "False", is("True"));
      }
    }
  }
}
