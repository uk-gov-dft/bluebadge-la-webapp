package uk.gov.dft.bluebadge.webapp.la.client.payment;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.webapp.la.client.payment.model.GovPayProfile;

public class PaymentApiClientTest {
  private static final String TEST_URI = "http://justtesting:7777/test";
  private static final String UPDATE_LOCAL_PAY_SECRET_ENDPOINT = "/payments/localAuthorities/";

  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final String PAY_API_KEY = "payApiKey0123";
  private static final GovPayProfile PAY_PROFILE =
      GovPayProfile.builder().apiKey(PAY_API_KEY).build();

  private MockRestServiceServer mockServer;
  private PaymentApiClient client;

  @Before
  public void setUp() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    client = new PaymentApiClient(restTemplate);
  }

  @Test
  public void updateLocalPaySecret_shouldWork() {
    mockServer
        .expect(
            once(),
            requestTo(TEST_URI + UPDATE_LOCAL_PAY_SECRET_ENDPOINT + LOCAL_AUTHORITY_SHORT_CODE))
        .andExpect(method(HttpMethod.POST))
        .andExpect(jsonPath("apiKey", equalTo(PAY_API_KEY)))
        .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

    client.updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, PAY_PROFILE);
  }

  @Test(expected = HttpServerErrorException.InternalServerError.class)
  @SneakyThrows
  public void updateLocalPaySecret_shouldThrowException_when500FromServer() {
    mockServer
        .expect(
            once(),
            requestTo(TEST_URI + UPDATE_LOCAL_PAY_SECRET_ENDPOINT + LOCAL_AUTHORITY_SHORT_CODE))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withServerError());

    client.updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, PAY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
      updateLocalPaySecret_shouldThrowIllegalArgumentException_whenLocalAuthorityShortCodeIsNull() {
    client.updateLocalAuthoritySecret(null, PAY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalPaySecret_shouldThrowIllegalArgumentException_whenProfileIsNull() {
    client.updateLocalAuthoritySecret(LOCAL_AUTHORITY_SHORT_CODE, null);
  }
}
