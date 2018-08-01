package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceDataResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;

public class ReferenceDataApiClientTest {
  public static final String TEST_URI = "http://justtesting:8787/test/";

  private static final String BASE_ENDPOINT = TEST_URI + "reference-data";

  private ReferenceDataApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() throws Exception {
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

    List<ReferenceData> result = client.retrieveReferenceData(RefDataDomainEnum.BADGE);
    assertThat(result).isEqualTo(referenceDataList);
  }
}
