package uk.gov.dft.bluebadge.webapp.la.client.payment;

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
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;

@Slf4j
@Service
public class PaymentApiClient extends BaseApiClient {

  private final RestTemplate restTemplate;

  @Autowired
  public PaymentApiClient(
      @Qualifier("paymentServiceRestTemplate") RestTemplate paymentServiceRestTemplate) {
    this.restTemplate = paymentServiceRestTemplate;
  }

  /**
   * Upload local authority gov uk pay secret
   *
   * @param request contains a new value for the gov uk pay secret.
   */
  public void updateLocalAuthoritySecret(
      final String localAuthorityShortCode, final GovPayProfile request) {
    log.debug("Update local authority gov uk pay secret.");

    String uri =
        UriComponentsBuilder.fromUriString("/localAuthorities/{shortCode}").build().toUriString();

    HttpEntity<GovPayProfile> httpRequest = new HttpEntity<>(request);

    try {
      restTemplate.postForEntity(uri, httpRequest, CommonResponse.class, localAuthorityShortCode);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }
}
