package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import java.time.LocalDate;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.LocalDateGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.NameGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.PostCodeGenerator;

public class OrderABadgeSiteSteps {

  private static final Logger log = getLogger(OrderABadgeSiteSteps.class);
  protected NameGenerator ng = new NameGenerator();
  protected LocalDateGenerator ldg = new LocalDateGenerator();
  protected PostCodeGenerator pcg = new PostCodeGenerator();

  @Autowired protected SitePage sitePage;

  @Autowired protected ScenarioContext scenarioContext;

  @When("^I enter all the mandatory valid personal details to order a badge$")
  public void iEnterAllMandatoryValidPersonalDetailsToOrderABadge() throws Throwable {
    String name = ng.get_full_name();
    String contactName = ng.get_full_name();
    LocalDate date = ldg.get_local_date();

    String dobDay = String.valueOf(date.getDayOfMonth());
    String dobMonth = String.valueOf(date.getMonth().getValue());
    String dobYear = String.valueOf(date.getYear());
    String postcode = pcg.get_postcode();

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findPageElementById("male").click();
    sitePage.findPageElementById("dobDay").sendKeys(dobDay);
    sitePage.findPageElementById("dobMonth").sendKeys(dobMonth);
    sitePage.findPageElementById("dobYear").sendKeys(dobYear);
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(postcode);
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").sendKeys("020 7014 0800");

    Select select = new Select(sitePage.findPageElementById("eligibility"));
    select.selectByVisibleText("PIP");
  }

  @When("^I enter all valid personal details to order a badge$")
  public void iEnterAllValidPersonalDetailsToOrderABadge() throws Throwable {
    String name = ng.get_full_name();
    String contactName = "Contact " + name;
    LocalDate date = ldg.get_local_date();
    String email = ng.get_email(name);

    String dobDay = String.valueOf(date.getDayOfMonth());
    String dobMonth = String.valueOf(date.getMonth().getValue());
    String dobYear = String.valueOf(date.getYear());
    String postcode = pcg.get_postcode();

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findPageElementById("male").click();
    sitePage.findPageElementById("dobDay").sendKeys(dobDay);
    sitePage.findPageElementById("dobMonth").sendKeys(dobMonth);
    sitePage.findPageElementById("dobYear").sendKeys(dobYear);
    sitePage.findElementWithUiPath("nino.field").sendKeys("SN234234C");
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("optionalAddressField.field").sendKeys("second line of address");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(postcode);
    sitePage.findElementWithUiPath("contactDetailsName.field").sendKeys(contactName);
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").sendKeys("020 7014 0800");
    sitePage
        .findElementWithUiPath("contactDetailsSecondaryContactNumber.field")
        .sendKeys("0161 763 8309");
    sitePage.findElementWithUiPath("contactDetailsEmailAddress.field").sendKeys(email);

    Select select = new Select(sitePage.findPageElementById("eligibility"));
    select.selectByVisibleText("PIP");
  }

  @When("^I enter all the mandatory valid processing details to order a badge$")
  public void iEnterAllMandatoryValidProcessingDetailsToOrderABadge() throws Throwable {
    sitePage.findElementWithUiPath("applicationDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("applicationDate.month.field").sendKeys("6");
    sitePage.findElementWithUiPath("applicationDate.year.field").sendKeys("2018");
    sitePage.findPageElementById("online").click();
    sitePage.findElementWithUiPath("badgeStartDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeStartDate.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeStartDate.year.field").sendKeys("2025");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.year.field").sendKeys("2028");
    sitePage.findPageElementById("badgeHolder").click();
    sitePage.findPageElementById("standard").click();
  }

  @When("^I enter all valid processing details to order a badge$")
  public void iEnterAllValidProcessingDetailsToOrderABadge() throws Throwable {
    sitePage.findElementWithUiPath("applicationDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("applicationDate.month.field").sendKeys("6");
    sitePage.findElementWithUiPath("applicationDate.year.field").sendKeys("2018");
    sitePage.findPageElementById("online").click();
    sitePage.findElementWithUiPath("badgeStartDate.day.field").sendKeys("1");
    sitePage
        .findElementWithUiPath("localAuthorityReferenceNumber.field")
        .sendKeys("Manchester City Council");
    sitePage.findElementWithUiPath("badgeStartDate.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeStartDate.year.field").sendKeys("2025");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.year.field").sendKeys("2028");
    sitePage.findPageElementById("badgeHolder").click();
    sitePage.findPageElementById("standard").click();
  }

  @And("^I should see a badge number on badge ordered page$")
  public void iShouldSeeABadgeNumberOnBadgeOrderedPage() {
    String badgeNumber = sitePage.findElementWithUiPath("badge.ordered.num").getText();
    assertNotNull(badgeNumber);
    assertNotEquals("", badgeNumber);
    log.debug("Badge number of the badge ordered is: [()]", badgeNumber);
    scenarioContext.setContext("badgeNumber", badgeNumber);
  }
}
