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

  public List<ReferenceData> retrieveCancellations() {
    return retrieveReferenceDataList(RefDataGroupEnum.CANCEL);
  }

  public List<ReferenceData> retrieveEligilities() {
    return retrieveReferenceDataList(RefDataGroupEnum.ELIGIBILITY);
  }

  public List<ReferenceData> retrieveGenders() {
    return retrieveReferenceDataList(RefDataGroupEnum.GENDER);
  }

  public List<ReferenceData> retrieveApplicationChannels() {
    return retrieveReferenceDataList(RefDataGroupEnum.APP_SOURCE);
  }

  public List<ReferenceData> retrieveDeliverTos() {
    return retrieveReferenceDataList(RefDataGroupEnum.DELIVER_TO);
  }

  public List<ReferenceData> retrieveDeliveryOptions() {
    return retrieveReferenceDataList(RefDataGroupEnum.DELIVERY_OPTIONS);
  }

  public List<ReferenceData> retrieveStatuses() {
    return retrieveReferenceDataList(RefDataGroupEnum.STATUS);
  }

  public List<ReferenceData> retrieveLocalAuthorities() {
    return retrieveReferenceDataList(RefDataGroupEnum.LA);
  }

  private List<ReferenceData> retrieveReferenceDataList(RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) {
      init();
    }
    return groupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  public String retrieveEligibilityDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.ELIGIBILITY, key);
  }

  public String retrieveGenderDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.GENDER, key);
  }

  public String retrieveApplicationChannelDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.APP_SOURCE, key);
  }

  public String retrieveDeliverToDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.DELIVER_TO, key);
  }

  public String retrieveDeliveryOptionDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.DELIVERY_OPTIONS, key);
  }

  public String retrieveStatusDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.STATUS, key);
  }

  public String retrieveLocalAuthorityDisplayValue(String key) {
    return retrieveReferenceDataDisplayValue(RefDataGroupEnum.LA, key);
  }

  private String retrieveReferenceDataDisplayValue(RefDataGroupEnum group, String key) {
    if (!isLoaded.get()) {
      init();
    }
    return groupedReferenceDataMap.get(group.getGroupKey()).get(key);
  }
}
