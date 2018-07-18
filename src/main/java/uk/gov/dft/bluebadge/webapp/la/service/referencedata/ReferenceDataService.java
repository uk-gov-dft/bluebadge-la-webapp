package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;

@Service
public class ReferenceDataService {

  private Map<String, List<ReferenceData>> groupedReferenceData = null;
  private final ReferenceDataApiClient referenceDataApiClient;
  private AtomicBoolean isLoaded = new AtomicBoolean();

  @Autowired
  public ReferenceDataService(ReferenceDataApiClient referenceDataApiClient) {
    this.referenceDataApiClient = referenceDataApiClient;
  }

  /**
   * Load the ref data first time required. Chose not to do PostConstruct so that can start service
   * if ref data service is still starting.
   */
  private void init() {
    if (!isLoaded.getAndSet(true)) {
      List<ReferenceData> referenceDataList =
          referenceDataApiClient.retrieveReferenceData(RefDataDomainEnum.BADGE);
      groupedReferenceData =
          referenceDataList
              .stream()
              .collect(Collectors.groupingBy(ReferenceData::getGroupShortCode));
    }
  }

  public List<ReferenceData> retrieveEligilities() {
    if (!isLoaded.get()) init();
    return groupedReferenceData.get(RefDataGroupEnum.ELIGIBILITY.getGroupKey());
  }

  public List<ReferenceData> retrieveGender() {
    if (!isLoaded.get()) init();
    return groupedReferenceData.get(RefDataGroupEnum.GENDER.getGroupKey());
  }
}
