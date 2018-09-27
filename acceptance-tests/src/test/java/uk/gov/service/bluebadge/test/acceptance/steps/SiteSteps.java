package uk.gov.service.bluebadge.test.acceptance.steps;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.config.AcceptanceTestProperties;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SignInPage;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.*;

public class SiteSteps extends AbstractSpringSteps {

  private static final Logger log = getLogger(SiteSteps.class);

  protected NameGenerator ng = new NameGenerator();
  protected LocalDateGenerator ldg = new LocalDateGenerator();
  protected PostCodeGenerator pcg = new PostCodeGenerator();

  @Autowired protected SitePage sitePage;

  @Autowired private SignInPage signInPage;

  @Autowired private AcceptanceTestProperties acceptanceTestProperties;

  @Autowired private TestContentUrls urlLookup;

  @Autowired protected ScenarioContext scenarioContext;

  @Given("^I navigate to (?:the )?\"([^\"]+)\" (?:.* )?page$")
  public void givenINavigateToPage(String pageName) throws Throwable {
    sitePage.openByPageName(pageName);
  }

  @When("^I (?:can )?click on(?: the| link)? \"([^\"]+)\"(?: link| button)?$")
  public void whenIClickOn(String linkTitle) throws Throwable {
    sitePage.findElementWithText(linkTitle).click();
  }

  @Then("^I (?:can )?see \"([^\"]+)\" (?:link|button|image)$")
  public void thenISeeLink(String linkTitle) throws Throwable {
    assertNotNull("Can see element", sitePage.findElementWithTitle(linkTitle));
  }

  @Then("^I (?:can )?see labelled element \"([^\"]+)\" with content \"([^\"]+)\"$")
  public void thenISeeElementWithUiPathAndContent(String uiPath, String content) throws Throwable {
    assertNotNull(
        "Can see element with data-uipath: " + uiPath, sitePage.findElementWithUiPath(uiPath));
    assertThat(sitePage.findElementWithUiPath(uiPath).getText(), containsString(content));
  }

  @Then("^I should see (?:.* )?page titled \"([^\"]+)\"$")
  public void thenIShouldSeePageTitled(String pageTitle) throws Throwable {
    assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
  }

  @Then("^I should see the content \"([^\"]*)\"$")
  public void thenIShouldSeeTheContent(String content) throws Throwable {
    assertThat(sitePage.getPageContent(), containsString(content));
  }

