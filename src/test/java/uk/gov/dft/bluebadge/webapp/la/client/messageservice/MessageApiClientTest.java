package uk.gov.dft.bluebadge.webapp.la.client.messageservice;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.webapp.la.client.common.CommonResponseErrorHandler;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.NotifyProfile;
import uk.gov.dft.bluebadge.webapp.la.client.messageservice.model.TemplateName;

public class MessageApiClientTest {
  private static final String TEST_URI = "http://justtesting:7777/test";
  private static final String UPDATE_LOCAL_NOTIFY_SECRET_ENDPOINT = "/messages/localAuthorities/";

  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final String NOTIFY_API_KEY = "notifyApiKey0123";
  private static final String APPLICATION_SUBMITTED_TEMPLATE_ID =
      "applicationSubmittedTemplateId0123";
  private static final NotifyProfile NOTIFY_PROFILE =
      NotifyProfile.builder()
          .apiKey(NOTIFY_API_KEY)
          .templates(
              ImmutableMap.of(
                  TemplateName.APPLICATION_SUBMITTED, APPLICATION_SUBMITTED_TEMPLATE_ID))
          .build();

  private MockRestServiceServer mockServer;
  private MessageApiClient client;

  @Before
  public void setUp() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    restTemplate.setErrorHandler(new CommonResponseErrorHandler(new ObjectMapper()));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    client = new MessageApiClient(restTemplate);
  }

  @Test
  public void updateLocalNotifySecret_shouldWork() {
    mockServer
        .expect(
            once(),
            requestTo(TEST_URI + UPDATE_LOCAL_NOTIFY_SECRET_ENDPOINT + LOCAL_AUTHORITY_SHORT_CODE))
        .andExpect(method(HttpMethod.POST))
        .andExpect(jsonPath("apiKey", equalTo(NOTIFY_API_KEY)))
        .andExpect(
            jsonPath("templates.APPLICATION_SUBMITTED", equalTo(APPLICATION_SUBMITTED_TEMPLATE_ID)))
        .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

    client.updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, NOTIFY_PROFILE);
  }

  @Test(expected = HttpServerErrorException.InternalServerError.class)
  @SneakyThrows
  public void updateLocalNotifySecret_shouldThrowException_when500FromServer() {
    mockServer
        .expect(
            once(),
            requestTo(TEST_URI + UPDATE_LOCAL_NOTIFY_SECRET_ENDPOINT + LOCAL_AUTHORITY_SHORT_CODE))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withServerError());

    client.updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, NOTIFY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
      updateLocalNotifySecret_shouldThrowIllegalArgumentException_whenLocalAuthorityShortCodeIsNull() {
    client.updateLocalNotifySecret(null, NOTIFY_PROFILE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalNotifySecret_shouldThrowIllegalArgumentException_whenProfileIsNull() {
    client.updateLocalNotifySecret(LOCAL_AUTHORITY_SHORT_CODE, null);
  }
}
