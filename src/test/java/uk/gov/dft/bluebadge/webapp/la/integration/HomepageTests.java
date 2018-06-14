package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.given;

import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HomepageTests extends IntegrationTestsBase {

  @Test
  public void givenNoAuth_whenHomepageRequested_thenRedirected() {
    given()
        .redirects().follow(false)
        .log().all()
        .get("/")
        .then()
        .statusCode(302)
        .header(HttpHeaders.LOCATION, baseUrl + "login");
  }
}
