package uk.gov.service.bluebadge.test.acceptance.pages.site;

import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverProvider;

public class NewsPage extends AbstractSitePage {

  private PageHelper helper;

  public NewsPage(WebDriverProvider webDriverProvider, final PageHelper helper) {
    super(webDriverProvider);
    this.helper = helper;
  }

  public String getPageTitle() {
    return getWebDriver().getTitle();
  }
}
