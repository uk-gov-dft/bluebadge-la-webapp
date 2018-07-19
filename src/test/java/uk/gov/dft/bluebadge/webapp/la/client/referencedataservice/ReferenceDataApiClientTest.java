package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceDataResponse;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;

public class ReferenceDataApiClientTest {

  private static final String SCHEME = "http";
  private static final String HOST = "localhost";
  private static final Integer PORT = 1111;
  private static final String CONTEXT = "context";
  private static final String API = "reference-data";

  private static final String BASE_ENDPOINT =
      String.format("%s://%s:%d/%s/%s", SCHEME, HOST, PORT, CONTEXT, API);

  @Mock private RestTemplateFactory mockRestTemplateFactory;

  private ReferenceDataApiClient client;

  private MockRestServiceServer mockServer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    RestTemplate restTemplate = new RestTemplate();
    mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    when(mockRestTemplateFactory.getInstance()).thenReturn(restTemplate);

    ServiceConfiguration serviceConfiguration = buildServiceConfiguration();

    client = new ReferenceDataApiClient(serviceConfiguration, mockRestTemplateFactory);
  }

  @Test
  public void retrieveReferenceDataWithADomain_shouldReturnReferenceDataForThatDomain()
      throws Exception {
    ReferenceData referenceData1 = buildReferenceData(1);
    ReferenceData referenceData2 = buildReferenceData(2);
    ReferenceData referenceData3 = buildReferenceData(3);
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

  private ReferenceData buildReferenceData(int i) {
    return new ReferenceData()
        .description("description" + 1)
        .displayOrder(i)
        .groupDescription("groupDescription" + i)
        .groupShortCode("groupShortCode" + i)
        .shortCode("shortCode" + i)
        .subgroupDescription("subGroupDescription" + i)
        .subgroupShortCode("subGroupShortCode" + i);
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
