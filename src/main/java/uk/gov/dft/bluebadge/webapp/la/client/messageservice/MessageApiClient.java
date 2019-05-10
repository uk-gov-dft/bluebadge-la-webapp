package uk.gov.dft.bluebadge.webapp.la.client.messageservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;

@Slf4j
@Service
public class MessageApiClient extends BaseApiClient {

  static class Endpoints {
    private Endpoints() {}

    static final String UPDATE_LOCAL_NOTIFY_SECRET_ENDPOINT =
        "/messages/localAuthorities/{shortCode}";
  }

  private final RestTemplate restTemplate;

  @Autowired
  public MessageApiClient(@Qualifier("messageServiceRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Upload local authority gov uk notify secret.
   *
   * @param notifyProfile contains a new value for the gov uk notify secret.
   */
  public void updateLocalNotifySecret(
      final String localAuthorityShortCode, final NotifyProfile notifyProfile) {
    log.debug(
        "Update gov uk notify secret for local authority with shortcode {}.",
        localAuthorityShortCode);
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    Assert.notNull(notifyProfile, "notifyProfile should not be null");

    HttpEntity<NotifyProfile> httpRequest = new HttpEntity<>(notifyProfile);

    try {
      restTemplate.postForEntity(
          Endpoints.UPDATE_LOCAL_NOTIFY_SECRET_ENDPOINT,
          httpRequest,
          CommonResponse.class,
          localAuthorityShortCode);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }
}
