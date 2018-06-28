package uk.gov.dft.bluebadge.webapp.la.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.dft.bluebadge.webapp.la.controller.MockMVCWithSecurityTests.VALID_USERNAME_1;

import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SecuredPageTests extends IntegrationTestsBase {
  @Test
  public void givenNoAuth_whenHomepageRequested_thenRedirected() {
    given()
        .redirects()
        .follow(false)
        .log()
        .all()
        .get("/")
        .then()
        .statusCode(302)
        .header(HttpHeaders.LOCATION, baseUrl + "sign-in");
  }
}
