package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.net.URISyntaxException;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.S3Helper;

public class OrderBadgeFromApplicationSteps extends AbstractSpringSteps {

  @Autowired protected SitePage sitePage;
  @Autowired protected DatabaseSteps databaseSteps;

  @Before("@OrderBadgeFromApplication")
  public void putPhotoInS3() throws URISyntaxException {
    S3Helper s3Helper = new S3Helper();
    s3Helper.putApplicationObject("/small.png", "applicationTestPhoto.png");
  }

  @And("^I can see the \"Order badge\" button$")
  public void iCanSeeOrderBadgeButton() {
    assertNotNull("Can see element", sitePage.findElementWithUiPath("order"));
  }

  @And("^I cannot see the \"Order badge\" button$")
  public void iCannotSeeOrderBadgeButton() {
    assertNull("Cannot see element", sitePage.findElementWithUiPath("order"));
  }

  @When("^I click the \"Order badge\" button$")
  public void iClickOrderBadgeButton() {
    sitePage.findElementWithUiPath("order").click();
  }

  @Then("^I should see the \"Order a badge\" page form pre-filled$")
  public void iShouldSeeOrderABadgePagePrefilled() {
    assertThat(
        "Application type should be ...",
        sitePage.findElementWithUiPath("applicantType.option.person").getAttribute("checked"),
        is("true"));
  }

  @Then("^I should see the \"Personal Details\" page form pre-filled$")
  public void iShouldSeePersonalDetailsPagePrefilled() {
    // Personal details
    assertThat(
        "Name field should contain ...",
        sitePage.findElementWithUiPath("name.field").getAttribute("value"),
        is("John The First"));
    assertThat(
        "DOB Day field should contain ...",
        sitePage.findElementWithUiPath("dob.day.field").getAttribute("value"),
        is("29"));
    assertThat(
        "DOB Month field should contain ...",
        sitePage.findElementWithUiPath("dob.month.field").getAttribute("value"),
        is("5"));
    assertThat(
        "DOB Year field should contain ...",
        sitePage.findElementWithUiPath("dob.year.field").getAttribute("value"),
        is("1970"));
    assertThat(
        "NINO field should contain ...",
        sitePage.findElementWithUiPath("nino.field").getAttribute("value"),
        is("AA234567B"));

    // Address
    assertThat(
        "Building and street field should contain ...",
        sitePage.findElementWithUiPath("buildingAndStreet.field").getAttribute("value"),
        is("Contact Building Street"));
    assertThat(
        "Town or city field should contain ...",
        sitePage.findElementWithUiPath("townOrCity.field").getAttribute("value"),
        is("Contact Town City"));
    assertThat(
        "Postcode field should contain ...",
        sitePage.findElementWithUiPath("postcode.field").getAttribute("value"),
        is("SW11AA"));

    // Contact details
    assertThat(
        "Email address field should contain ...",
        sitePage.findElementWithUiPath("contactDetailsEmailAddress.field").getAttribute("value"),
        is("test@mail.com"));

    assertThat(
        "Primary contact number field should contain ...",
        sitePage.findElementWithUiPath("contactDetailsContactNumber.field").getAttribute("value"),
        is("0502345678"));
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").clear();
    sitePage.findElementWithUiPath("contactDetailsContactNumber.field").sendKeys("020 7014 0800");

    // Gender radio
    assertThat(
        "Gender should be ...",
        sitePage.findElementWithUiPath("gender.option.MALE").getAttribute("checked"),
        is("true"));

    // Eligibility select
    assertThat(
        "Eligibility type code should be ...",
        sitePage.findElementWithUiPath("eligibility.field").getAttribute("value"),
        is("WALKD"));
  }

  @Then("^I should see the \"Processing\" page form pre-filled$")
  public void iShouldSeeProcessingPagePrefilled() {
    assertThat(
        "Gender should be ...",
        sitePage.findElementWithUiPath("applicationChannel.option.ONLINE").getAttribute("checked"),
        is("true"));

    sitePage.findElementWithUiPath("badgeStartDate.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeStartDate.month.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeStartDate.year.field").sendKeys("2050");

    sitePage.findElementWithUiPath("badgeExpiryDateValid.day.field").sendKeys("1");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.month.field").sendKeys("2");
    sitePage.findElementWithUiPath("badgeExpiryDateValid.year.field").sendKeys("2050");

    sitePage.findElementWithUiPath("deliverTo_council").click();
  }

  @Before("@OrderBadgeFromApplicationScripts")
  public void executeInsertApplicationsDBScript() throws SQLException {
    databaseSteps.runScript("scripts/create-applications-for-ordering-badges-from.sql");
  }

  @After("@OrderBadgeFromApplicationScripts")
  public void executeDeleteApplicationsDBScript() throws SQLException {
    databaseSteps.runScript("scripts/delete-applications-for-ordering-badges-from.sql");
  }
}
