package uk.gov.dft.bluebadge.webapp.la.integration;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.dft.bluebadge.webapp.la.LocalAuthorityApplication;

/**
 * Base class for all integration tests to give a common config (this helps Spring) and useful
 * defaults.
 *
 * <p>This starts the web app with a random port, which is accessed through the server port field.
 * But most implementations can simply use the configured Rest Assured
 */
@SpringBootTest(
  classes = LocalAuthorityApplication.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {"management.server.port=19999"}
)
@ActiveProfiles({"test", "dev"})
public abstract class IntegrationTestsBase {
  @LocalServerPort protected int serverPort;

  protected static final int MANAGEMENT_PORT = 19999;
  protected String baseUrl;

  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.basePath = "/";
    RestAssured.port = serverPort;

    baseUrl = RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
  }
}
