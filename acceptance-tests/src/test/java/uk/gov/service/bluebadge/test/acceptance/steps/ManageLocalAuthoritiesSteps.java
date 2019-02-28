package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertEquals;

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

  @And("^I should see non-mandatory fields marked as optional$")
  public void andIShouldSeeNonMandatoryFieldsMarkedAsOptional() {
    assertEquals(
        "Welsh description (optional)",
        sitePage.findElementWithUiPath("welshDescription.label").getText());
    assertEquals(
        "Name line 2 (optional)", sitePage.findElementWithUiPath("nameLine2.label").getText());
    assertEquals(
        "Address line 1 (optional)",
        sitePage.findElementWithUiPath("addressLine1.label").getText());
    assertEquals(
        "Address line 2 (optional)",
        sitePage.findElementWithUiPath("addressLine2.label").getText());
    assertEquals(
        "Address line 3 (optional)",
        sitePage.findElementWithUiPath("addressLine3.label").getText());
    assertEquals(
        "Address line 4 (optional)",
        sitePage.findElementWithUiPath("addressLine4.label").getText());
    assertEquals("Town (optional)", sitePage.findElementWithUiPath("town.label").getText());
    assertEquals("County (optional)", sitePage.findElementWithUiPath("county.label").getText());
    assertEquals(
        "Contact number (optional)",
        sitePage.findElementWithUiPath("contactNumber.label").getText());
    assertEquals(
        "Email address (optional)", sitePage.findElementWithUiPath("emailAddress.label").getText());
    assertEquals(
        "Badge pack type (optional)",
        sitePage.findElementWithUiPath("badgePackType.label").getText());
    assertEquals(
        "Payments enabled (optional)",
        sitePage.findElementWithUiPath("paymentsEnabled.label").getText());
    assertEquals(
        "Different service signpost URL (optional)",
        sitePage.findElementWithUiPath("differentServiceSignpostUrl.label").getText());
  }
}
