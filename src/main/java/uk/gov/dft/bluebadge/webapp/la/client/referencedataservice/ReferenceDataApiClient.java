package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceDataResponse;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;

@Slf4j
@Service
public class ReferenceDataApiClient extends BaseApiClient {

  private final RestTemplate restTemplate;

  @Autowired
  public ReferenceDataApiClient(
      @Qualifier("referenceDataRestTemplate") RestTemplate referenceDataRestTemplate) {
    this.restTemplate = referenceDataRestTemplate;
  }

  /**
   * Retrieve badge reference data
   *
   * @return List of reference data items.
   */
  public List<ReferenceData> retrieve(RefDataDomainEnum referenceDataDomain) {
    log.debug("Loading reference data.");

    ReferenceDataResponse response =
        restTemplate
            .getForEntity(
                UriComponentsBuilder.newInstance()
                    .path("/")
                    .pathSegment("reference-data", referenceDataDomain.getDomain())
                    .toUriString(),
                ReferenceDataResponse.class)
            .getBody();

    return response.getData();
  }

  /**
   * Updates a local authority.
   *
   * @param shortCode identifier of the local authority to update.
   * @param localAuthority objects with values to update.
   */
  public void updateLocalAuthority(String shortCode, LocalAuthority localAuthority) {
    String uri =
        UriComponentsBuilder.fromUriString("/reference-data/authorities/{shortCode}")
            .build()
            .toUriString();

    HttpEntity<LocalAuthority> httpRequest = new HttpEntity<>(localAuthority);

    try {
      restTemplate.put(uri, HttpMethod.PUT, httpRequest, shortCode);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }

  /**
   * Updates a local authority.
   *
   * @param shortCode identifier of the local council to update.
   * @param localCouncil objects with values to update.
   */
  public void updateLocalCouncil(String shortCode, LocalCouncil localCouncil) {
    String uri =
        UriComponentsBuilder.fromUriString("/reference-data/councils/{shortCode}")
            .build()
            .toUriString();

    HttpEntity<LocalCouncil> httpRequest = new HttpEntity<>(localCouncil);

    try {
      restTemplate.put(uri, httpRequest, shortCode);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }
}
