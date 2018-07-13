package uk.gov.service.bluebadge.test.acceptance.steps;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.config.AcceptanceTestProperties;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SignInPage;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.LocalDateGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.NameGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.PostCodeGenerator;
import uk.gov.service.bluebadge.test.acceptance.util.TestContentUrls;

import java.util.List;

public class SiteSteps extends AbstractSpringSteps {

  private static final Logger log = getLogger(SiteSteps.class);
  private NameGenerator ng = new NameGenerator();
  protected LocalDateGenerator ldg = new LocalDateGenerator();
  protected PostCodeGenerator pcg = new PostCodeGenerator();

  @Autowired private SitePage sitePage;

  @Autowired private SignInPage signInPage;

  @Autowired private AcceptanceTestProperties acceptanceTestProperties;

  @Autowired private TestContentUrls urlLookup;

  @Given("^I navigate to (?:the )?\"([^\"]+)\" (?:.* )?page$")
  public void givenINavigateToPage(String pageName) {
    sitePage.openByPageName(pageName);
  }

  @Given("^I navigate to (?:the )?\"([^\"]+)\" (?:.* )?url$")
  public void givenINavigateToPageUnmapped(String pageName) {
    sitePage.openByPageNameUnmapped(pageName);
  }

  @When("^I (?:can )?click on(?: the| link)? \"([^\"]+)\"(?: link| button)?$")
  public void whenIClickOn(String linkTitle) {
    sitePage.findElementWithText(linkTitle).click();
  }

  @Then("^I (?:can )?see \"([^\"]+)\" (?:link|button|image)$")
  public void thenISeeLink(String linkTitle) {
    assertNotNull("Can see element", sitePage.findElementWithTitle(linkTitle));
  }

  @Then("^I (?:can )?see labelled element \"([^\"]+)\" with content \"([^\"]+)\"$")
  public void thenISeeElementWithUiPathAndContent(String uiPath, String content) {
    assertNotNull(
        "Can see element with data-uipath: " + uiPath, sitePage.findElementWithUiPath(uiPath));
    assertThat(sitePage.findElementWithUiPath(uiPath).getText(), containsString(content));
  }

