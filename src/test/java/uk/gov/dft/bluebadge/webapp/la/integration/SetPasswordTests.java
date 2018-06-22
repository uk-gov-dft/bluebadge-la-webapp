package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.given;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SetPasswordTests extends IntegrationTestsBase {
  @Test
  public void givenNoAuth_whenSetPasswordRequested_thenSuccess() {
    given()
        .log()
        .all()
        .get("/set***REMOVED***/{email_uuid}", UUID.randomUUID().toString())
        .then()
        .statusCode(200);
  }
}