  @Then("^I should (?:also )?see:?$")
  public void thenIShouldAlsoSee(final DataTable pageSections) throws Throwable {
    String elementName = null;
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
  public void thenIShouldSeeItemsOf(String pageElementName, final DataTable elementItems)
      throws Throwable {
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
  public void thenIShouldNotSeeHeaders(DataTable headersTable) throws Throwable {
    List<String> headers = headersTable.asList(String.class);
    for (String header : headers) {
      assertNull("Header should not be displayed", sitePage.findElementWithText(header));
    }
  }

  @Then("^I should see headers?:$")
  public void thenIShouldSeeHeaders(DataTable headersTable) throws Throwable {
    List<String> headers = headersTable.asList(String.class);
    for (String header : headers) {
      assertNotNull("Header should be displayed: " + header, sitePage.findElementWithText(header));
    }
  }

  @Then("^I wait (\\d+)s$")
  @SuppressWarnings("squid:S2925") // Suppress thread.sleep warning
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
  public void thenIShouldNotSeeElementTitled(String title) throws Throwable {
    assertNull("Element is not on page", sitePage.findElementWithTitle(title));
  }

  @And("^I type username \"([^\"]+)\" and  ***REMOVED***)
  public void andITypeUsernameAndPassword(String username, String password) throws Throwable {
    signInPage.findPageElementById("username").sendKeys(username);
    signInPage.findPageElementById("password").sendKeys(password);
  }

  @And("^I can click Sign in button$")
  public void andICanClickSignInButton() throws Throwable {
    signInPage.findElementWithUiPath("button").click();
  }

  @Then("^I should see the title \"([^\"]*)\"$")
  public void iShouldSeeTheTitle(String title) throws Throwable {
    assertThat("Incorrect page title", sitePage.getH1Tag(), getMatcherForText(title));
  }

  @When("^I click on Start now button$")
  public void iClickOnStartNowButton() throws Throwable {
    sitePage.findPageElementById("get-started");
  }

  @And("^I select an option \"([^\"]*)\"$")
  public void iSelectAnOption(String value) throws Throwable {
    sitePage.findPageElementById(value).click();
  }

  @And("^I select an option \"([^\"]*)\" on \"([^\"]*)\"$")
  public void iSelectAnOption(String value, String selectId) throws Throwable {
    Select select = new Select(sitePage.findPageElementById(selectId));
    select.selectByVisibleText(value);
  }

  @And("^I click on Continue button$")
  public void iClickOnContinueButton() throws Throwable {
    sitePage.findPageElementById("submit").click();
  }

  @And("^I select No$")
  public void iSelectNo() throws Throwable {
    sitePage.findPageElementById("renewal-or-new-application-new").click();
  }

  @And("^I should see LA name as \"([^\"]*)\"$")
  public void iShouldSeeLANameAs(String la) throws Throwable {
    assertThat(
        "LA name expected",
        signInPage.findElementWithUiPath("topbar.title").getText(),
        getMatcherForText(la));
  }

  @And("^I should see username as \"([^\"]*)\"$")
  public void iShouldSeeUsernameAs(String username) throws Throwable {
    assertThat(
        "Username expected",
        signInPage.findElementWithUiPath("topbar.username").getText(),
        getMatcherForText(username));
  }

  @And("^I should see signout link$")
  public void iShouldSeeSignoutLink() throws Throwable {
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
  public void andICanClickSignOutButton() throws Throwable {
    sitePage.findElementWithUiPath("topbar.signout").click();
  }

  @When("^I type \"([^\"]+)\" for \"([^\"]+)\" field$")
  public void whenItypeTextForField(String text, String field) throws Throwable {
    signInPage.findPageElementById(field).sendKeys(text);
  }

  @When("^I type \"([^\"]+)\" for \"([^\"]+)\" field by uipath$")
  public void whenItypeTextForFieldUiPath(String text, String fieldUiPath) throws Throwable {
    signInPage.findElementWithUiPath(fieldUiPath).sendKeys(text);
  }

  @When("^I enter full name and email address and clicks on create a new user button$")
  public void iEnterFullNameAndEmailAddressAndClicksOnCreateANewUserButton() throws Throwable {

    String name = ng.get_full_name();
    String email = ng.get_email(name);
    System.setProperty("fullname", name);
    System.setProperty("email", email);

    sitePage.findPageElementById("name").sendKeys(name);
    sitePage.findPageElementById("emailAddress").sendKeys(email);
    sitePage.findElementWithUiPath("createUserButton").click();
    thenIWait(3);
  }

  @And("^I should see the newly created user is on the users list$")
  public void iShouldSeeTheNewCreatedUserIsOnTheUsersList() throws Throwable {
    assertTrue(sitePage.getPageContent().contains(System.getProperty("email")));
  }

  @And("^I should see \"([^\"]*)\" text on the page$")
  public void iShouldSeeTextOnPage(String content) throws Throwable {
    assertTrue(sitePage.getPageContent().contains(content));
  }

  @And("^I should not see \"([^\"]*)\" text on the page$")
  public void iShouldNotSeeTextOnPage(String content) throws Throwable {
    assertFalse(sitePage.getPageContent().contains(content));
  }

  @Then("^I should see the validation message for \"([^\"]*)\" as \"([^\"]*)\"$")
  public void iShouldSeeTheValidationMessageForAs(String arg0, String arg1) throws Throwable {
    WebElement errorElement = null;
    if (arg0.equals("invalid email")) {
      errorElement =
          signInPage.findElementWithUiPath("emailAddress.summary-error");
    } else if (arg0.equals("invalid email or password")) {
      errorElement =
          signInPage.findElementWithUiPath("error.form.signin.invalid");
    } else if (arg0.equals("invalid name")) {
      errorElement =
          signInPage.findElementWithUiPath("name.summary-error");
    } else if (arg0.equals("blank permissions")) {
      errorElement =
          signInPage.findElementWithUiPath("role.summary-error");
    } else if (arg0.equals("blank Local authority")) {
      errorElement =
          signInPage.findElementWithUiPath("localAuthorityShortCode.summary-error");
    }
    assertThat(
        "Failed to find error element for " + arg0,
        errorElement,
        Matchers.notNullValue());
    assertThat("Validation message expected", errorElement.getText(), getMatcherForText(arg1));
  }

  @When("^I search for newly create user using email address$")
  public void iSearchForNewlyCreateUserUsingEmailAddress() throws Throwable {
    sitePage.findPageElementById("search").sendKeys(System.getProperty("email"));
    sitePage.findElementWithUiPath("search.button").click();
  }

  @Then("^I should see the search results with newly created user$")
  public void iShouldSeeTheSearchResultsWithNewlyCreatedUser() throws Throwable {
    assertThat(
        "Only 1 result is expected",
        sitePage.findElementWithUiPath("search.count").getText(),
        getMatcherForText("1 Result:"));
    assert (sitePage
        .findElementWithUiPath("table.body")
        .getText()
        .contains(System.getProperty("email")));
  }

  @When("^I search for newly create user using full name$")
  public void iSearchForNewlyCreateUserUsingFullName() throws Throwable {
    sitePage.findPageElementById("search").sendKeys(System.getProperty("fullname"));
    sitePage.findElementWithUiPath("search.button").click();
  }

  @And("^I can click on the \"([^\"]*)\" button on manage user page$")
  public void iCanClickOnTheButtonOnManageUserPage(String arg0) throws Throwable {
    sitePage.findElementWithUiPath("createUserButton").click();
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
        getMatcherForText(System.getProperty("updated_email")));
  }

  @When("^I enter invalid email address and clicks on update button$")
  public void iEnterInvalidEmailAddressAndClicksOnUpdateButton() throws Throwable {

    sitePage.findElementWithUiPath("emailAddress.field").clear();
    sitePage.findElementWithUiPath("emailAddress.field").sendKeys("not valid email");

    sitePage.findElementWithUiPath("updateUserButton").click();
  }

  @And("^I (?:can )?click on element \"([^\"]+)\"(?: link| button)?$")
  public void andICanClickOnElement(String uiPath) throws Throwable {
    sitePage.findElementWithUiPath(uiPath).click();
  }

  @And("^I can click on the \"([^\"]*)\" link on left navigation$")
  public void iCanClickOnTheLinkOnLeftNavigation(String linkTitle) throws Throwable {
    String uiPath = navTitleToUiPath(linkTitle);
    sitePage.findElementWithUiPath(uiPath).click();
  }

  private String navTitleToUiPath(String navTitle) {
    String uiPath;
    switch (navTitle) {
      case "Manage users":
        uiPath = "sidebar-nav.manage-users";
        break;
      case "Order a badge":
        uiPath = "sidebar-nav.order-a-badge";
        break;
      case "Find a badge":
        uiPath = "sidebar-nav.manage-badges";
        break;
      case "New applications":
        uiPath = "sidebar-nav.new-applications";
        break;
      default:
        uiPath = "sidebar-nav";
        break;
    }
    return uiPath;
  }

  @When("^I select option \"([^\"]*)\"$")
  public void iSelectOption(String arg0) throws Throwable {
    sitePage.findElementWithUiPath(arg0).click();
  }

  @When("^I select option \"([^\"]*)\" from dropdown \"([^\"]*)\"$")
  public void iSelectSingleOptionFromDropdown(String optionName, String dropdownName) {
    WebElement dropElement = sitePage.findElementWithUiPath(dropdownName);
    Select dropdown = new Select(dropElement);
    dropdown.selectByValue(optionName);
  }

  @And("^I type the search term \"([^\"]*)\" in the field \"([^\"]*)\"$")
  public void iTypeSearchTermInTheField(String searchTerm, String uipath) {
    sitePage.findElementWithUiPath(uipath).sendKeys(searchTerm);
  }

  @Then("^I should see only results containing search term \"([^\"]*)\"$")
  public void iShouldSeeOnlyResultsContainingSearchTerm(String searchTerm) {

    assertTrue(sitePage.getPageContent().contains(searchTerm));

    List<WebElement> records =
        sitePage.findElementWithUiPath("table.body").findElements(By.className("govuk-table__row"));

    for (WebElement record : records) {
      assertTrue(record.getText().toLowerCase().contains(searchTerm.toLowerCase()));
    }

    WebElement displayCount = sitePage.findElementWithUiPath("search.count");
    assertTrue(displayCount.getText().equals(records.size() + " Results:"));
  }

  @Then("^I should see only results where postcode \"([^\"]*)\"$")
  public void iShouldSeeOnlyResultsWithPostcode(String postcode) {

    List<WebElement> records =
        sitePage.findElementWithUiPath("table.body").findElements(By.className("govuk-table__row"));

    List<String> displayedRecordsNames =
        records
            .stream()
            .map(r -> r.findElement(By.tagName("a")).getText())
            .collect(Collectors.toList());

    List<String> namesWithPostcode = Arrays.asList("John The First", "Alex Johnson");
    for (String record : displayedRecordsNames) {
      assertTrue(namesWithPostcode.contains(record));
    }

    WebElement displayCount = sitePage.findElementWithUiPath("search.count");
    assertTrue(displayCount.getText().equals(records.size() + " Results:"));
  }

  @And("^I can click \"([^\"]*)\" button$")
  public void iCanClickButton(String uiPath) throws Throwable {
    sitePage.findElementWithUiPath(uiPath).click();
  }

  @And("^I should ([^\"]+)(?: not see | see)? element with ui path \"([^\"]*)\"$")
  public void iShouldSeeElementWithUiPath(String visible, String uiPath) throws Throwable {
    WebElement elementWithUiPath = sitePage.findElementWithUiPath(uiPath);
    assertThat(
        elementWithUiPath,
        "not see".equals(visible) ? Matchers.nullValue() : Matchers.notNullValue());
  }

  @And("^I should ([^\"]+)(?: not see | see)? the left navigation menu item \"([^\"]*)\"$")
  public void iShouldSeeTheLeftNavigationMenuItem(String visible, String navTitle)
      throws Throwable {
    String uiPath = navTitleToUiPath(navTitle);
    iShouldSeeElementWithUiPath(visible, uiPath);
  }

  @And("^I can see all records$")
  public void iCanSeeNotFilteredRecords() {
    List<WebElement> records =
        sitePage.findElementWithUiPath("table.body").findElements(By.className("govuk-table__row"));

    List<String> allRecordsNames =
        Arrays.asList("John The First", "Alex Johnson", "David Littlejohnson", "Freddie Kruger");
    assertFalse(records.size() < allRecordsNames.size());

    List<String> displayedRecordsNames =
        records
            .stream()
            .map(r -> r.findElement(By.tagName("a")).getText())
            .collect(Collectors.toList());

    assertTrue(displayedRecordsNames.containsAll(allRecordsNames));

    WebElement displayCount =
        sitePage.findElementWithUiPath("title").findElement(By.tagName("span"));
    assertTrue(Integer.valueOf(displayCount.getText()).equals(records.size()));
  }

  @And(
      "^Search filter \"([^\"]*)\" has value \"([^\"]*)\" and search field \"([^\"]*)\" has value \"([^\"]*)\"$")
  public void serachFilterAndSearchFieldCorrectlyPopulated(
      String searchFilter, String filterValue, String searchField, String fieldValue) {
    WebElement dropElement = sitePage.findElementWithUiPath(searchFilter);
    Select dropdown = new Select(dropElement);
    assertEquals(filterValue, dropdown.getFirstSelectedOption().getText());

    WebElement fieldElement = sitePage.findElementWithUiPath(searchField);
    assertEquals(fieldValue, fieldElement.getAttribute("value"));
  }

  @Then("^I see no records returned for the search term \"([^\"]*)\"$")
  public void iShouldSeeNoRecordsForTheSearchTerm(String searchTerm) {
    assertTrue(
        sitePage
            .findElementWithUiPath("search.term")
            .getText()
            .contains("There are no results for " + searchTerm));

    WebElement displayCount = sitePage.findElementWithUiPath("search.count");
    assertTrue(displayCount.getText().equals("0 Results:"));
  }

  //hooks
  private Map<String, Object> settings() {
    Map<String, Object> settings = new HashMap<>();

    settings.put("username", "developer");
    settings.put(" ***REMOVED***);
    settings.put(
        "url", "jdbc:postgresql://localhost:5432/bb_dev?currentSchema=applicationmanagement");
    settings.put("driverClassName", "org.postgresql.Driver");

    return settings;
  }

  @Before("@NewApplicationScripts")
  public void executeInsertApplicationsDBScript() throws SQLException {
    DbUtils db = new DbUtils(settings());
    db.runScript("scripts/create_applications.sql");
  }

  @After("@NewApplicationScripts")
  public void executeDeleteApplicationsDBScript() throws SQLException {
    DbUtils db = new DbUtils(settings());
    db.runScript("scripts/delete_applications.sql");
  }

  @Before("@UsersRolesAndPermissionsScripts")
  public void executeInsertUsersDBScript() throws SQLException {
    DbUtils db = new DbUtils(settings());
    db.runScript("scripts/create_users.sql");
  }

  @After("@UsersRolesAndPermissionsScripts")
  public void executeDeleteUsersDBScript() throws SQLException {
    DbUtils db = new DbUtils(settings());
    db.runScript("scripts/delete_users.sql");
  }

  @Then("^I should see the newly created user's permission as \"([^\"]*)\"$")
  public void iShouldSeeTheNewlyCreatedUserSPermissionAs(String permission) throws Throwable {
    assertThat(
        "Only 1 result is expected",
        sitePage.findElementWithUiPath("search.count").getText(),
        getMatcherForText("1 Result:"));
    assert (sitePage
        .findElementWithUiPath("table.body")
        .getText()
        .contains(System.getProperty("email")));
    assert (sitePage.findElementWithUiPath("table.body").getText().contains(permission));
  }
}
