package uk.gov.dft.bluebadge.webapp.la.client.applications;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationsApiClientTest extends ApplicationTestData {

  private static final String TEST_URI = "http://justtesting:8787/test";
  private static final String APPLICATIONS_ENDPOINT = TEST_URI + "/applications";

  private static final ApplicationTypeCodeField FIND_PARAM_APPLICATION_TYPE =
      ApplicationTypeCodeField.NEW;
  private static final String FIND_PARAM_NAME = "name";
  private static final String FIND_PARAM_POSTCODE = "M1";
  private static final String FIND_PARAM_POSTCODE_WRONG = "WRONG";
  private static final LocalDate FIND_PARAM_FROM = LocalDate.now().minusDays(7);
  private static final LocalDate FIND_PARAM_TO = LocalDate.now().minusDays(2);
  private static final String FIND_PARAM_FROM_API =
      LocalDate.now().minusDays(7).format(ISO_LOCAL_DATE);
  private static final String FIND_PARAM_TO_API =
      LocalDate.now().minusDays(2).format(ISO_LOCAL_DATE);

  private ApplicationsApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @Before
  public void setUp() {
    initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    client = new ApplicationsApiClient(restTemplate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void find_shouldThrowIllegalArgumentException_WhenNoParamIsSet() {
    client.find(
        Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
  }

  @Test
  public void find_ShouldReturnResults_WhenApplicationTypeIsSetAndThereAreResults()
      throws Exception {
    ApplicationSummaryResponse applicationSummaryResponse =
        new ApplicationSummaryResponse().data(APPLICATION_SUMMARIES);
    String applicationResponseBody = objectMapper.writeValueAsString(applicationSummaryResponse);

    mockServer
        .expect(
            once(),
            requestTo(
                APPLICATIONS_ENDPOINT + "?applicationTypeCode=" + FIND_PARAM_APPLICATION_TYPE))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(applicationResponseBody, MediaType.APPLICATION_JSON));

    List<ApplicationSummary> results =
        client.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(FIND_PARAM_APPLICATION_TYPE));

    assertThat(results).containsExactlyElementsOf(APPLICATION_SUMMARIES);
  }

  @Test
  public void find_ShouldReturnResults_WhenAllParamsAreSetAndThereAreResults() throws Exception {
    ApplicationSummaryResponse applicationSummaryResponse =
        new ApplicationSummaryResponse().data(APPLICATION_SUMMARIES);
    String applicationResponseBody = objectMapper.writeValueAsString(applicationSummaryResponse);

    mockServer
        .expect(
            once(),
            requestTo(
                APPLICATIONS_ENDPOINT
                    + "?name="
                    + FIND_PARAM_NAME
                    + "&postcode="
                    + FIND_PARAM_POSTCODE
                    + "&from="
                    + FIND_PARAM_FROM_API
                    + "&to="
                    + FIND_PARAM_TO_API
                    + "&applicationTypeCode="
                    + FIND_PARAM_APPLICATION_TYPE))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(applicationResponseBody, MediaType.APPLICATION_JSON));

    List<ApplicationSummary> results =
        client.find(
            Optional.of(FIND_PARAM_NAME),
            Optional.of(FIND_PARAM_POSTCODE),
            Optional.of(FIND_PARAM_FROM),
            Optional.of(FIND_PARAM_TO),
            Optional.of(FIND_PARAM_APPLICATION_TYPE));

    assertThat(results).containsExactlyElementsOf(APPLICATION_SUMMARIES);
  }

  @Test
  public void find_ShouldThrowException_When400() throws Exception {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);

    try {
      mockServer
          .expect(
              once(), requestTo(APPLICATIONS_ENDPOINT + "?postcode=" + FIND_PARAM_POSTCODE_WRONG))
          .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

      client.find(
          Optional.empty(),
          Optional.of(FIND_PARAM_POSTCODE_WRONG),
          Optional.empty(),
          Optional.empty(),
          Optional.empty());
    } catch (BadRequestException ex) {
      assertThat(ex.getCommonResponse()).isEqualTo(commonResponse);
    }
  }
}
