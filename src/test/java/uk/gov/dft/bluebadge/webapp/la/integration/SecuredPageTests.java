package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;

import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SecuredPageTests extends IntegrationTestsBase {
  @Value("${bbb.auth-server-url}")
  private String authServerUrl;

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

  @Test
  public void givenNoAuth_whenLoginRequested_thenRedirectedToAuthServer() {
    String redirectUrl = given()
        .redirects().follow(false)
        .log().all()
        .get("/")
        .then()
        .statusCode(302)
        .header(HttpHeaders.LOCATION, baseUrl + "login")
        .extract().header(HttpHeaders.LOCATION);

    given()
        .redirects().follow(false)
        .log().all()
        .get(redirectUrl)
        .then()
        .statusCode(302)
        .header(HttpHeaders.LOCATION, startsWith(authServerUrl + "/oauth/authorize"))
        .header(HttpHeaders.LOCATION, containsString("response_type=code"))
    ;
  }
}
