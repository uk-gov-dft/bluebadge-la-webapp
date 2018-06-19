package uk.gov.service.bluebadge.test.acceptance.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Paths;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;
import uk.gov.service.bluebadge.test.acceptance.pages.site.ManageUsersPage;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SignInPage;
import uk.gov.service.bluebadge.test.acceptance.pages.site.SitePage;
import uk.gov.service.bluebadge.test.acceptance.util.TestContentUrls;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverProvider;
import uk.gov.service.bluebadge.test.acceptance.webdriver.WebDriverServiceProvider;

/**
 * Central configuration class, enabling acceptance tests to benefit from Spring-based dependency
 * injection.
 */
@Configuration
public class AcceptanceTestConfiguration {

  private static final Logger log = getLogger(AcceptanceTestConfiguration.class);

  @Bean
  public PageHelper pageHelper(final WebDriverProvider webDriverProvider) {
    return new PageHelper(webDriverProvider);
  }

  @Bean
  public TestContentUrls testContentUrls() {
    return new TestContentUrls();
  }

  @Bean
  public WebDriverProvider webDriverProvider(
      final WebDriverServiceProvider webDriverServiceProvider,
      final AcceptanceTestProperties acceptanceTestProperties) {

    return new WebDriverProvider(
        webDriverServiceProvider,
        acceptanceTestProperties.isHeadlessMode(),
        acceptanceTestProperties.getDownloadDir());
  }

  @Bean(initMethod = "initialise", destroyMethod = "dispose")
  public WebDriverServiceProvider webDriverServiceProvider() {
    return new WebDriverServiceProvider();
  }

  @Bean
  public AcceptanceTestProperties acceptanceTestProperties(final Environment environment) {

    //final String buildDirectory = environment.getRequiredProperty("buildDirectory");
    final String buildDirectory = "./build";

    // Supported custom JVM system properties:
    //
    // headless       - values 'true' or 'false'; optional. Determines whether the tests will be run in a headless
    //                  mode.
    // buildDirectory - full path to a directory that the build process generates artefacts into, one that the
    //                  tests can safely write temporary content to and expected that it'll be gone on clean build.
    //                  In Maven this would typically be 'target' directory of the current module.

    final AcceptanceTestProperties acceptanceTestProperties =
        new AcceptanceTestProperties(
            Boolean.parseBoolean(System.getProperty("headless", "true")),
            Paths.get(buildDirectory, "download"),
            Paths.get(buildDirectory));

    log.info("Applying test properties: {}", acceptanceTestProperties);

    return acceptanceTestProperties;
  }

  @Bean
  public SitePage sitePage(final WebDriverProvider webDriverProvider, final PageHelper pageHelper) {
    return new SitePage(webDriverProvider, pageHelper);
  }

  @Bean
  public SignInPage signInPage(final WebDriverProvider webDriverProvider, PageHelper helper) {
    return new SignInPage(webDriverProvider, helper);
  }

  @Bean
  public ManageUsersPage manageUsersPage(
      final WebDriverProvider webDriverProvider, PageHelper helper) {
    return new ManageUsersPage(webDriverProvider, helper);
  }
}
