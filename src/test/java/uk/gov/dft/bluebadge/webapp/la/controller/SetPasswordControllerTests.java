package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SetPasswordControllerTests extends MockMVCWithSecurityTests {

  @Test
  public void givenNoAuth_whenGetSetPasswordRequest_thenSuccess() throws Exception {
    mockMvc
        .perform(get("/set***REMOVED***/{uuid}", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(view().name("set***REMOVED***"));
  }

  @Test
  public void givenNoAuth_whenPostSetPasswordRequest_thenSuccess() throws Exception {
    String passwordUuid = UUID.randomUUID().toString();

    mockServer
        .expect(method(HttpMethod.PATCH))
        .andExpect(
            requestTo(
                "http://localhost:8180/test-usermanagementservice/user/password/" + passwordUuid))
        .andRespond(
            withSuccess(
                "{\"apiVersion\":null,\"context\":null,\"id\":null,"
                    + "\"method\":null,\"errors\":null,\"data\":{\"totalItems"
                    + "\":2,\"users\":[{\"id\":1,\"name\":\"Bob\","
                    + "\"emailAddress\":\"blah@blah.com\",\"localAuthorityId\":2}"
                    + ",{\"id\":3,\"name\":\"Bob2\",\"emailAddress\":"
                    + "\"blah2@blah.com\",\"localAuthorityId\":2}]}}",
                MediaType.APPLICATION_JSON));

    mockMvc
        .perform(
            post("/set***REMOVED***/{uuid}", passwordUuid)
                .param(" ***REMOVED***)
                .param(" ***REMOVED***)
                .with(csrf()))
        .andExpect(redirectedUrl("/"));
  }
}
