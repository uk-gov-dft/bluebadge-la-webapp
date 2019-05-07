package uk.gov.dft.bluebadge.webapp.la.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.payment.PaymentApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;

public class PaymentServiceTest {

  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final String PAY_API_KEY = "payApiKey0123";
  private static final GovPayProfile PAY_PROFILE =
      GovPayProfile.builder().apiKey(PAY_API_KEY).build();

  private PaymentService paymentService;

  @Mock PaymentApiClient paymentApiClientMock;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    paymentService = new PaymentService(paymentApiClientMock);
  }

  @Test
  public void updateLocalAuthoritySecret_shouldWork() {
    paymentService.updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, PAY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
      updateLocalAuthoritySecret_shouldThrowIllegalArgumentException_whenLocalAuthorityShortCodeIsNull() {
    paymentService.updateLocalAuthoritySecret(null, PAY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalAuthoritySecret_shouldThrowIllegalArgumentException_whenProfileIsNull() {
    paymentService.updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, null);
  }
}
