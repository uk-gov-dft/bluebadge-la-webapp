package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.*;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;

public class BadgeManagementApiClientTest {
  public static final String TEST_URI = "http://justtesting:8787/test";
  private static final String BADGES_ENDPOINT = TEST_URI + "/badges";

  private static final String BADGE_NUMBER = "12345";
  private static final Badge BADGE =
      new Badge()
          .badgeNumber(BADGE_NUMBER)
          .eligibilityCode("PIP")
          .localAuthorityRef("localAuthorityRef");
  private static final String POST_CODE = "L329PA";
  private static final String NAME = "jason";
  public static final String CANCEL_REASON_CODE = "REVOKE";

  private BadgeManagementApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock RestTemplate mockTemplate;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    client = new BadgeManagementApiClient(restTemplate);
  }

  @Test
  public void orderBlueBadges_ShouldReturnBlueBadgeNumber_WhenValidInput() throws Exception {
    List<String> badgeNumbers = Lists.newArrayList("123");
    BadgeNumbersResponse badgeNumbersResponse = new BadgeNumbersResponse().data(badgeNumbers);
    String badgeNumbersResponseBody = objectMapper.writeValueAsString(badgeNumbersResponse);
    mockServer
        .expect(once(), requestTo(BADGES_ENDPOINT))
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
        .expect(once(), requestTo(BADGES_ENDPOINT))
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
        .expect(once(), requestTo(BADGES_ENDPOINT))
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
        .expect(once(), requestTo(BADGES_ENDPOINT + "/" + BADGE_NUMBER))
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
          .expect(once(), requestTo(BADGES_ENDPOINT + "/" + BADGE_NUMBER))
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
        .expect(once(), requestTo(BADGES_ENDPOINT + "?postCode=" + POST_CODE))
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
          .expect(once(), requestTo(BADGES_ENDPOINT + "?postCode=" + POST_CODE))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  @Test
  public void findABadgeByName_ShouldRetrieveAListOfBadges() throws JsonProcessingException {
    BadgeSummary b1 = new BadgeSummary();
    List<BadgeSummary> badges = Lists.newArrayList(b1);
    BadgesResponse badgeResponse = new BadgesResponse().data(badges);

    String body = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(once(), requestTo(BADGES_ENDPOINT + "?name=" + NAME))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON_UTF8));

    List<BadgeSummary> retrievedBadges = client.findBadgeByName(NAME);
    assertThat(retrievedBadges).isEqualTo(badges);
  }

  @Test
  public void findABadgeByName_ShouldThrowException_When400() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    try {
      mockServer
          .expect(once(), requestTo(BADGES_ENDPOINT + "?name=" + NAME))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

      client.findBadgeByName(NAME);
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  @Test
  public void cancellingABadge_ShouldChangeStatusOfABadgeToCancelled() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String response = objectMapper.writeValueAsString(commonResponse);

    BadgeCancelRequest badgeCancelRequest = new BadgeCancelRequest();
    badgeCancelRequest.setBadgeNumber(BADGE_NUMBER);
    badgeCancelRequest.setCancelReasonCode(CANCEL_REASON_CODE);
    String requestBody = objectMapper.writeValueAsString(badgeCancelRequest);

    String uri = BADGES_ENDPOINT + "/" + BADGE_NUMBER + "/cancellations";

    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().json(requestBody))
        .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

    client.cancelBadge(BADGE_NUMBER, CANCEL_REASON_CODE);
  }

  @Test(expected = HttpClientErrorException.class)
  public void cancellingBadge_ShouldThrowException_whenNoRequestBodyIsPassed() {

    client = new BadgeManagementApiClient(mockTemplate);
    when(mockTemplate.exchange(any(), any(), any(), eq(CommonResponse.class), eq(BADGE_NUMBER)))
            .thenThrow(new HttpClientErrorException(HttpStatus.GATEWAY_TIMEOUT));

    client.cancelBadge(BADGE_NUMBER, CANCEL_REASON_CODE);
  }

}
