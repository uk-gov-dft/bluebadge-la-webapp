package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;

public class OrderBadgeFromApplicationSteps extends AbstractSpringSteps {

  private static final Logger log = getLogger(SiteSteps.class);

  @Autowired protected SitePage sitePage;
  @Autowired protected DatabaseSteps databaseSteps;

  @And("^I can see the \"Order badge\" button$")
  public void iCanSeeOrderBadgeButton() throws Throwable {
    assertNotNull("Can see element", sitePage.findElementWithUiPath("order"));
  }

  @And("^I cannot see the \"Order badge\" button$")
  public void iCannotSeeOrderBadgeButton() throws Throwable {
    assertNull("Cannot see element", sitePage.findElementWithUiPath("order"));
  }

  @When("^I click the \"Order badge\" button$")
  public void iClickOrderBadgeButton() throws Throwable {
    sitePage.findElementWithUiPath("order").click();
  }

  @Then("^I should see the \"Order a badge\" page where the relevant application type is pre-set$")
  public void iShouldSeeOrderABadgePagePrefilledWithApplicationDetails() throws Throwable {
    assertThat(
        "I should see page titled.",
        sitePage.getDocumentTitle(),
        is("Order a badge - GOV.UK Manage Blue Badges"));
    assertThat(
        "Application type should be ...",
        sitePage.findElementWithUiPath("applicantType.option.person").getAttribute("checked"),
        is("true"));
  }

  @Then(
      "^I should see the \"Personal Details\" page where the form is pre-filled with the application's relevant data$")
  public void iShouldSeePersonalDetailsPagePrefilledWithApplicationDetails() throws Throwable {
    assertThat(
        "I should see page titled.",
        sitePage.getDocumentTitle(),
        is("Personal Details - GOV.UK Manage Blue Badges"));

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

  @Then(
      "^I should see the \"Processing\" page where the form is pre-filled with the application's relevant data$")
  public void iShouldSeeProcessingPagePrefilledWithApplicationDetails() throws Throwable {
    assertThat(
        "I should see page titled.",
        sitePage.getDocumentTitle(),
        is("Processing - GOV.UK Manage Blue Badges"));

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
