package uk.gov.service.bluebadge.test.acceptance.steps;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.config.AcceptanceTestProperties;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SignInPage;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.TestContentUrls;

import java.util.List;


public class SiteSteps extends AbstractSpringSteps {

    private static final Logger log = getLogger(SiteSteps.class);

    @Autowired
    private SitePage sitePage;

    @Autowired
    private SignInPage signInPage;

    @Autowired
    private AcceptanceTestProperties acceptanceTestProperties;

    @Autowired
    private TestContentUrls urlLookup;

    @Given("^I navigate to (?:the )?\"([^\"]+)\" (?:.* )?page$")
    public void givenINavigateToPage(String pageName) throws Throwable {
        sitePage.openByPageName(pageName);
    }

    @When("^I (?:can )?click on(?: the| link)? \"([^\"]+)\"(?: link| button)?$")
    public void whenIClickOn(String linkTitle) throws Throwable {
        sitePage.findPageElementById("idcta-username").click();
    }

    @Then("^I (?:can )?see \"([^\"]+)\" (?:link|button|image)$")
    public void thenISeeLink(String linkTitle) throws Throwable {
        assertNotNull("Can see element", sitePage.findElementWithTitle(linkTitle));
    }

    @Then("^I (?:can )?see labelled element \"([^\"]+)\" with content \"([^\"]+)\"$")
    public void thenISeeElementWithUiPathAndContent(String uiPath, String content) throws Throwable {
        assertNotNull("Can see element with data-uipath: " + uiPath, sitePage.findElementWithUiPath(uiPath));
        assertThat(sitePage.findElementWithUiPath(uiPath).getText(), containsString(content));
    }

    @Then("^I should see (?:.* )?page titled \"([^\"]+)\"$")
    public void thenIShouldSeePageTitled(String pageTitle) throws Throwable {
        assertThat("I should see page titled.", sitePage.getDocumentTitle(), is(pageTitle));
    }

    @Then("^I should see the content \"([^\"]*)\"$")
    public void thenIShouldSeeTheContent(String content) throws Throwable {
        assertThat("Document content is as expected", sitePage.getDocumentContent(), getMatcherForText(content));
    }

    @Then("^I should see the \"page not found\" error page$")
    public void thenIShouldSeeThePageNotFoundErrorPage() throws Throwable {
        // Ideally we would check the HTTP response code is 404 as well but it's not
        // currently possible to do this with the Selinium Web Driver.
        // See https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/141
        thenIShouldSeePageTitled("Page not found");
    }

    @Then("^I should (?:also )?see:?$")
    public void thenIShouldAlsoSee(final DataTable pageSections) throws Throwable {
        String elementName = null;
        for (List<String> elementsContent : pageSections.raw()) {
            elementName = elementsContent.get(0);
            WebElement pageElement = sitePage.findPageElement(elementName);

            assertThat("I should find page element: " + elementName,
                    pageElement, is(notNullValue()));

            assertThat("Page element " + elementName + " should contain ...",
                    getElementText(pageElement),
                    getMatcherForText(elementsContent.get(1)));
        }
    }

    @Then("^I should(?: also)? see \"([^\"]+)\" with:")
    public void thenIShouldSeeItemsOf(String pageElementName, final DataTable elementItems) throws Throwable {
        WebElement pageElement = sitePage.findPageElement(pageElementName);

        assertNotNull("I should find page element: " + pageElementName, pageElement);

        for (List<String> elementItem : elementItems.raw()) {
            String expectedItemText = elementItem.get(0);

            assertThat("Page element " + pageElementName + " contain item with text: " + expectedItemText,
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
        return element.findElements(By.tagName("li")).stream()
                .map(WebElement::getText)
                .collect(toList());
    }

    @Then("^I should not see element with title \"([^\"]*)\"$")
    public void thenIShouldNotSeeElementTitled(String title) throws Throwable {
        assertNull("Element is not on page", sitePage.findElementWithTitle(title));
    }


    @And("^I type username \"([^\"]+)\" and  ***REMOVED***)
    public void andITypeUsernameAndPassword(String username, String password) throws Throwable {
        signInPage.findPageElementById("email").sendKeys(username);
        signInPage.findPageElementById("password").sendKeys(password);
    }

    @And("^I can click Sign in button$")
    public void andICanClickSignInButton() throws Throwable {
        signInPage.findElementByXpath("/html/body/div/main/div/form/a[2]/input").click();
    }

    @Then("^I should see the validation message \"([^\"]*)\"$")
    public void thenIShouldSeeTheValidationMessage(String validation) throws Throwable {
        assertThat("Validation message expected", signInPage.getDocumentContent(), getMatcherForText(validation));
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

    @And("^I click on Continue button$")
    public void iClickOnContinueButton() throws Throwable {
        sitePage.findPageElementById("submit").click();
    }

    @And("^I select No$")
    public void iSelectNo() throws Throwable {
        sitePage.findPageElementById("renewal-or-new-application-new").click();
    }
}
