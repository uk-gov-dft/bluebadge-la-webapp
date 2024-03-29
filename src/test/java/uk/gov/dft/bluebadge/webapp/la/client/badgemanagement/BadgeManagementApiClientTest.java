package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliverToCodeField.COUNCIL;
import static uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.DeliveryOptionCodeField.STAND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeCancelRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumberResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.CommonResponseErrorHandler;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;
import uk.gov.dft.bluebadge.webapp.la.service.enums.ReplaceReason;

public class BadgeManagementApiClientTest {
  private static final String TEST_URI = "http://justtesting:8787/test";
  private static final String BADGES_ENDPOINT = TEST_URI + "/badges";

  private static final String BADGE_NUMBER = "12345";
  private static final Badge BADGE =
      new Badge()
          .badgeNumber(BADGE_NUMBER)
          .eligibilityCode("PIP")
          .localAuthorityRef("localAuthorityRef");
  private static final String POST_CODE = "L329PA";
  private static final String NAME = "jason";
  private static final CancelReason CANCEL_REASON_CODE = CancelReason.REVOKE;
  private static final Integer PAGE_NUM = 2;
  private static final Integer PAGE_SIZE = 14;
  private static final PagingInfo PAGING_INFO = new PagingInfo();

  static {
    PAGING_INFO.setPageNum(PAGE_NUM);
    PAGING_INFO.setPageSize(PAGE_SIZE);
  }

  private BadgeManagementApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock RestTemplate mockTemplate;

