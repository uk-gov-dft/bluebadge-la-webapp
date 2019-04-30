package uk.gov.dft.bluebadge.webapp.la.client.messageservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;

@Slf4j
@Service
public class MessageApiClient extends BaseApiClient {

  private final RestTemplate restTemplate;

  @Autowired
  public MessageApiClient(@Qualifier("messageServiceRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Upload local authority gov uk notify secret.
   *
   * @param request contains a new value for the gov uk notify secret.
   */
  public void updateLocalNotifySecret(
      final String localAuthorityShortCode, final NotifyProfile request) {
    log.debug("Update local authority gov uk notify secret.");

    String uri =
        UriComponentsBuilder.fromUriString("/localAuthorities/{shortCode}").build().toUriString();

    HttpEntity<NotifyProfile> httpRequest = new HttpEntity<>(request);

    try {
      restTemplate.postForEntity(uri, httpRequest, CommonResponse.class, localAuthorityShortCode);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }
}
