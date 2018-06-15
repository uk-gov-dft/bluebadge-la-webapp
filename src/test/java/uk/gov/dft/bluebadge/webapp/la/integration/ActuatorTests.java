package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ActuatorTests extends IntegrationTestsBase {

  @Before
  public void setup() {
    RestAssured.basePath = "/manage/actuator";
    RestAssured.port = MANAGEMENT_PORT;
  }

  @Test
  public void givenNoAuth_whenActuatorInfoRequested_thenSuccess() {
    get("info").then().statusCode(200);
  }

  @Test
  public void givenNoAuth_whenActuatorHealthRequested_thenSuccess() {
    get("health").then().statusCode(200).body("status", equalTo("UP"));
  }

  @Test
  public void givenNoAuth_whenActuatorLoggersRequested_thenSuccess() {
    get("loggers").then().statusCode(200);
  }
}
