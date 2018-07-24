package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
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
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;

public class UserManagementApiClientTest {

  public static final String TEST_URI = "http://justtesting:9999/test";
  UserManagementApiClient userManagementApiClient;

  private MockRestServiceServer mockServer;
  private ObjectMapper om = new ObjectMapper();

  @Before
  public void setup() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    userManagementApiClient = new UserManagementApiClient(restTemplate);
  }

  @Test
  public void getUsersForAuthority() throws Exception {
    UsersResponse usersResponse = new UsersResponse();
    mockServer
        .expect(once(), requestTo(startsWith(TEST_URI + "/users")))
        .andExpect(method(HttpMethod.GET))
        .andExpect(queryParam("name", "bob"))
        .andExpect(queryParam("authorityId", "2"))
        .andRespond(withSuccess(om.writeValueAsString(usersResponse), MediaType.APPLICATION_JSON));

    userManagementApiClient.getUsersForAuthority(2, "bob");

    mockServer.verify();
  }

  @Test
  public void getById() throws Exception {
    UserResponse userResponse = new UserResponse();
    User data = new User();
    data.setName("Bob");
    userResponse.setData(data);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/22"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User user = userManagementApiClient.getById(22);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("Bob");
    mockServer.verify();
  }

  @Test
  public void createUser() throws Exception {
    UserResponse userResponse = new UserResponse();
    User data = new User();
    data.setId(555);
    userResponse.setData(data);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(jsonPath("emailAddress", equalTo("Jane@bbb.com")))
        .andExpect(jsonPath("name", equalTo("Jane")))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User userToCreate = new User();
    userToCreate.setEmailAddress("Jane@bbb.com");
    userToCreate.setName("Jane");

    User result = userManagementApiClient.createUser(userToCreate);
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(userToCreate);
    assertThat(result.getId()).isEqualTo(555);
    mockServer.verify();
  }

  @Test
  public void updateUser() throws Exception {
    UserResponse userResponse = new UserResponse();
    User data = new User();
    data.setId(555);
    userResponse.setData(data);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/789"))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(jsonPath("emailAddress", equalTo("dave@bbb.com")))
        .andExpect(jsonPath("name", equalTo("Dave")))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User userToUpdate = new User();
    userToUpdate.setEmailAddress("dave@bbb.com");
    userToUpdate.setName("Dave");
    userToUpdate.setId(789);
    User result = userManagementApiClient.updateUser(userToUpdate);

    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(userToUpdate);
    assertThat(result.getId()).isEqualTo(555);
    mockServer.verify();
  }

  @Test
  public void deleteUser() throws Exception {
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/456"))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withSuccess());

    userManagementApiClient.deleteUser(456);

    mockServer.verify();
  }

  @Test
  public void requestPasswordReset() throws Exception {
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/756/passwordReset"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    userManagementApiClient.requestPasswordReset(756);
    mockServer.verify();
  }
}
