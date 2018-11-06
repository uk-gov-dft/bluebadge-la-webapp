package uk.gov.dft.bluebadge.webapp.la.integration;

import com.jayway.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import redis.embedded.RedisServer;
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
public abstract class IntegrationTestsBase {
  @LocalServerPort protected int serverPort;

  protected static final int MANAGEMENT_PORT = 19999;
  protected String baseUrl;

  private static RedisServer REDISSERVER = new RedisServer(35623);

  @BeforeClass
  public static void before() {
    REDISSERVER.start();
  }

  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.basePath = "/";
    RestAssured.port = serverPort;

    baseUrl = RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
  }

  @AfterClass
  public static void after() {
    REDISSERVER.stop();
  }
}
