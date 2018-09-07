package uk.gov.service.bluebadge.test.acceptance.webdriver;

import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

/**
 * Manages WebDriver (the client component of the WebDriver).
 *
 * <p>Together with {@linkplain WebDriverServiceProvider} implements <a
 * href="https://sites.google.com/a/chromium.org/chromedriver/getting-started#Controlling_'s_lifetime">
 * recommendations for performant use of the ChromeDriver</a>, where the WebDriver service is
 * started and stopped only once per testing session (costly operation, should be performed as few
 * times as possible) with only the WebDriver client being cycled with each test (cheap operation,
 * can be performed frequently).
 */
public class WebDriverProvider {

  private static final Logger log = getLogger(WebDriverProvider.class);

  private final WebDriverServiceProvider webDriverServiceProvider;

  /**
   * When 'true', the WebDriver will be operating in a headless mode, i.e. not displaying web
   * browser window.
   */
  private final boolean isHeadlessMode;

  /**
   * When 'true', the WebDriver will be operating through zap proxy.
   */
  private final boolean isZapMode;

  /** Location that the web browser will be downloading content into. */
  private final Path downloadDirectory;

  private WebDriver webDriver;

  public WebDriverProvider(
          final WebDriverServiceProvider webDriverServiceProvider,
          final boolean isHeadlessMode,
          final Path downloadDirectory,
          final boolean isZapMode) {
    this.webDriverServiceProvider = webDriverServiceProvider;
    this.isHeadlessMode = isHeadlessMode;
    this.isZapMode = isZapMode;
    this.downloadDirectory = downloadDirectory;
  }

  public WebDriver getWebDriver() {
    if (webDriver == null) {
      throw new IllegalStateException(
          "WebDriver hasn't been initialised, yet. Have you forgotten to call 'initialise()' first?");
    }

    return webDriver;
  }

  /**
   * Initialises the WebDriver (client).
   *
   * <p>This method should be called once before each test to ensure that the session state doesn't
   * bleed from one test to another (such as user being logged in).
   */
  public void initialise() {

    final ChromeOptions chromeOptions = new ChromeOptions();

    final Map<String, Object> chromePrefs = new HashMap<>();
    log.info("Setting WebDriver download directory to '{}'.", downloadDirectory);
    chromePrefs.put("download.default_directory", downloadDirectory.toAbsolutePath().toString());

    chromeOptions.setExperimentalOption("prefs", chromePrefs);
    log.info(
        "Configuring WebDriver to run in {} mode.",
        isHeadlessMode ? "headless" : "full, graphical");
    if (isHeadlessMode) {
      chromeOptions.addArguments("--headless");
      chromeOptions.addArguments("window-size=1920,1080");
    }

    if (isZapMode){

      Proxy proxy = new Proxy();
      proxy.setHttpProxy("localhost:9999");
      proxy.setFtpProxy("localhost:9999");
      proxy.setSslProxy("localhost:9999");
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.PROXY, proxy);

      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
      webDriver = new RemoteWebDriver(webDriverServiceProvider.getUrl(), capabilities);
    }
    else {
      webDriver = new RemoteWebDriver(webDriverServiceProvider.getUrl(), chromeOptions);
    }




  }

  /**
   * Stops the WebDriver client.
   *
   * <p>This method should be called once after each test to ensure that the session state doesn't
   * bleed from one test to another (such as user being logged in).
   */
  public void dispose() {
    webDriver.quit();
    webDriver = null;
  }
}
