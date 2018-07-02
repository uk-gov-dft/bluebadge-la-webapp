package uk.gov.service.bluebadge.test.acceptance.pages;

import org.openqa.selenium.WebDriver;
import uk.gov.service.bluebadge.test.acceptance.util.SnapshotHelper;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverProvider;

/**
 * Base class for classes implementing 'Page Object' pattern; provides common properties (such as
 * base URL) and dependencies (such as web driver) to extending classes.
 */
public abstract class AbstractPage {

  private final WebDriverProvider webDriverProvider;

  public AbstractPage(final WebDriverProvider webDriverProvider) {
    this.webDriverProvider = webDriverProvider;
  }

  protected WebDriver getWebDriver() {
    return webDriverProvider.getWebDriver();
  }

  public void takeSnapShot() throws Exception {
    SnapshotHelper.takeSnapShot(getWebDriver());
  }
}
