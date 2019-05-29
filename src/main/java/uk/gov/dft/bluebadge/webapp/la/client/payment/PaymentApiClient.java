package uk.gov.dft.bluebadge.webapp.la.client.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;

@Slf4j
@Service
public class PaymentApiClient {

  static class Endpoints {
    private Endpoints() {}

    static final String UPDATE_LOCAL_AUTHORITY_SECRET_ENDPOINT =
        "/payments/localAuthorities/{shortCode}";
  }

  private final RestTemplate restTemplate;

  @Autowired
  public PaymentApiClient(@Qualifier("paymentServiceRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Upload local authority gov uk pay secret
   *
   * @param payProfile contains a new value for the gov uk pay secret.
   */
  public void updateLocalAuthoritySecret(
      final String localAuthorityShortCode, final GovPayProfile payProfile) {
    log.debug("Update gov uk pay secret for local authority {}.", localAuthorityShortCode);
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    Assert.notNull(payProfile, "payProfile should not be null");

    HttpEntity<GovPayProfile> httpRequest = new HttpEntity<>(payProfile);
    restTemplate.postForEntity(
        PaymentApiClient.Endpoints.UPDATE_LOCAL_AUTHORITY_SECRET_ENDPOINT,
        httpRequest,
        CommonResponse.class,
        localAuthorityShortCode);
  }
}
