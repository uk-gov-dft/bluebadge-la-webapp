package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.*;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

public class BadgeManagementApiClientTest {

  private static final String SCHEME = "http";
  private static final String HOST = "localhost";
  private static final Integer PORT = 1111;
  private static final String CONTEXT = "context";
  private static final String API = "badges";

  private static final String BASE_ENDPOINT =
      String.format("%s://%s:%d/%s/%s", SCHEME, HOST, PORT, CONTEXT, API);

  private static final String BADGE_NUMBER = "12345";
  private static final Badge BADGE =
      new Badge()
          .badgeNumber(BADGE_NUMBER)
          .eligibilityCode("PIP")
          .localAuthorityRef("localAuthorityRef");
  private static final String POST_CODE = "L329PA";

  @Mock private RestTemplateFactory mockRestTemplateFactory;

  private BadgeManagementApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    when(mockRestTemplateFactory.getInstance()).thenReturn(restTemplate);

    ServiceConfiguration serviceConfiguration = buildServiceConfiguration();

    client = new BadgeManagementApiClient(mockRestTemplateFactory, serviceConfiguration);
  }

  @Test
  public void orderBlueBadges_ShouldReturnBlueBadgeNumber_WhenValidInput() throws Exception {
    List<String> badgeNumbers = Lists.newArrayList("123");
    BadgeNumbersResponse badgeNumbersResponse = new BadgeNumbersResponse().data(badgeNumbers);
    String badgeNumbersResponseBody = objectMapper.writeValueAsString(badgeNumbersResponse);
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(badgeNumbersResponseBody, MediaType.APPLICATION_JSON));
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    List<String> result = client.orderBlueBadges(badgeOrderRequest);
    assertThat(result).containsExactlyElementsOf(badgeNumbers);
  }

  @Test(expected = BadRequestException.class)
  public void orderBlueBadges_ShouldThrowBadRequestException_WhenInvalidInput() throws Exception {
    String commonResponseBody = objectMapper.writeValueAsString(new CommonResponse());

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString()))
        .andRespond(
            withBadRequest().body(commonResponseBody).contentType(MediaType.APPLICATION_JSON));
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    client.orderBlueBadges(badgeOrderRequest);
  }

  @Test(expected = HttpServerErrorException.class)
  public void orderBlueBadges_ShouldThrowException_When500() throws Exception {
    String commonResponseBody = objectMapper.writeValueAsString(new CommonResponse());

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString()))
        .andRespond(withServerError());
    BadgeOrderRequest badgeOrderRequest = new BadgeOrderRequest();
    client.orderBlueBadges(badgeOrderRequest);
  }

  @Test
  public void retrieveBadge_ShouldRetrieveTheSpecifiedBadge() throws JsonProcessingException {
    BadgeResponse badgeResponse = new BadgeResponse().data(BADGE);

    String body = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "/" + BADGE_NUMBER))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON_UTF8));
    Badge retrievedBadge = client.retrieveBadge(BADGE_NUMBER);
    assertThat(retrievedBadge).isEqualTo(BADGE);
  }

  @Test
  public void retrieveBadge_ShouldThrowException_When404() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    try {
      mockServer
          .expect(once(), requestTo(BASE_ENDPOINT + "/" + BADGE_NUMBER))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));
      client.retrieveBadge(BADGE_NUMBER);
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  @Test
  public void findABadgeByPostcode_ShouldRetrieveAListOfBadges() throws JsonProcessingException {
    BadgeSummary b1 = new BadgeSummary();
    BadgeSummary b2 = new BadgeSummary();
    List<BadgeSummary> badges = Lists.newArrayList(b1, b2);
    BadgesResponse badgeResponse = new BadgesResponse().data(badges);

    String body = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "?postCode=" + POST_CODE))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON_UTF8));

    List<BadgeSummary> retrievedBadges = client.findBadgeByPostCode(POST_CODE);
    assertThat(retrievedBadges).isEqualTo(badges);
  }

  @Test
  public void findABadgeByPostCode_ShouldThrowException_When400() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    try {
      mockServer
          .expect(once(), requestTo(BASE_ENDPOINT + "?postCode=" + POST_CODE))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  private ServiceConfiguration buildServiceConfiguration() {
    ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
    serviceConfiguration.setScheme(SCHEME);
    serviceConfiguration.setHost(HOST);
    serviceConfiguration.setPort(PORT);
    serviceConfiguration.setContextpath(CONTEXT);
    return serviceConfiguration;
  }
}
