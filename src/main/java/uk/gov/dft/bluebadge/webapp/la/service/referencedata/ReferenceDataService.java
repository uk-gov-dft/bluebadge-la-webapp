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

  private Map<String, List<ReferenceData>> badgeGroupedReferenceDataList = null;
  private Map<String, Map<String, String>> badgeGroupedReferenceDataMap = null;
  private Map<String, List<ReferenceData>> applicationGroupedReferenceDataList = null;
  private Map<String, Map<String, String>> applicationGroupedReferenceDataMap = null;

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
      badgeGroupedReferenceDataList = initDataList(RefDataDomainEnum.BADGE);
      applicationGroupedReferenceDataList = initDataList(RefDataDomainEnum.APP);
      badgeGroupedReferenceDataMap = initDataMap(badgeGroupedReferenceDataList);
      applicationGroupedReferenceDataMap = initDataMap(applicationGroupedReferenceDataList);
    }
  }

  private Map<String, List<ReferenceData>> initDataList(RefDataDomainEnum domain) {
    List<ReferenceData> referenceDataList = referenceDataApiClient.retrieveReferenceData(domain);
    return referenceDataList
        .stream()
        .collect(Collectors.groupingBy(ReferenceData::getGroupShortCode));
  }

  private Map<String, Map<String, String>> initDataMap(
      Map<String, List<ReferenceData>> groupedReferenceDataList) {
    Map<String, Map<String, String>> groupedReferenceDataMap = new HashMap<>();

    groupedReferenceDataList.forEach(
        (key, value) ->
            groupedReferenceDataMap.put(
                key,
                value
                    .stream()
                    .collect(
                        Collectors.toMap(
                            ReferenceData::getShortCode, ReferenceData::getDescription))));
    return groupedReferenceDataMap;
  }

  // BADGE
  public List<ReferenceData> retrieveBadgeCancellations() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.CANCEL);
  }

  public List<ReferenceData> retrieveBadgeEligilities() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.ELIGIBILITY);
  }

  public List<ReferenceData> retrieveBadgeGenders() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.GENDER);
  }

  public List<ReferenceData> retrieveBadgeApplicationChannels() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.APP_SOURCE);
  }

  public List<ReferenceData> retrieveBadgeDeliverTos() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.DELIVER_TO);
  }

  public List<ReferenceData> retrieveBadgeDeliveryOptions() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.DELIVERY_OPTIONS);
  }

  public List<ReferenceData> retrieveBadgeStatuses() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.STATUS);
  }

  public List<ReferenceData> retrieveBadgeLocalAuthorities() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.LA);
  }

  // APPLICATION
  public List<ReferenceData> retrieveApplicationWalkingDifficulties() {
    return retrieveApplicationReferenceDataList(RefDataGroupEnum.WALKING_DIFFICULTIES);
  }

  // BADGE
  private List<ReferenceData> retrieveBadgeReferenceDataList(RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) {
      init();
    }
    return badgeGroupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  private List<ReferenceData> retrieveApplicationReferenceDataList(
      RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) {
      init();
    }
    return applicationGroupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  public String retrieveBadgeEligibilityDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.ELIGIBILITY, key);
  }

  public String retrieveBadgeGenderDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.GENDER, key);
  }

  public String retrieveBadgeApplicationChannelDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.APP_SOURCE, key);
  }

  public String retrieveBadgeDeliverToDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.DELIVER_TO, key);
  }

  public String retrieveBadgeDeliveryOptionDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.DELIVERY_OPTIONS, key);
  }

  public String retrieveBadgeStatusDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.STATUS, key);
  }

  public String retrieveBadgeLocalAuthorityDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.LA, key);
  }

  // APPLICATION
  public String retrieveApplicationGenderDisplayValue(String key) {
    return retrieveApplicationReferenceDataDisplayValue(RefDataGroupEnum.GENDER, key);
  }

  public String retrieveApplicationEligibilityDisplayValue(String key) {
    return retrieveApplicationReferenceDataDisplayValue(RefDataGroupEnum.ELIGIBILITY, key);
  }

  public String retrieveApplicationWalkingDifficultyDisplayValue(String key) {
    return retrieveApplicationReferenceDataDisplayValue(RefDataGroupEnum.WALKING_DIFFICULTIES, key);
  }

  public String retrieveApplicationWalkingSpeedDisplayValue(String key) {
    return retrieveApplicationReferenceDataDisplayValue(RefDataGroupEnum.WALKING_SPEED, key);
  }

  private String retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum group, String key) {
    if (!isLoaded.get()) {
      init();
    }
    return badgeGroupedReferenceDataMap.get(group.getGroupKey()).get(key);
  }

  private String retrieveApplicationReferenceDataDisplayValue(RefDataGroupEnum group, String key) {
    if (!isLoaded.get()) {
      init();
    }
    return applicationGroupedReferenceDataMap.get(group.getGroupKey()).get(key);
  }
}
