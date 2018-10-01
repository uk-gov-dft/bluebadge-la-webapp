package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.NameGenerator;

public class ManageUserSteps {

  private static final Logger log = getLogger(ManageUserSteps.class);

  protected NameGenerator ng = new NameGenerator();
  @Autowired protected SiteSteps siteSteps;
  @Autowired protected DatabaseSteps databaseSteps;
  @Autowired protected SitePage sitePage;
  @Autowired protected ScenarioContext scenarioContext;

  @When("^I enter full name and email address and clicks on create a new user button$")
  public void iEnterFullNameAndEmailAddressAndClicksOnCreateANewUserButton() throws Throwable {

    String name = ng.get_full_name();
    String email = ng.get_email(name);
    System.setProperty("fullname", name);
    System.setProperty("email", email);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findPageElementById("emailAddress").sendKeys(email);
    sitePage.findElementWithUiPath("createUserButton").click();
  }

  @And("^I should see the newly created user is on the users list$")
  public void iShouldSeeTheNewCreatedUserIsOnTheUsersList() throws Throwable {
    assertTrue(sitePage.getPageContent().contains(System.getProperty("email")));
  }

  @When("^I click on the first name link from users table$")
  public void iClickOnTheFirstNameLinkFromUsersTable() throws Throwable {
    sitePage.findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(1)>a").click();
  }

  @When("^I change email address and clicks on update button$")
  public void iChangeEmailAddressAndClicksOnUpdateButton() throws Throwable {
    String new_email = ng.get_email(sitePage.findPageElementById("name").getAttribute("value"));

    sitePage.findElementWithUiPath("emailAddress.field").clear();
    sitePage.findElementWithUiPath("emailAddress.field").sendKeys(new_email);
    System.setProperty("updated_email", new_email);

    sitePage.findElementWithUiPath("updateUserButton").click();
  }

  @Then("^I should see the relevant email address has updated$")
  public void iShouldSeeTheUpdatedUserIsOnTheUsersTable() throws Throwable {

    assertThat(
        "Updated email address expected",
        sitePage
            .findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(2)")
            .getText(),
        SiteSteps.getMatcherForText(System.getProperty("updated_email")));
  }

  @When("^I enter invalid email address and clicks on update button$")
  public void iEnterInvalidEmailAddressAndClicksOnUpdateButton() throws Throwable {

    sitePage.findElementWithUiPath("emailAddress.field").clear();
    sitePage.findElementWithUiPath("emailAddress.field").sendKeys("not valid email");

    sitePage.findElementWithUiPath("updateUserButton").click();
  }

  @And("^I create a new user$")
  public void iCreateANewUser() throws Throwable {
    siteSteps.whenIClickOn("Create a new user");
    siteSteps.iSelectAnOption("role.LA_READ");
    iEnterFullNameAndEmailAddressAndClicksOnCreateANewUserButton();
  }

  @And("^I navigate to new users reset password page$")
  public void iNavigateToNewUsersResetPasswordPage() throws Throwable {
    String email = System.getProperty("email");
    String query = "SELECT uuid from usermanagement.email_link el join usermanagement.users u on u.id = el.user_id where u.email_address = '%s'";
    String linkUuid = databaseSteps.queryForString(String.format(query, email));
    siteSteps.givenINavigateToPage("set***REMOVED***/" + linkUuid);
  }
}
