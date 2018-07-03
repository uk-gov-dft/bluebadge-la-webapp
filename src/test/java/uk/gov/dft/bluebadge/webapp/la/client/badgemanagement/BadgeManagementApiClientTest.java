package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

public class BadgeManagementApiClientTest {

  private static final String SCHEME = "http";
  private static final String HOST = "localhost";
  private static final Integer PORT = 1111;
  private static final String CONTEXT = "context";
  private static final String API = "badges";

  private static final String BASE_ENDPOINT =
      String.format("%s://%s:%d/%s/%s", SCHEME, HOST, PORT, CONTEXT, API);

  private static final Integer SAMPLE_SIZE = 5;

  private String NAME = "myname";
  private String NI_NUMBER = "aa101010a";
  private String BADGE_NUMBER = "12345";

  @Mock private RestTemplateFactory mockRestTemplateFactory;

  private BadgeManagementApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  private BadgesResponse badgesResponse;
  private String badgesResponseBody = "";

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    when(mockRestTemplateFactory.getInstance()).thenReturn(restTemplate);

    ServiceConfiguration serviceConfiguration = buildServiceConfiguration();

    client = new BadgeManagementApiClient(mockRestTemplateFactory, serviceConfiguration);
    badgesResponse = buildResponse(SAMPLE_SIZE);

    badgesResponseBody = objectMapper.writeValueAsString(badgesResponse);
  }

  @Test
  public void findBadges_shouldReturnAllBadges_whenNoParametersAreGiven() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT))
        .andRespond(withSuccess(badgesResponseBody, MediaType.APPLICATION_JSON));
    List<BadgeSummary> badgeSummaries = client.findBadges(null, null, null);
    assertThat(badgeSummaries).containsExactlyElementsOf(badgeSummaries);
  }

  @Test
  public void findBadges_shouldReturnFilteredBadges_whenNameParameterIsGiven() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "?name=" + NAME))
        .andRespond(withSuccess(badgesResponseBody, MediaType.APPLICATION_JSON));
    List<BadgeSummary> badgeSummaries = client.findBadges(NAME, null, null);
    assertThat(badgeSummaries).containsExactlyElementsOf(badgeSummaries);
  }

  @Test
  public void findBadges_shouldReturnFilteredBadges_whenNiNumberParameterIsGiven() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "?ni=" + NI_NUMBER))
        .andRespond(withSuccess(badgesResponseBody, MediaType.APPLICATION_JSON));
    List<BadgeSummary> badgeSummaries = client.findBadges(null, NI_NUMBER, null);
    assertThat(badgeSummaries).containsExactlyElementsOf(badgeSummaries);
  }

  @Test
  public void findBadges_shouldReturnFilteredBadges_whenBadNumberNumberParameterIsGiven() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "?badgeNumber=" + BADGE_NUMBER))
        .andRespond(withSuccess(badgesResponseBody, MediaType.APPLICATION_JSON));
    List<BadgeSummary> badgeSummaries = client.findBadges(null, null, BADGE_NUMBER);
    assertThat(badgeSummaries).containsExactlyElementsOf(badgeSummaries);
  }

  @Test
  public void findBadges_shouldReturnFilteredBadges_whenAllParametersAreGiven() {
    mockServer
        .expect(
            once(),
            requestTo(
                BASE_ENDPOINT
                    + "?name="
                    + NAME
                    + "&badgeNumber="
                    + BADGE_NUMBER
                    + "&ni="
                    + NI_NUMBER))
        .andRespond(withSuccess(badgesResponseBody, MediaType.APPLICATION_JSON));
    List<BadgeSummary> badgeSummaries = client.findBadges(NAME, NI_NUMBER, BADGE_NUMBER);
    assertThat(badgeSummaries).containsExactlyElementsOf(badgeSummaries);
  }

  @Test
  public void retrieveBadge_shouldRetrieveTheSpecifiedBadge() throws JsonProcessingException {
    String BADGE_NUMBER = "12345";
    Badge badge = new Badge();
    badge.badgeNumber(BADGE_NUMBER);
    badge.contactNumber("0111 1111 111");
    badge.eligibilityCode("PIP");
    BadgeResponse badgeResponse = new BadgeResponse();
    badgeResponse.data(badge);

    String body = objectMapper.writeValueAsString(badgeResponse);

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "/" + BADGE_NUMBER))
        .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));
    Badge retrievedBadge = client.retrieveBadge(BADGE_NUMBER);
    assertThat(retrievedBadge).isEqualTo(badge);
  }

  private BadgesResponse buildResponse(int numberOfBadges) {
    BadgesResponse badgesResponse = new BadgesResponse();
    badgesResponse.data(Lists.newArrayList());

    for (int i = 1; i <= numberOfBadges; i++) {
      BadgeSummary badgeSummary1 = new BadgeSummary();
      badgeSummary1.badgeNumber(String.valueOf(i));
      badgeSummary1.localAuthorityId(1);
      badgeSummary1.localAuthorityName("Manchester" + i);
      badgeSummary1.name("john doe " + i);
      badgeSummary1.nationalInsurance("AA10101" + i + "A");
      badgeSummary1.partyTypeCode("person" + i);
      badgeSummary1.partyTypeDescription("A person" + i);
      badgesResponse.getData().add(badgeSummary1);
    }

    return badgesResponse;
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
