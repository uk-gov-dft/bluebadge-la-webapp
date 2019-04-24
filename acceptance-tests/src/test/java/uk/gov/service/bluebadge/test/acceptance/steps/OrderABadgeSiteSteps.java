package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import java.time.LocalDate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.LocalDateGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.NameGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.PostCodeGenerator;

public class OrderABadgeSiteSteps {

  private static final Logger log = getLogger(OrderABadgeSiteSteps.class);
  private NameGenerator ng = new NameGenerator();
  private LocalDateGenerator ldg = new LocalDateGenerator();
  private PostCodeGenerator pcg = new PostCodeGenerator();

  private SitePage sitePage;
  private ScenarioContext scenarioContext;

  @Autowired
  public OrderABadgeSiteSteps(SitePage sitePage, ScenarioContext scenarioContext) {
    this.sitePage = sitePage;
    this.scenarioContext = scenarioContext;
  }

  @When(
      "^I enter all the mandatory valid personal details to order a badge(?: for ?(.*) postcode)?$")
  public void iEnterAllMandatoryValidPersonalDetailsToOrderABadge(String postcode) {
    String name = ng.get_full_name();
    LocalDate date = ldg.get_local_date();

    String dobDay = String.valueOf(date.getDayOfMonth());
    String dobMonth = String.valueOf(date.getMonth().getValue());
    String dobYear = String.valueOf(date.getYear());
    String myPostcode = null == postcode ? pcg.get_postcode() : postcode;
    scenarioContext.setContext("name", name);
    scenarioContext.setContext("postcode", myPostcode);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findElementWithUiPath("gender.option.MALE").click();
    sitePage.findPageElementById("dobDay").sendKeys(dobDay);
    sitePage.findPageElementById("dobMonth").sendKeys(dobMonth);
    sitePage.findPageElementById("dobYear").sendKeys(dobYear);
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(myPostcode);
    sitePage
        .findElementWithUiPath("contactDetailsContactNumber.field")
        .sendKeys(" 020 7014 0 800 ");

    Select select = new Select(sitePage.findPageElementById("eligibility"));
    select.selectByVisibleText("PIP");
  }

  @When(
      "^I enter all the mandatory valid organisation details to order a badge(?: for ?(.*) postcode)?$")
  public void iEnterAllMandatoryValidOrganisationDetailsToOrderABadge(String postcode) {
    String name = ng.get_full_name();
    String contactName = "Contact " + name;

    String myPostcode = null == postcode ? pcg.get_postcode() : postcode;
    scenarioContext.setContext("name", name);
    scenarioContext.setContext("postcode", myPostcode);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(myPostcode);
    sitePage.findElementWithUiPath("contactDetailsName.field").sendKeys(contactName);
    sitePage
        .findElementWithUiPath("contactDetailsContactNumber.field")
        .sendKeys("+44 20 7014 080 ");
  }

  @When("^I enter all valid personal details to order a badge(?: for ?(.*) postcode)?$")
  public void iEnterAllValidPersonalDetailsToOrderABadge(String postcode) {
    String name = ng.get_full_name();
    String contactName = "Contact " + name;
    LocalDate date = ldg.get_local_date();
    String email = ng.get_email(name);

    String dobDay = String.valueOf(date.getDayOfMonth());
    String dobMonth = String.valueOf(date.getMonth().getValue());
    String dobYear = String.valueOf(date.getYear());
    String myPostcode = null == postcode ? pcg.get_postcode() : postcode;
    scenarioContext.setContext("name", name);
    scenarioContext.setContext("postcode", myPostcode);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findElementWithUiPath("gender.option.MALE").click();
    sitePage.findPageElementById("dobDay").sendKeys(dobDay);
    sitePage.findPageElementById("dobMonth").sendKeys(dobMonth);
    sitePage.findPageElementById("dobYear").sendKeys(dobYear);
    sitePage.findElementWithUiPath("nino.field").sendKeys("sn 23 42 34 C");
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("optionalAddressField.field").sendKeys("second line of address");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(myPostcode);
    sitePage.findElementWithUiPath("contactDetailsName.field").sendKeys(contactName);
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").sendKeys("020 7014 0800");
    sitePage
        .findElementWithUiPath("contactDetailsSecondaryContactNumber.field")
        .sendKeys("0161 763 8309");
    sitePage.findElementWithUiPath("contactDetailsEmailAddress.field").sendKeys(email);

    WebElement fileUpload = sitePage.findElementWithUiPath("photo.field");
    if (System.getProperty("user.dir").endsWith("acceptance-tests")) {
      fileUpload.sendKeys(
          System.getProperty("user.dir") + "/src/test/resources/attachments/icon-test.jpg");
    } else {
      fileUpload.sendKeys(
          System.getProperty("user.dir")
              + "/acceptance-tests/src/test/resources/attachments/icon-test.jpg");
    }
    Select select = new Select(sitePage.findPageElementById("eligibility"));
    select.selectByVisibleText("PIP");
  }

