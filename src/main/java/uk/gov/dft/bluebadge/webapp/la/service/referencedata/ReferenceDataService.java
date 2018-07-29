package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

import java.util.HashMap;
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

  private Map<String, List<ReferenceData>> groupedReferenceDataList = null;
  private Map<String, Map<String, String>> groupedReferenceDataMap = null;

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
      groupedReferenceDataList =
          referenceDataList
              .stream()
              .collect(Collectors.groupingBy(ReferenceData::getGroupShortCode));

      groupedReferenceDataMap = new HashMap<>();

      groupedReferenceDataList.forEach(
          (key, value) ->
              groupedReferenceDataMap.put(
                  key,
                  value
                      .stream()
                      .collect(
                          Collectors.toMap(
                              ReferenceData::getShortCode, ReferenceData::getDescription))));
    }
  }

  public List<ReferenceData> retrieveEligilities() {
    return retrieveReferenceData(RefDataGroupEnum.ELIGIBILITY);
  }

  public List<ReferenceData> retrieveGender() {
    return retrieveReferenceData(RefDataGroupEnum.GENDER);
  }

  public List<ReferenceData> retrieveApplicationChannel() {
    return retrieveReferenceData(RefDataGroupEnum.APP_SOURCE);
  }

  public List<ReferenceData> retrieveDeliverTo() {
    return retrieveReferenceData(RefDataGroupEnum.DELIVER_TO);
  }

  public List<ReferenceData> retrieveDeliveryOptions() {
    return retrieveReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS);
  }

  private List<ReferenceData> retrieveReferenceData(RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  public String retrieveEligibilityDisplayValue(String key) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataMap.get(RefDataGroupEnum.ELIGIBILITY.getGroupKey()).get(key);
  }

  public String retrieveGenderDisplayValue(String key) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataMap.get(RefDataGroupEnum.GENDER.getGroupKey()).get(key);
  }

  public String retrieveApplicationChannelDisplayValue(String key) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataMap.get(RefDataGroupEnum.APP_SOURCE.getGroupKey()).get(key);
  }

  public String retrieveDeliverToDisplayValue(String key) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataMap.get(RefDataGroupEnum.DELIVER_TO.getGroupKey()).get(key);
  }

  public String retrieveDeliveryOptionsDisplayValue(String key) {
    if (!isLoaded.get()) init();
    return groupedReferenceDataMap.get(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey()).get(key);
  }
}