  @Then("^I should see (?:.* )?page titled \"([^\"]+)\"$")
  public void thenIShouldSeePageTitled(String pageTitle) {
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @Then("^I should see the content \"([^\"]*)\"$")
  public void thenIShouldSeeTheContent(String content) {
    assertThat(sitePage.getPageContent(), containsString(content));
  }

  @Then("^I should see the \"page not found\" error page$")
  public void thenIShouldSeeThePageNotFoundErrorPage() {
    // Ideally we would check the HTTP response code is 404 as well but it's not
    // currently possible to do this with the Selinium Web Driver.
    // See https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/141
    thenIShouldSeePageTitled("Page not found");
  }

  @Then("^I should (?:also )?see:?$")
  public void thenIShouldAlsoSee(final DataTable pageSections) {
    String elementName;
    for (List<String> elementsContent : pageSections.raw()) {
      elementName = elementsContent.get(0);
      WebElement pageElement = sitePage.findPageElement(elementName);

      assertThat("I should find page element: " + elementName, pageElement, is(notNullValue()));

      assertThat(
          "Page element " + elementName + " should contain ...",
          getElementText(pageElement),
          getMatcherForText(elementsContent.get(1)));
    }
  }

  @Then("^I should(?: also)? see \"([^\"]+)\" with:")
  public void thenIShouldSeeItemsOf(String pageElementName, final DataTable elementItems) {
    WebElement pageElement = sitePage.findPageElement(pageElementName);

    assertNotNull("I should find page element: " + pageElementName, pageElement);

    for (List<String> elementItem : elementItems.raw()) {
      String expectedItemText = elementItem.get(0);

      assertThat(
          "Page element " + pageElementName + " contain item with text: " + expectedItemText,
          getElementTextList(pageElement),
          hasItem(getMatcherForText(expectedItemText)));
    }
  }

  @Then("^I should not see headers?:$")
  public void thenIShouldNotSeeHeaders(DataTable headersTable) {
    List<String> headers = headersTable.asList(String.class);
    for (String header : headers) {
      assertNull("Header should not be displayed", sitePage.findElementWithText(header));
    }
  }

  @Then("^I should see headers?:$")
  public void thenIShouldSeeHeaders(DataTable headersTable) {
    List<String> headers = headersTable.asList(String.class);
    for (String header : headers) {
      assertNotNull("Header should be displayed: " + header, sitePage.findElementWithText(header));
    }
  }

  @Then("^I wait (\\d+)s$")
  public void thenIWait(int sec) throws InterruptedException {
    Thread.sleep(sec * 1000);
  }

  private static Matcher<String> getMatcherForText(String text) {
    if (text.endsWith(" ...")) {
      return startsWith(text.substring(0, text.length() - 4));
    }

    return is(text);
  }

  private String getElementText(WebElement element) {
    if (element.getTagName().equals("input")) {
      return element.getAttribute("value");
    }

    return element.getText();
  }

  private List<String> getElementTextList(WebElement element) {
    return element
        .findElements(By.tagName("li"))
        .stream()
        .map(WebElement::getText)
        .collect(toList());
  }

  @Then("^I should not see element with title \"([^\"]*)\"$")
  public void thenIShouldNotSeeElementTitled(String title) {
    assertNull("Element is not on page", sitePage.findElementWithTitle(title));
  }

  @And("^I type username \"([^\"]+)\" and  ***REMOVED***)
  public void andITypeUsernameAndPassword(String username, String password) {
    signInPage.findPageElementById("username").sendKeys(username);
    signInPage.findPageElementById("password").sendKeys(password);
  }

  @And("^I can click Sign in button$")
  public void andICanClickSignInButton() {
    signInPage.findElementWithUiPath("button").click();
  }

  @When("^I click \"([^\"]*)\"$")
  public void whenIClick(String uiPath) {
    signInPage.findElementWithUiPath(uiPath).click();
  }

  @Then("^I should see the title \"([^\"]*)\"$")
  public void iShouldSeeTheTitle(String title) {
    assertThat("Incorrect page title", sitePage.getH1Tag(), getMatcherForText(title));
  }

  @When("^I click on Start now button$")
  public void iClickOnStartNowButton() {
    sitePage.findPageElementById("get-started");
  }

  @And("^I select an option \"([^\"]*)\"$")
  public void iSelectAnOption(String value) {
    sitePage.findPageElementById(value).click();
  }

  @And("^I select an option \"([^\"]*)\" on \"([^\"]*)\"$")
  public void iSelectAnOption(String value, String selectId) {
    Select select = new Select(sitePage.findPageElementById(selectId));
    select.selectByVisibleText(value);
  }

  @And("^I click on Continue button$")
  public void iClickOnContinueButton() {
    sitePage.findPageElementById("submit").click();
  }

  @And("^I select No$")
  public void iSelectNo() {
    sitePage.findPageElementById("renewal-or-new-application-new").click();
  }

  @And("^I should see LA name as \"([^\"]*)\"$")
  public void iShouldSeeLANameAs(String la) {
    assertThat(
        "LA name expected",
        signInPage.findElementWithUiPath("topbar.title").getText(),
        getMatcherForText(la));
  }

  @And("^I should see username as \"([^\"]*)\"$")
  public void iShouldSeeUsernameAs(String username) {
    assertThat(
        "Username expected",
        signInPage.findElementWithUiPath("topbar.username").getText(),
        getMatcherForText(username));
  }

  @And("^I should see signout link$")
  public void iShouldSeeSignoutLink() {
    assertThat(
        "Sign out link expected",
        signInPage.findElementWithUiPath("topbar.signout"),
        notNullValue());
    assertThat(
        "Sign out link expected",
        signInPage.findElementWithUiPath("topbar.signout").getAttribute("value"),
        getMatcherForText("Sign out"));
  }

  @And("^I can click Sign out button$")
  public void andICanClickSignOutButton() {
    sitePage.findElementWithUiPath("topbar.signout").click();
  }

  @When("^I type \"([^\"]+)\" for \"([^\"]+)\" field$")
  public void whenItypeTextForField(String text, String field) {
    signInPage.findPageElementById(field).sendKeys(text);
  }

  @When("^I enter full name and email address and clicks on create a new user button$")
  public void iEnterFullNameAndEmailAddressAndClicksOnCreateANewUserButton() {

    String name = ng.get_full_name();
    String email = ng.get_email(name);
    System.setProperty("fullname", name);
    System.setProperty("email", email);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findPageElementById("emailAddress").sendKeys(email);
    sitePage.findElementWithUiPath("createUserButton").click();
  }

  @And("^I should see the newly created user is on the users list$")
  public void iShouldSeeTheNewCreatedUserIsOnTheUsersList() {
    assertTrue(sitePage.getPageContent().contains(System.getProperty("email")));
  }

  @Then("^I should see the validation message for \"([^\"]*)\" displayed as \"([^\"]*)\"$")
  public void iShouldSeeValidationMessage(String validationEnumName, String expected) {
    ValidationMessageEnum messageEnum = ValidationMessageEnum.valueOf(validationEnumName);
    assertMessageDisplayed(
        messageEnum.getAssertFailedMessage(), messageEnum.getElementUiPath(), expected);
  }

  private void assertMessageDisplayed(String error, String uiPath, String text) {
    assertThat(error, signInPage.findElementWithUiPath(uiPath).getText(), getMatcherForText(text));
  }

  @Then("^I should see the validation message for \"([^\"]*)\" as \"([^\"]*)\"$")
  public void iShouldSeeTheValidationMessageForAs(String arg0, String arg1) {
    switch (arg0) {
      case "invalid email":
        assertThat(
            "Validation message expected",
            signInPage.findElementWithUiPath("username.summary-error").getText(),
            getMatcherForText(arg1));
        break;
      case "invalid password":
        assertThat(
            "Validation message expected",
            signInPage.findElementWithUiPath("error-summary-description").getText(),
            getMatcherForText(arg1));
        break;
      case "invalid name":
        assertThat(
            "Validation message expected",
            signInPage.findElementWithUiPath("name.summary-error").getText(),
            getMatcherForText(arg1));
        break;
      case "invalid email on update details":
        assertThat(
            "Validation message expected",
            signInPage.findElementWithUiPath("emailAddress.summary-error").getText(),
            getMatcherForText(arg1));
        break;
      default:
        fail("Please select a valid test condition");
        break;
    }
  }

  @When("^I search for newly create user using email address$")
  public void iSearchForNewlyCreateUserUsingEmailAddress() {
    sitePage.findPageElementById("search").sendKeys(System.getProperty("email"));
    sitePage.findElementWithUiPath("search.button").click();
  }

  @Then("^I should see the search results with newly created user$")
  public void iShouldSeeTheSearchResultsWithNewlyCreatedUser() {
    assertThat(
        "Only 1 result is expected",
        sitePage.findElementWithUiPath("search.count").getText(),
        getMatcherForText("1 Result:"));
    assert (sitePage
        .findElementWithUiPath("table.body")
        .getText()
        .contains(System.getProperty("email")));
  }

  @When("^I click on the first name link from users table$")
  public void iClickOnTheFirstNameLinkFromUsersTable() {
    sitePage.findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(1)>a").click();
  }

  @When("^I change email address and clicks on update button$")
  public void iChangeEmailAddressAndClicksOnUpdateButton() {
    String new_email = ng.get_email(sitePage.findPageElementById("name").getAttribute("value"));

    sitePage.findElementWithUiPath("emailAddress.field").clear();
    sitePage.findElementWithUiPath("emailAddress.field").sendKeys(new_email);
    System.setProperty("updated_email", new_email);

    sitePage.findElementWithUiPath("updateUserButton").click();
  }

  @Then("^I should see the relevant email address has updated$")
  public void iShouldSeeTheUpdatedUserIsOnTheUsersTable() {

    assertThat(
        "Updated email address expected",
        sitePage
            .findElementWithCssSelector("table>tbody>tr:nth-child(1)>td:nth-child(2)")
            .getText(),
        getMatcherForText(System.getProperty("updated_email")));
  }

  @When("^I enter invalid email address and clicks on update button$")
  public void iEnterInvalidEmailAddressAndClicksOnUpdateButton() {

    sitePage.findElementWithUiPath("emailAddress.field").clear();
    sitePage.findElementWithUiPath("emailAddress.field").sendKeys("not valid email");

    sitePage.findElementWithUiPath("updateUserButton").click();
  }

  @And("^I (?:can )?click on element \"([^\"]+)\"(?: link| button)?$")
  public void AndICanClickOnElement(String uiPath) {
    sitePage.findElementWithUiPath(uiPath).click();
  }

  @And("^I can click on the \"([^\"]*)\" link on left navigation$")
  public void iCanClickOnTheLinkOnLeftNavigation(String linkTitle) {
    String uipath = "sidebar-nav";
    switch (linkTitle) {
      case "Manage users":
        uipath = "sidebar-nav.manage-users";
        break;
      case "Order a badge":
        uipath = "sidebar-nav.order-a-badge";
        break;
      default:
        break;
    }
    sitePage.findElementWithUiPath(uipath).click();
  }

  @When("^I select option \"([^\"]*)\"$")
  public void iSelectOption(String arg0) {
    sitePage.findElementWithUiPath(arg0).click();
  }

  @And("^I can click \"([^\"]*)\" button$")
  public void iCanClickButton(String uiPath) {
    sitePage.findElementWithUiPath(uiPath).click();
  }
}
