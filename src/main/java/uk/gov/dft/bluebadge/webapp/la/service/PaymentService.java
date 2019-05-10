package uk.gov.dft.bluebadge.webapp.la.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.payment.PaymentApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;

@Service
@Slf4j
public class PaymentService {

  private PaymentApiClient paymentApiClient;

  @Autowired
  public PaymentService(PaymentApiClient paymentApiClient) {
    this.paymentApiClient = paymentApiClient;
  }

  public void updateLocalAuthoritySecret(
      final String localAuthorityShortCode, final GovPayProfile payProfile) {
    log.debug("Update local authority gov uk notify secret.");
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    Assert.notNull(payProfile, "payProfile should not be null");
    paymentApiClient.updateLocalAuthoritySecret(localAuthorityShortCode, payProfile);
  }
}
