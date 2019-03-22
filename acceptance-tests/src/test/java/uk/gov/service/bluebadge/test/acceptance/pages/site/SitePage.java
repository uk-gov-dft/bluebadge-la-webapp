package uk.gov.service.bluebadge.test.acceptance.pages.site;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import uk.gov.service.bluebadge.test.acceptance.pages.AbstractPage;
import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;
import uk.gov.service.bluebadge.test.acceptance.util.TestContentUrls;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverProvider;

public class SitePage extends AbstractPage {

  public static final String URL = System.getProperty("baseUrl");

  private PageHelper helper;
  private TestContentUrls urlLookup;
  private List<PageElements> pagesElements;

  public SitePage(WebDriverProvider webDriverProvider, final PageHelper helper) {
    super(webDriverProvider);
    this.helper = helper;
    this.urlLookup = new TestContentUrls();
    this.pagesElements = new ArrayList<>();

    pagesElements.add(new CommonPageElements());
  }

  public void openByPageName(final String pageName) {
    try {
      String lookupUrl = urlLookup.lookupUrl(pageName);
      getWebDriver().get(lookupUrl);
    } catch (Exception ex) {
      openByPageNameUnmapped(pageName);
    }
  }

  public void openByPageNameUnmapped(final String pageName) {
    String lookupUrl = TestContentUrls.lookupUrlUnmapped(pageName);
    getWebDriver().get(lookupUrl);
  }

  public void clickOnElement(WebElement element) {
    // scroll to element to prevent error like "Other element would receive the click"
    new Actions(getWebDriver()).moveToElement(element).moveByOffset(0, 100).perform();

    // click it
    element.click();
  }

  public WebElement findElementWithText(String text) {
    return helper.findOptionalElement(By.xpath("//*[text()='" + text + "']"));
  }

  public WebElement findElementWithTitle(String title) {
    return helper.findOptionalElement(By.xpath("//*[@title='" + title + "']"));
  }

  public WebElement findElementWithUiPath(String uiPath) {
    return helper.findOptionalElement(By.xpath("//*[@data-uipath='" + uiPath + "']"));
  }

  public WebElement findElementByXpath(String xpath) {
    return helper.findElement(By.xpath(xpath));
  }

  public String getDocumentTitle() {
    return getWebDriver().getTitle();
  }

  public WebElement findPageElement(String elementName) {
    return findPageElement(elementName, 0);
  }

  public WebElement findElementWithCssSelector(String CssSelector) {
    return helper.findOptionalElement(By.cssSelector(CssSelector));
  }

  public String getPageContent() {
    return getWebDriver().getPageSource();
  }

  public WebElement findPageElement(String elementName, int nth) {
    for (PageElements pageElements : pagesElements) {
      if (pageElements.contains(elementName)) {
        return pageElements.getElementByName(elementName, nth, helper);
      }
    }

    return null;
  }

  public WebElement findPageElementById(String elementId) {
    return helper.findElement(By.id(elementId));
  }

  public String getH1Tag() {
    return getWebDriver().findElement(By.tagName("h1")).getText();
  }

private List<WebElement>ListOfNewApplications(){
    return getWebDriver().findElements(By.xpath(".//tr[@class='govuk-table__row']/td/a"));
}

public   WebElement SelectSpecificNewApplication(String name){
    List<WebElement>allApplications=ListOfNewApplications();
    for(WebElement application:allApplications){
      if(application.getText().equalsIgnoreCase(name)){
        return application;
      }
    }
    return  null;
}
  private List<WebElement> AllBreathlessnessAnswers(){
    return getWebDriver().findElements(By.xpath(".//div[@class='dft-data-list__item' and contains(.,'When they get breathless')]//ul"));
  }

  private String FilterBreathlessAnswer(String x){

    List<WebElement>breathlessAnswers=AllBreathlessnessAnswers();

    for(WebElement breath: breathlessAnswers){

      if(breath.getText().contains(x)){
        return x;
      }
    }

    return  null;
  }

  public  boolean ApplicationContainsBreathlessness(String answer){
    return FilterBreathlessAnswer(answer)!=null;
  }


}
