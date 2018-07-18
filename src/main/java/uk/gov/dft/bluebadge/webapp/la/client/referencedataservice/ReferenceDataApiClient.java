package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceDataResponse;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;

@Slf4j
@Service
public class ReferenceDataApiClient {

  public static final String REFERENCE_DATA_DOMAIN_BADGE = "BADGE";

  private final ServiceConfiguration referencedataManagementApiConfig;
  private final RestTemplateFactory restTemplateFactory;

  @Autowired
  public ReferenceDataApiClient(
      ServiceConfiguration referencedataManagementApiConfig,
      RestTemplateFactory restTemplateFactory) {
    this.referencedataManagementApiConfig = referencedataManagementApiConfig;
    this.restTemplateFactory = restTemplateFactory;
  }

  /**
   * Retrieve badge reference data
   *
   * @return List of reference data items.
   */
  public List<ReferenceData> retrieveReferenceData(RefDataDomainEnum referenceDataDomain) {
    log.debug("Loading reference data.");

      ReferenceDataResponse response =
        restTemplateFactory
          .getInstance()
          .getForEntity(
            referencedataManagementApiConfig
              .getUriComponentsBuilder("reference-data", referenceDataDomain.getDomain())
              .toUriString(),
            ReferenceDataResponse.class)
          .getBody();

      return response.getData();
  }
}
