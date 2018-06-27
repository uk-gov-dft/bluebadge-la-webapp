package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import static uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient.Endpoints.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.Password;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UsersResponse;

@Service
public class UserManagementApiClient {

  class Endpoints {
    private Endpoints() {}

    static final String GET_USER_BY_EMAIL_ENDPOINT = "/users?emailAddress={emailAddress}";
    static final String GET_BY_ID_ENDPOINT = "/authorities/{authorityId}/users/{userId}";
    static final String CREATE_ENDPOINT = "/authorities/{authorityId}/users";
    static final String GET_USERS_FOR_AUTHORITY_ENDPOINT =
        "/authorities/{authorityId}/users?name={name}";
    static final String UPDATE_ENDPOINT = "/authorities/{authorityId}/users/{userId}";
    static final String DELETE_ENDPOINT = "/authorities/{authorityId}/users/{userId}";
    static final String UPDATE_PASSWORD_ENDPOINT = "/user/password/{uuid}";
    static final String REQUEST_PASSWORD_RESET_ENDPOINT =
        "/authorities/{authorityId}/users/{userId}/passwordReset";
  }

  private RestTemplateFactory restTemplateFactory;
  private UserManagementServiceConfiguration serviceConfiguration;

  @Autowired
  public UserManagementApiClient(
      UserManagementServiceConfiguration serviceConfiguration,
      RestTemplateFactory restTemplateFactory) {
    this.serviceConfiguration = serviceConfiguration;
    this.restTemplateFactory = restTemplateFactory;
  }

  public boolean checkUserExistsForEmail(String emailAddress) {
    Assert.notNull(emailAddress, "checkUserExistsForEmail - emailAddress must be supplied");

    UserResponse userResponse = getUserForEmail(emailAddress);
    return 1 == userResponse.getData().getTotalItems();
  }

  public UserResponse getUserForEmail(String emailAddress) {

    String trimmedAddress = StringUtils.trimToNull(emailAddress);
    Assert.notNull(
        trimmedAddress, "getUserForEmail - emailAddress must be provided for getUserForEmail");

    return restTemplateFactory
        .getInstance()
        .getForEntity(
            serviceConfiguration.getUrlPrefix() + GET_USER_BY_EMAIL_ENDPOINT,
            UserResponse.class,
            trimmedAddress)
        .getBody();
  }

  /**
   * Retrieve a list of users for a Local Authority wrapped in a UsersResponse.
   *
   * @param authorityId The Local Authority to retrieve for.
   * @return UsersResponse containing list of Users.
   */
  public UsersResponse getUsersForAuthority(Integer authorityId, String nameFilter) {

    Assert.notNull(authorityId, "getUsersForAuthority - Local Authority Id must be provided");

    ResponseEntity<UsersResponse> userListResponse =
        restTemplateFactory
            .getInstance()
            .getForEntity(
                serviceConfiguration.getUrlPrefix() + GET_USERS_FOR_AUTHORITY_ENDPOINT,
                UsersResponse.class,
                authorityId,
                nameFilter);
    return userListResponse.getBody();
  }

  public UserResponse getById(Integer authorityId, Integer userId) {
    Assert.notNull(authorityId, "authorityId must be provided for getById");
    Assert.notNull(userId, "userId must be provided for getById");

    return restTemplateFactory
        .getInstance()
        .getForEntity(
            serviceConfiguration.getUrlPrefix() + GET_BY_ID_ENDPOINT,
            UserResponse.class,
            authorityId,
            userId)
        .getBody();
  }

  public UserResponse createUser(Integer authorityId, User user) {
    Assert.notNull(authorityId, "Authority Id must be provided");
    Assert.notNull(user, "createUser - user must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    return restTemplateFactory
        .getInstance()
        .postForObject(
            serviceConfiguration.getUrlPrefix() + CREATE_ENDPOINT,
            request,
            UserResponse.class,
            authorityId);
  }

  public UserResponse updateUser(User user) {
    Assert.notNull(user, "updateUser - must be set");

    HttpEntity<User> request = new HttpEntity<>(user);

    String uri =
        UriComponentsBuilder.fromUriString(serviceConfiguration.getUrlPrefix() + UPDATE_ENDPOINT)
            .build()
            .toUriString();

    return restTemplateFactory
        .getInstance()
        .exchange(
            uri,
            HttpMethod.PUT,
            request,
            UserResponse.class,
            user.getLocalAuthorityId(),
            user.getId())
        .getBody();
  }

  public void deleteUser(Integer localAuthorityId, Integer userId) {
    Assert.notNull(localAuthorityId, "deleteUser - authority must be set");
    Assert.notNull(userId, "deleteUser - userId must be set");

    String uri =
        UriComponentsBuilder.fromUriString(serviceConfiguration.getUrlPrefix() + DELETE_ENDPOINT)
            .build()
            .toUriString();

    restTemplateFactory.getInstance().delete(uri, localAuthorityId, userId);
  }

  public void requestPasswordReset(Integer localAuthorityId, Integer id) {
    Assert.notNull(id, "requestPasswordReset - localAuthorityId must be provided");
    Assert.notNull(id, "requestPasswordReset - id must not be null");

    restTemplateFactory
        .getInstance()
        .getForEntity(
            serviceConfiguration.getUrlPrefix() + REQUEST_PASSWORD_RESET_ENDPOINT,
            String.class,
            localAuthorityId,
            id);
  }

  public UserResponse updatePassword(String uuid, String password, String passwordConfirm) {
    Assert.notNull(uuid, "updatePassword - uuid must be provided");
    Assert.notNull( ***REMOVED***);
    Assert.notNull( ***REMOVED***);

    String uri = serviceConfiguration.getUrlPrefix() + UPDATE_PASSWORD_ENDPOINT;
    Password passwords = new Password();
    passwords.setPassword(password);
    passwords.setPasswordConfirm(passwordConfirm);

    HttpEntity<Password> requestBody = new HttpEntity<>(passwords);

    return this.restTemplateFactory
        .getInstance()
        .patchForObject(uri, requestBody, UserResponse.class, uuid);
  }
}
