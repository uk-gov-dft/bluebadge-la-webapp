package uk.gov.dft.bluebadge.webapp.la.client.usermanagement;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.Password;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;

@Service
public class SetPasswordApiClient extends BaseApiClient {

  static class Endpoints {
    private Endpoints() {}

    static final String UPDATE_P_ENDPOINT = "/user/password/{uuid}";
  }

  private RestTemplate restTemplate;

  @Autowired
  public SetPasswordApiClient(
      @Qualifier("setPasswordRestTemplate") RestTemplate setPasswordRestTemplate) {
    this.restTemplate = setPasswordRestTemplate;
  }

  public User updatePassword(String uuid, String password, String passwordConfirm) {
    Assert.notNull(uuid, "updatePassword - uuid must be provided");
    // Do NOT assert password not null.  Rely on API to return correct error message.

    Password passwords = new Password();
    passwords.setPassword(password);
    passwords.setPasswordConfirm(passwordConfirm);

    HttpEntity<Password> requestBody = new HttpEntity<>(passwords);

    try {
      return Objects.requireNonNull(
              this.restTemplate.patchForObject(
                  Endpoints.UPDATE_P_ENDPOINT, requestBody, UserResponse.class, uuid))
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }
}
