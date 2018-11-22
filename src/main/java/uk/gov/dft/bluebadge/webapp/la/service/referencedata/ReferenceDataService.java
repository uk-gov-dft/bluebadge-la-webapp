package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;

@Service
@Slf4j
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReferenceDataService {

  private Map<String, List<ReferenceData>> badgeGroupedReferenceDataList = null;
  private Map<String, Map<String, String>> badgeGroupedReferenceDataMap = null;
  private Map<String, List<ReferenceData>> applicationGroupedReferenceDataList = null;
  private Map<String, Map<String, String>> applicationGroupedReferenceDataMap = null;

  private final ReferenceDataApiClient referenceDataApiClient;
  private final SecurityUtils securityUtils;
  private AtomicBoolean isLoaded = new AtomicBoolean();

  @Autowired
  public ReferenceDataService(
      ReferenceDataApiClient referenceDataApiClient, SecurityUtils securityUtils) {
    this.referenceDataApiClient = referenceDataApiClient;
    this.securityUtils = securityUtils;
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

  /**
   * Get the display name for the local authority of the logged in user.
   * @return LA name, e.g. Shropshire County Council
   */
  public String retrieveBadgeLocalAuthorityDisplayValue() {
    return retrieveBadgeLocalAuthorityDisplayValue(
        securityUtils.getCurrentLocalAuthorityShortCode());
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

  /*
   * Used directly by template.  Do not delete.
   */
  public String retrieveAppEnumDisplayValueByString(String group, String key) {
    if (!isLoaded.get()) {
      init();
    }
    if (null == key) {
      return "";
    }
    if (null == applicationGroupedReferenceDataMap) {
      log.warn(
          "No application reference data loaded.  Returning enpty string in retrieveAppEnumDisplayValueByString.");
      return "";
    }
    Map<String, String> groupMap = applicationGroupedReferenceDataMap.get(group);
    if (null == groupMap) {
      return "INVALID REF DATA GROUP";
    }
    return groupMap.get(key);
  }

  /*
   * Used directly by template.  Do not delete.
   */
  public String retrieveAppEnumDisplayValue(String group, Enum<?> key) {
    if (null == key) {
      return "";
    }

    return retrieveAppEnumDisplayValueByString(group, key.name());
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

  public List<ReferenceData> displayedUserRoles() {
    ReferenceData admin =
        new ReferenceData().description("Administrator").shortCode(Role.LA_ADMIN.name());
    ReferenceData editor =
        new ReferenceData().description("Editor").shortCode(Role.LA_EDITOR.name());
    ReferenceData viewer =
        new ReferenceData().description("View only").shortCode(Role.LA_READ.name());

    List<ReferenceData> roles = Lists.newArrayList(viewer, editor, admin);

    if (securityUtils.isPermitted(Permissions.CREATE_DFT_USER)) {
      ReferenceData dftAdmin =
          new ReferenceData().description("DfT Administrator").shortCode(DFT_ADMIN.name());
      roles.add(dftAdmin);
    }

    return roles;
  }
}
