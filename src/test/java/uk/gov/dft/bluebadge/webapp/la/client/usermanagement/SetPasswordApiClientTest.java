package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;

public class SetPasswordApiClientTest {

  public static final String TEST_URI = "http://justtesting:9999/test";
  SetPasswordApiClient setPasswordApiClient;

  private MockRestServiceServer mockServer;
  private ObjectMapper om = new ObjectMapper();

  @Before
  public void setup() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    setPasswordApiClient = new SetPasswordApiClient(restTemplate);
  }

  @Test
  public void updatePassword() throws Exception {
    String uuid = "test_uuid";
    UserResponse userResponse = new UserResponse();
    User data = new User();
    data.setId(555);
    userResponse.setData(data);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/user/password/" + uuid))
        .andExpect(method(HttpMethod.PATCH))
        .andExpect(jsonPath(" ***REMOVED***)))
        .andExpect(jsonPath(" ***REMOVED***)))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    setPasswordApiClient.updatePassword(uuid, "pass1", "pass2");
    mockServer.verify();
  }
}