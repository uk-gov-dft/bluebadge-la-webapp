package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTRY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.COUNTRY_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL_INVALID;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.DIFFERENT_SERVICE_SIGNPOST_URL_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.LOCAL_AUTHORITY_INVALID_VALUE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NATION;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.NATION_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.POSTCODE;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.POSTCODE_PARAM;
import static uk.gov.dft.bluebadge.webapp.la.testdata.LocalAuthorityTestData.WEB_SITE_URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceDataResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;

public class ReferenceDataApiClientTest {
  private static final String TEST_URI = "http://justtesting:8787/test/";

  private static final String BASE_ENDPOINT = TEST_URI + "reference-data";
  private static final String AUTHORITIES_PATH = "/authorities/";
  private static final String COUNCILS_PATH = "/councils/";

  private static final String SHORT_CODE_VALUE = "ABERD";

  private ReferenceDataApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(TEST_URI));
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    client = new ReferenceDataApiClient(restTemplate);
  }

  @Test
  public void retrieveReferenceDataWithADomain_shouldReturnReferenceDataForThatDomain()
      throws Exception {
    ReferenceData referenceData1 = ReferenceDataUtils.buildReferenceData("groupShortCode", 1);
    ReferenceData referenceData2 = ReferenceDataUtils.buildReferenceData("groupShortCode", 2);
    ReferenceData referenceData3 = ReferenceDataUtils.buildReferenceData("groupShortCode", 3);
    List<ReferenceData> referenceDataList =
        Lists.newArrayList(referenceData1, referenceData2, referenceData3);
    ReferenceDataResponse response = new ReferenceDataResponse().data(referenceDataList);
    String responseBody = objectMapper.writeValueAsString(response);

    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + "/" + RefDataDomainEnum.BADGE))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

    List<ReferenceData> result = client.retrieve(RefDataDomainEnum.BADGE);
    assertThat(result).isEqualTo(referenceDataList);
  }

  @Test
  public void updateLocalAuthority_shouldUpdateLocalAuthority() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + AUTHORITIES_PATH + SHORT_CODE_VALUE))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(
            jsonPath(DIFFERENT_SERVICE_SIGNPOST_URL_PARAM, equalTo(DIFFERENT_SERVICE_SIGNPOST_URL)))
        .andExpect(jsonPath(COUNTRY_PARAM, equalTo(COUNTRY)))
        .andExpect(jsonPath(POSTCODE_PARAM, equalTo(POSTCODE)))
        .andExpect(jsonPath(NATION_PARAM, equalTo(NATION.name())))
        .andExpect(jsonPath("contactUrl", equalTo(WEB_SITE_URL)))
        .andRespond(withSuccess());

    client.updateLocalAuthority(SHORT_CODE_VALUE, LOCAL_AUTHORITY);
  }

  @Test(expected = BadRequestException.class)
  @SneakyThrows
  public void updateLocalAuthority_shouldThrowBadRequestException_WhenBadRequest() {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + AUTHORITIES_PATH + SHORT_CODE_VALUE))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(
            jsonPath(
                DIFFERENT_SERVICE_SIGNPOST_URL_PARAM,
                equalTo(DIFFERENT_SERVICE_SIGNPOST_URL_INVALID)))
        .andExpect(jsonPath(COUNTRY_PARAM, equalTo(COUNTRY)))
        .andExpect(jsonPath(POSTCODE_PARAM, equalTo(POSTCODE)))
        .andExpect(jsonPath(NATION_PARAM, equalTo(NATION.name())))
        .andExpect(jsonPath("contactUrl", equalTo(WEB_SITE_URL)))
        .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

    client.updateLocalAuthority(SHORT_CODE_VALUE, LOCAL_AUTHORITY_INVALID_VALUE);
  }

  @Test
  public void updateLocalCouncil_shouldUpdateLocalCouncil() {
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + COUNCILS_PATH + SHORT_CODE_VALUE))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(jsonPath("description", equalTo("a description")))
        .andExpect(jsonPath("welshDescription", equalTo("Welsh")))
        .andRespond(withSuccess());

    client.updateLocalCouncil(
        SHORT_CODE_VALUE,
        LocalCouncil.builder().description("a description").welshDescription("Welsh").build());
  }

  @Test(expected = BadRequestException.class)
  @SneakyThrows
  public void updateLocalCouncil_shouldThrowBadRequestException_WhenBadRequest() {
    CommonResponse commonResponse = new CommonResponse();
    String body = objectMapper.writeValueAsString(commonResponse);
    mockServer
        .expect(once(), requestTo(BASE_ENDPOINT + COUNCILS_PATH + SHORT_CODE_VALUE))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(jsonPath("description", equalTo("description invalid")))
        .andRespond(withBadRequest().body(body).contentType(MediaType.APPLICATION_JSON_UTF8));

    client.updateLocalCouncil(
        SHORT_CODE_VALUE, LocalCouncil.builder().description("description invalid").build());
  }
}
