package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.*;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;

@Service
public class UserManagementApiClient extends BaseApiClient {

  static class Endpoints {
    private Endpoints() {}

    static final String GET_BY_ID_ENDPOINT = "/users/{userId}";
    static final String CREATE_ENDPOINT = "/users";
    static final String GET_USERS_FOR_AUTHORITY_ENDPOINT =
        "/users?name={name}&authorityId={authorityId}";
    static final String UPDATE_ENDPOINT = "/users/{userId}";
    static final String DELETE_ENDPOINT = "/users/{userId}";
    static final String REQUEST_RESET_EMAIL_ENDPOINT = "/users/{userId}/passwordReset";
  }

  private RestTemplate restTemplate;

  @Autowired
  public UserManagementApiClient(
      @Qualifier("userManagementRestTemplate") RestTemplate userManagementRestTemplate) {
    this.restTemplate = userManagementRestTemplate;
  }

  /**
   * Retrieve a list of users for a Local Authority wrapped in a UsersResponse.
   *
   * @param authorityId The Local Authority to retrieve for.
   * @return UsersResponse containing list of Users.
   */
  public List<User> getUsersForAuthority(Integer authorityId, String nameFilter) {

    Assert.notNull(authorityId, "getUsersForAuthority - Local Authority Id must be provided");

    ResponseEntity<UsersResponse> userListResponse =
        restTemplate.getForEntity(
            GET_USERS_FOR_AUTHORITY_ENDPOINT, UsersResponse.class, nameFilter, authorityId);
    return Objects.requireNonNull(userListResponse.getBody()).getData();
  }

  public User getById(Integer userId) {
    Assert.notNull(userId, "userId must be provided for getById");

    try {
      return Objects.requireNonNull(
              restTemplate.getForEntity(GET_BY_ID_ENDPOINT, UserResponse.class, userId).getBody())
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  public User createUser(User user) {
    Assert.notNull(user, "createUser - user must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    try {
      return Objects.requireNonNull(
              restTemplate.postForObject(CREATE_ENDPOINT, request, UserResponse.class))
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  public User updateUser(User user) {
    Assert.notNull(user, "updateUser - must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    String uri = UriComponentsBuilder.fromUriString(UPDATE_ENDPOINT).build().toUriString();

    try {
      return Objects.requireNonNull(
              restTemplate
                  .exchange(uri, HttpMethod.PUT, request, UserResponse.class, user.getId())
                  .getBody())
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  public void deleteUser(Integer userId) {
    Assert.notNull(userId, "deleteUser - userId must be set");

    String uri = UriComponentsBuilder.fromUriString(DELETE_ENDPOINT).build().toUriString();
    try {
      restTemplate.delete(uri, userId);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }

  public void requestPasswordReset(Integer id) {
    Assert.notNull(id, "requestPasswordReset - id must not be null");

    try {
      restTemplate.getForEntity(REQUEST_RESET_EMAIL_ENDPOINT, String.class, id);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }
}
