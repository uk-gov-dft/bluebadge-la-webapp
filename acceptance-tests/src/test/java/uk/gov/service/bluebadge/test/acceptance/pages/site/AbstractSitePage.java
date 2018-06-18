package uk.gov.service.bluebadge.test.acceptance.pages.site;

import uk.gov.service.bluebadge.test.acceptance.pages.AbstractPage;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverProvider;

public abstract class AbstractSitePage extends AbstractPage {

  public static final String URL = System.getProperty("baseUrl");

  public AbstractSitePage(WebDriverProvider webDriverProvider) {
    super(webDriverProvider);
  }
}
