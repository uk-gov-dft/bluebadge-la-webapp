package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.CREATE_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.DELETE_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.GET_BY_ID_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.GET_CURRENT_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.GET_USERS_FOR_AUTHORITY_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.REQUEST_RESET_EMAIL_ENDPOINT;
import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.UPDATE_ENDPOINT;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;

@Service
public class UserManagementApiClient {

  static class Endpoints {
    private Endpoints() {}

    static final String GET_CURRENT_ENDPOINT = "/users/me";
    static final String GET_BY_ID_ENDPOINT = "/users/{uuid}";
    static final String CREATE_ENDPOINT = "/users";
    static final String GET_USERS_FOR_AUTHORITY_ENDPOINT =
        "/users?name={name}&authorityShortCode={authorityShortCode}";
    static final String UPDATE_ENDPOINT = "/users/{uuid}";
    static final String DELETE_ENDPOINT = "/users/{uuid}";
    static final String REQUEST_RESET_EMAIL_ENDPOINT = "/users/{uuid}/passwordReset";
  }

  private RestTemplate restTemplate;

  @Autowired
  public UserManagementApiClient(
      @Qualifier("userManagementRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Retrieve a list of users for a Local Authority wrapped in a UsersResponse.
   *
   * @param authorityShortCode The Local Authority to retrieve for.
   * @return UsersResponse containing list of Users.
   */
  public List<User> getUsersForAuthority(String authorityShortCode, String nameFilter) {
    ResponseEntity<UsersResponse> userListResponse =
        restTemplate.getForEntity(
            GET_USERS_FOR_AUTHORITY_ENDPOINT, UsersResponse.class, nameFilter, authorityShortCode);
    return Objects.requireNonNull(userListResponse.getBody()).getData();
  }

  public User currentUserDetails() {
    return Objects.requireNonNull(
            restTemplate.getForEntity(GET_CURRENT_ENDPOINT, UserResponse.class).getBody())
        .getData();
  }

  public User getByUuid(UUID uuid) {
    Assert.notNull(uuid, "uuid must be provided for getByUuid");

    return Objects.requireNonNull(
            restTemplate
                .getForEntity(GET_BY_ID_ENDPOINT, UserResponse.class, uuid.toString())
                .getBody())
        .getData();
  }

  public User createUser(User user) {
    Assert.notNull(user, "createUser - user must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    return Objects.requireNonNull(
            restTemplate.postForObject(CREATE_ENDPOINT, request, UserResponse.class))
        .getData();
  }

  public User updateUser(User user) {
    Assert.notNull(user, "updateUser - must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    String uri = UriComponentsBuilder.fromUriString(UPDATE_ENDPOINT).build().toUriString();

    return Objects.requireNonNull(
            restTemplate
                .exchange(
                    uri, HttpMethod.PUT, request, UserResponse.class, user.getUuid().toString())
                .getBody())
        .getData();
  }

  public void deleteUser(UUID uuid) {
    Assert.notNull(uuid, "deleteUser - uuid must be set");

    String uri = UriComponentsBuilder.fromUriString(DELETE_ENDPOINT).build().toUriString();
    restTemplate.delete(uri, uuid.toString());
  }

  public void requestPasswordReset(UUID uuid) {
    Assert.notNull(uuid, "requestPasswordReset - uuid must not be null");
    restTemplate.postForEntity(REQUEST_RESET_EMAIL_ENDPOINT, null, String.class, uuid);
  }
}
