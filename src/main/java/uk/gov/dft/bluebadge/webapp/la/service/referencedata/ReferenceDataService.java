package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;
import static uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum.CANCEL;
import static uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum.REPLACE;
import static uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum.STATUS;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
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
    List<ReferenceData> referenceDataList = referenceDataApiClient.retrieve(domain);
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

  public List<ReferenceData> retrieveBadgeReplaceReasons() {
    return retrieveBadgeReferenceDataList(RefDataGroupEnum.REPLACE);
  }

  // APPLICATION
  public List<ReferenceData> retrieveApplicationWalkingDifficulties() {
    return retrieveApplicationReferenceDataList(RefDataGroupEnum.WALKING_DIFFICULTIES);
  }

  private List<ReferenceData> retrieveApplicationReferenceDataList(
      RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) {
      init();
    }
    return applicationGroupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  // BADGE
  public List<ReferenceData> retrieveBadgeReferenceDataList(RefDataGroupEnum referenceDataGroup) {
    if (!isLoaded.get()) {
      init();
    }
    return badgeGroupedReferenceDataList.get(referenceDataGroup.getGroupKey());
  }

  public Optional<ReferenceData> retrieveBadgeReferenceDataItem(
      RefDataGroupEnum referenceDataGroup, String shortCode) {
    return retrieveBadgeReferenceDataList(referenceDataGroup)
        .stream()
        .filter(rd -> rd.getShortCode().equalsIgnoreCase(shortCode))
        .findFirst();
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

  public String retrieveBadgeStatusDisplayValue(Badge badge) {
    String description = retrieveBadgeReferenceDataDisplayValue(STATUS, badge.getStatusCode());
    String reason = StringUtils.EMPTY;
    if ("REPLACED".equals(badge.getStatusCode())) {
      reason = retrieveBadgeReferenceDataDisplayValue(REPLACE, badge.getReplaceReasonCode());
    } else if ("CANCELLED".equals(badge.getStatusCode())) {
      reason = retrieveBadgeReferenceDataDisplayValue(CANCEL, badge.getCancelReasonCode());
    }

    return StringUtils.isEmpty(reason) ? description : description + " (" + reason + ")";
  }

  public String retrieveBadgeLocalAuthorityDisplayValue(String key) {
    return retrieveBadgeReferenceDataDisplayValue(RefDataGroupEnum.LA, key);
  }

  /**
   * Get the display name for the local authority of the logged in user.
   *
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
   * Used directly by template. Do not delete.
   */
  @SuppressWarnings("WeakerAccess")
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
   * Used directly by template. Do not delete.
   */
  @SuppressWarnings("unused")
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
        ReferenceData.builder()
            .description("Administrator")
            .shortCode(Role.LA_ADMIN.name())
            .build();
    ReferenceData editor =
        ReferenceData.builder().description("Editor").shortCode(Role.LA_EDITOR.name()).build();
    ReferenceData viewer =
        ReferenceData.builder().description("View only").shortCode(Role.LA_READ.name()).build();

    List<ReferenceData> roles = Lists.newArrayList(viewer, editor, admin);

    if (securityUtils.isPermitted(Permissions.CREATE_DFT_USER)) {
      ReferenceData dftAdmin =
          ReferenceData.builder()
              .description("DfT Administrator")
              .shortCode(DFT_ADMIN.name())
              .build();
      roles.add(dftAdmin);
    }

    return roles;
  }

  public void updateLocalAuthority(String shortCode, LocalAuthority localAuthority) {
    log.debug("Updating local authority with shortCode [{}]", shortCode);
    Assert.notNull(shortCode, "shortCode should not be null");
    Assert.notNull(localAuthority, "localAuthority should not be null");

    referenceDataApiClient.updateLocalAuthority(shortCode, localAuthority);
  }

  public void updateLocalCouncil(String shortCode, LocalCouncil localCouncil){
    Assert.notNull(shortCode, "Short code required to update LC.");
    Assert.notNull(localCouncil, "Council details required to update LC");

    referenceDataApiClient.updateLocalCouncil(shortCode, localCouncil);
  }
}
