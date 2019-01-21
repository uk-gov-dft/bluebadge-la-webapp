package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.dft.bluebadge.webapp.la.BaseIntegrationNoRedisTest;

@RunWith(SpringRunner.class)
public class ActuatorTests extends BaseIntegrationNoRedisTest {

  protected String baseUrl;

  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.basePath = "/manage/actuator";
    RestAssured.port = managementPort;

    baseUrl = RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
  }

  @Test
  public void givenNoAuth_whenActuatorInfoRequested_thenSuccess() {
    get("info").then().statusCode(200);
  }

  @Test
  public void givenNoAuth_whenActuatorHealthRequested_thenSuccess() {
    get("health").then().log().all().statusCode(503).body("status", equalTo("DOWN"));
  }

  @Test
  public void givenNoAuth_whenActuatorLoggersRequested_thenSuccess() {
    get("loggers").then().statusCode(200);
  }
}