  @Before
  public void setUp() {
    initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    restTemplate.setErrorHandler(new CommonResponseErrorHandler(objectMapper));
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
    mockServer
        .expect(once(), requestTo(BADGES_ENDPOINT))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString()))
        .andRespond(withServerError());
    client.orderBlueBadges(new BadgeOrderRequest());
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
        .expect(
            once(),
            requestTo(
                BADGES_ENDPOINT
                    + "?postCode="
                    + POST_CODE
                    + "&pageSize="
                    + PAGE_SIZE
                    + "&pageNum="
                    + PAGE_NUM))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON_UTF8));

    BadgesResponse retrievedBadgesResponse = client.findBadgeByPostCode(POST_CODE, PAGING_INFO);
    assertThat(retrievedBadgesResponse.getData()).isEqualTo(badges);
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
  public void exportBadgesByLa_shouldReturnByteContent_WhenRequestIsSuccessful() {
    String uri = BADGES_ENDPOINT + "?laShortCode=" + "ABERD";

    byte[] byteContent = "Any String".getBytes();
    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.GET))
        .andExpect(header("Accept", "application/zip"))
        .andRespond(withSuccess(byteContent, MediaType.parseMediaType("application/zip")));

    ResponseEntity<byte[]> response = client.exportBadgesByLa("ABERD");
    assertThat(response.getBody()).isEqualTo(byteContent);
  }

  @Test(expected = IllegalArgumentException.class)
  public void exportBadgesByLa_shouldThrowIllegalArgumentExcepton_whenLaShortCodeIsNull() {
    client.exportBadgesByLa(null);
  }

  @Test(expected = HttpServerErrorException.class)
  public void exportBadgesByLa_ShouldThrowException_When500() {
    String uri = BADGES_ENDPOINT + "?laShortCode=" + "ABERD";
    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withServerError());
    client.exportBadgesByLa("ABERD");
  }

  @Test(expected = BadRequestException.class)
  @SneakyThrows
  public void exportBadgesByLa_ShouldThrowException_When400() {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    String uri = BADGES_ENDPOINT + "?laShortCode=" + "ABERD";
    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));
    client.exportBadgesByLa("ABERD");
  }

  @Test
  public void findABadgeByName_ShouldRetrieveAListOfBadges() throws JsonProcessingException {
    BadgeSummary b1 = new BadgeSummary();
    List<BadgeSummary> badges = Lists.newArrayList(b1);
    BadgesResponse badgeResponse = new BadgesResponse().data(badges);

    String body = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(
            once(),
            requestTo(
                BADGES_ENDPOINT
                    + "?name="
                    + NAME
                    + "&pageSize="
                    + PAGE_SIZE
                    + "&pageNum="
                    + PAGE_NUM))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON_UTF8));

    BadgesResponse retrievedBadgesResponse = client.findBadgeByName(NAME, PAGING_INFO);
    assertThat(retrievedBadgesResponse.getData()).isEqualTo(badges);
  }

  @Test
  public void findABadgeByName_ShouldThrowException_When400() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    try {
      mockServer
          .expect(
              once(),
              requestTo(
                  BADGES_ENDPOINT
                      + "?name="
                      + NAME
                      + "&pageSize="
                      + PAGE_SIZE
                      + "&pageNum="
                      + PAGE_NUM))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

      client.findBadgeByName(NAME, PAGING_INFO);
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  @Test
  public void cancellingABadge_ShouldChangeStatusOfABadgeToCancelled() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String response = objectMapper.writeValueAsString(commonResponse);

    BadgeCancelRequest badgeCancelRequest =
        BadgeCancelRequest.builder()
            .badgeNumber(BADGE_NUMBER)
            .cancelReasonCode(CANCEL_REASON_CODE)
            .build();

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
    when(mockTemplate.postForEntity(any(), any(), eq(CommonResponse.class), eq(BADGE_NUMBER)))
        .thenThrow(new HttpClientErrorException(HttpStatus.GATEWAY_TIMEOUT));

    client.cancelBadge(BADGE_NUMBER, CANCEL_REASON_CODE);
  }

  @Test
  public void deleteBadge() {
    String uri = BADGES_ENDPOINT + "/" + BADGE_NUMBER;

    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withSuccess());

    client.deleteBadge(BADGE_NUMBER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteBadge_exceptionIfBadgeNumberNotSet() {
    client.deleteBadge(null);
  }

  @Test
  public void deleteBadge_ShouldThrowException_When404() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);
    mockServer
        .expect(once(), requestTo(BADGES_ENDPOINT + "/" + BADGE_NUMBER))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

    try {
      client.deleteBadge(BADGE_NUMBER);
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }

  @Test(expected = HttpServerErrorException.class)
  public void deleteBadge_ShouldThrowException_When500() {
    mockServer
        .expect(once(), requestTo(BADGES_ENDPOINT + "/" + BADGE_NUMBER))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withServerError());
    client.deleteBadge(BADGE_NUMBER);
  }

  @Test
  @SneakyThrows
  public void replaceBadge() {
    String uri = BADGES_ENDPOINT + "/" + BADGE_NUMBER + "/replacements";
    String NEW_BADGE_NUMBER = "ABC";

    BadgeReplaceRequest request =
        BadgeReplaceRequest.builder()
            .badgeNumber(BADGE_NUMBER)
            .replaceReasonCode(ReplaceReason.LOST)
            .deliverToCode(COUNCIL)
            .deliveryOptionCode(STAND)
            .build();
    String requestBody = objectMapper.writeValueAsString(request);

    BadgeNumberResponse badgeResponse =
        BadgeNumberResponse.builder().data(NEW_BADGE_NUMBER).build();
    String response = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().json(requestBody))
        .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

    String newBadgeNumber = client.replaceBadge(request);
    assertEquals(NEW_BADGE_NUMBER, newBadgeNumber);
  }

  @Test(expected = BadRequestException.class)
  @SneakyThrows
  public void replaceBadge_throwException_whenNoRequestBodyIsPassed() {
    String uri = BADGES_ENDPOINT + "/" + BADGE_NUMBER + "/replacements";

    BadgeReplaceRequest request =
        BadgeReplaceRequest.builder()
            .badgeNumber(BADGE_NUMBER)
            .replaceReasonCode(ReplaceReason.LOST)
            .deliverToCode(COUNCIL)
            .deliveryOptionCode(STAND)
            .build();

    String commonResponseBody = objectMapper.writeValueAsString(new CommonResponse());

    mockServer
        .expect(once(), requestTo(uri))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andRespond(
            withBadRequest().body(commonResponseBody).contentType(MediaType.APPLICATION_JSON));

    client.replaceBadge(request);
  }
}
