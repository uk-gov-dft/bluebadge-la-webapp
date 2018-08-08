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

import java.util.UUID;

public class UserManagementApiClientTest {

  public static final String TEST_URI = "http://justtesting:9999/test";
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  private static final UUID USER_UUID = UUID.randomUUID();
  private static final UUID USER_UUID_2 = UUID.randomUUID();
  private static final UUID USER_UUID_DELETE = UUID.randomUUID();

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
        .andExpect(queryParam("authorityShortCode", LOCAL_AUTHORITY_SHORT_CODE))
        .andRespond(withSuccess(om.writeValueAsString(usersResponse), MediaType.APPLICATION_JSON));

    userManagementApiClient.getUsersForAuthority(LOCAL_AUTHORITY_SHORT_CODE, "bob");

    mockServer.verify();
  }

  @Test
  public void getByUuid() throws Exception {
    UserResponse userResponse = new UserResponse();

    User data = User.builder()
      .name("Bob")
      .build();

    userResponse.setData(data);
    mockServer
        .expect(once(), requestTo(TEST_URI + String.format("/users/%s",USER_UUID.toString())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User user = userManagementApiClient.getByUuid(USER_UUID);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("Bob");
    mockServer.verify();
  }

  @Test
  public void createUser() throws Exception {
    UserResponse userResponse = new UserResponse();
    User responseUser = User.builder()
      .uuid(USER_UUID)
      .build();
    userResponse.setData(responseUser);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(jsonPath("emailAddress", equalTo("Jane@bbb.com")))
        .andExpect(jsonPath("name", equalTo("Jane")))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User userToCreate = User.builder()
      .emailAddress("Jane@bbb.com")
      .name("Jane")
      .build();

    User result = userManagementApiClient.createUser(userToCreate);
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(userToCreate);
    assertThat(result.getUuid()).isEqualTo(USER_UUID);
    mockServer.verify();
  }

  @Test
  public void updateUser() throws Exception {
    UserResponse userResponse = new UserResponse();
    User responseData = User.builder()
      .uuid(USER_UUID)
      .build();
    userResponse.setData(responseData);
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/" + USER_UUID_2.toString()))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(jsonPath("emailAddress", equalTo("dave@bbb.com")))
        .andExpect(jsonPath("name", equalTo("Dave")))
        .andRespond(withSuccess(om.writeValueAsString(userResponse), MediaType.APPLICATION_JSON));

    User userToUpdate = User.builder().emailAddress("dave@bbb.com").name("Dave").uuid(USER_UUID_2).build();
    User result = userManagementApiClient.updateUser(userToUpdate);

    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(userToUpdate);
    assertThat(result.getUuid()).isEqualTo(USER_UUID);
    mockServer.verify();
  }

  @Test
  public void deleteUser() throws Exception {
    mockServer
        .expect(once(), requestTo(TEST_URI + "/users/" + USER_UUID_DELETE.toString()))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withSuccess());

    userManagementApiClient.deleteUser(USER_UUID_DELETE);

    mockServer.verify();
  }

  @Test
  public void requestPasswordReset() throws Exception {
    mockServer
        .expect(once(), requestTo(TEST_URI + String.format("/users/%s/passwordReset", USER_UUID_DELETE.toString())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    userManagementApiClient.requestPasswordReset(USER_UUID_DELETE);
    mockServer.verify();
  }
}