  @When("^I enter all valid organisation details to order a badge(?: for ?(.*) postcode)?$")
  public void iEnterAllValidOrganisationDetailsToOrderABadge(String postcode) {
    String name = ng.get_full_name();
    String contactName = "Contact " + name;
    String email = ng.get_email(name);

    String myPostcode = null == postcode ? pcg.get_postcode() : postcode;
    scenarioContext.setContext("name", name);
    scenarioContext.setContext("postcode", myPostcode);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findElementWithUiPath("buildingAndStreet.field").sendKeys("building and street");
    sitePage.findElementWithUiPath("optionalAddressField.field").sendKeys("second line of address");
    sitePage.findElementWithUiPath("townOrCity.field").sendKeys("Town or city");
    sitePage.findElementWithUiPath("postcode.field").sendKeys(myPostcode);
    sitePage.findElementWithUiPath("contactDetailsName.field").sendKeys(contactName);
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").sendKeys("020 7014 0800");
    sitePage
        .findElementWithUiPath("contactDetailsSecondaryContactNumber.field")
        .sendKeys("0161 763 8309");
    sitePage.findElementWithUiPath("contactDetailsEmailAddress.field").sendKeys(email);
  }

  @When("^I enter all the mandatory valid processing details to order a badge for person$")
  public void iEnterAllMandatoryValidProcessingDetailsToOrderABadgeForPerson() {
    sitePage.findElementWithUiPath("applicationDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("applicationDate.month.field").sendKeys("6");
    sitePage.findElementWithUiPath("applicationDate.year.field").sendKeys("2018");
    sitePage.findElementWithUiPath("applicationChannel.option.PAPER").click();
    sitePage.findElementWithUiPath("badgeStartDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeStartDate.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeStartDate.year.field").sendKeys("2025");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.month.field").sendKeys("5");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.year.field").sendKeys("2028");
    sitePage.findElementWithUiPath("deliverTo_home").click();
    sitePage.findElementWithUiPath("deliveryOptions.option.STAND").click();
  }

  @When("^I enter all the mandatory valid processing details to order a badge for organisation$")
  public void iEnterAllMandatoryValidProcessingDetailsToOrderABadgeForOrganisations() {
    iEnterAllMandatoryValidProcessingDetailsToOrderABadgeForPerson();
    sitePage.findElementWithUiPath("numberOfBadges.field").sendKeys("3");
  }

  @When("^I enter all valid processing details to order a badge for person$")
  public void iEnterAllValidProcessingDetailsToOrderABadgeForPerson() {
    iEnterAllMandatoryValidProcessingDetailsToOrderABadgeForPerson();
    sitePage
        .findElementWithUiPath("localAuthorityReferenceNumber.field")
        .sendKeys("Manchester City Council");
  }

  @When("^I enter all valid processing details to order a badge for organisation")
  public void iEnterAllValidProcessingDetailsToOrderABadgeForOrganisation() {
    iEnterAllMandatoryValidProcessingDetailsToOrderABadgeForPerson();
    sitePage.findElementWithUiPath("numberOfBadges.field").sendKeys("3");
  }

  @And(
      "^I should see a badge number on badge ordered page$|^I should see badge numbers on badge ordered page$")
  public void iShouldSeeABadgeNumberOnBadgeOrderedPage() {
    String badgeNumber = sitePage.findElementWithUiPath("badge.ordered.num").getText();
    assertNotNull(badgeNumber);
    assertNotEquals("", badgeNumber);
    log.debug("Badge number of the badge ordered is: [()]", badgeNumber);
    scenarioContext.setContext("badgeNumber", badgeNumber);
  }
}
