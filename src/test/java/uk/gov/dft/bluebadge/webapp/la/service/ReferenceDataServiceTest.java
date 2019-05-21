package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalCouncil;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class ReferenceDataServiceTest {

  private static final String ELIGIBILITY_1_SHORTCODE = "eligibilityShortCode1";
  private static final String ELIGIBILITY_2_SHORTCODE = "eligibilityShortCode2";
  private static final String ELIGIBILITY_1 = "eligibilityValue1";
  private static final String ELIGIBILITY_2 = "eligibilityValue2";
  private static final String GENDER_1_SHORTCODE = "MALE";
  private static final String GENDER_2_SHORTCODE = "FEMALE";
  private static final String GENDER_1 = "male";
  private static final String GENDER_2 = "female";
  private static final String APPLICATION_CHANNEL_1_SHORTCODE = "APPLICATION_CHANNEL1";
  private static final String APPLICATION_CHANNEL_2_SHORTCODE = "APPLICATION_CHANNEL2";
  private static final String APPLICATION_CHANNEL_1 = "application channel 1";
  private static final String APPLICATION_CHANNEL_2 = "application channel 2";
  private static final String DELIVER_TO_1_SHORTCODE = "DELIVER_TO1";
  private static final String DELIVER_TO_2_SHORTCODE = "DELIVER_TO2";
  private static final String DELIVER_TO_1 = "deliver to 1";
  private static final String DELIVER_TO_2 = "deliver to 2";
  private static final String DELIVERY_OPTIONS_1_SHORTCODE = "DELIVERY_OPTIONS1";
  private static final String DELIVERY_OPTIONS_2_SHORTCODE = "DELIVERY_OPTIONS2";
  private static final String DELIVERY_OPTIONS_1 = "delivery options 1";
  private static final String DELIVERY_OPTIONS_2 = "delivery options 2";
  private static final String LA_1_SHORTCODE = "ABERD";
  private static final String LA_2_SHORTCODE = "BARNS";
  private static final String LA_1 = "Aberdeenshire council";
  private static final String LA_2 = "Barnsley MBC";
  private static final String WALKING_DIFFICULTY_1_SHORTCODE = "PEOPLECAR";
  private static final String WALKING_DIFFICULTY_2_SHORTCODE = "BREATH";
  private static final String WALKING_DIFFICULTY_1 = "People Carrier";
  private static final String WALKING_DIFFICULTY_2 = "Breathlessness";
  private static final String WALKING_SPEED_1_SHORTCODE = "SLOW";
  private static final String WALKING_SPEED_2_SHORTCODE = "SAME";
  private static final String WALKING_SPEED_1 = "Slower";
  private static final String WALKING_SPEED_2 = "About the same";
  private static final String DIFFERENT_SIGNPOST_SERVICE_URL = "http://localhost";
  private static final LocalAuthority LOCAL_AUTHORITY =
      LocalAuthority.builder().differentServiceSignpostUrl(DIFFERENT_SIGNPOST_SERVICE_URL).build();

  @Mock private ReferenceDataApiClient referenceDataManagementApiClientMock;
  @Mock private SecurityUtils securityUtilsMock;

  private ReferenceDataService referenceDataService;

  private ReferenceData referenceDataEligibility1;
  private ReferenceData referenceDataEligibility2;
  private ReferenceData referenceDataGender1;
  private ReferenceData referenceDataGender2;
  private ReferenceData referenceDataApplicationChannel1;
  private ReferenceData referenceDataApplicationChannel2;
  private ReferenceData referenceDataDeliverTo1;
  private ReferenceData referenceDataDeliverTo2;
  private ReferenceData referenceDataDeliveryOptions1;
  private ReferenceData referenceDataDeliveryOptions2;
  private ReferenceData statusNew =
      ReferenceData.builder()
          .shortCode("NEW")
          .groupShortCode(RefDataGroupEnum.STATUS.getGroupKey())
          .description("New")
          .build();
  private ReferenceData statusCancelled =
      ReferenceData.builder()
          .shortCode("CANCELLED")
          .groupShortCode(RefDataGroupEnum.STATUS.getGroupKey())
          .description("Cancelled")
          .build();
  private ReferenceData statusReplaced =
      ReferenceData.builder()
          .shortCode("REPLACED")
          .groupShortCode(RefDataGroupEnum.STATUS.getGroupKey())
          .description("Replaced")
          .build();
  private ReferenceData referenceDataLocalAuthority1;
  private ReferenceData referenceDataLocalAuthority2;
  private ReferenceData referenceDataWalkingDifficulty1;
  private ReferenceData referenceDataWalkingDifficulty2;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    referenceDataService =
        new ReferenceDataService(referenceDataManagementApiClientMock, securityUtilsMock);

    referenceDataEligibility1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1);
    referenceDataEligibility1.setShortCode(ELIGIBILITY_1_SHORTCODE);
    referenceDataEligibility1.setDescription(ELIGIBILITY_1);
    referenceDataEligibility2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 2);
    referenceDataEligibility2.setShortCode(ELIGIBILITY_2_SHORTCODE);
    referenceDataEligibility2.setDescription(ELIGIBILITY_2);
    referenceDataGender1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 3);
    referenceDataGender1.setShortCode(GENDER_1_SHORTCODE);
    referenceDataGender1.setDescription(GENDER_1);
    referenceDataGender2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4);
    referenceDataGender2.setShortCode(GENDER_2_SHORTCODE);
    referenceDataGender2.setDescription(GENDER_2);
    referenceDataApplicationChannel1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 5);
    referenceDataApplicationChannel1.setShortCode(APPLICATION_CHANNEL_1_SHORTCODE);
    referenceDataApplicationChannel1.setDescription(APPLICATION_CHANNEL_1);
    referenceDataApplicationChannel2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 6);
    referenceDataApplicationChannel2.setShortCode(APPLICATION_CHANNEL_2_SHORTCODE);
    referenceDataApplicationChannel2.setDescription(APPLICATION_CHANNEL_2);
    referenceDataDeliverTo1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 7);
    referenceDataDeliverTo1.setShortCode(DELIVER_TO_1_SHORTCODE);
    referenceDataDeliverTo1.setDescription(DELIVER_TO_1);
    referenceDataDeliverTo2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 8);
    referenceDataDeliverTo2.setShortCode(DELIVER_TO_2_SHORTCODE);
    referenceDataDeliverTo2.setDescription(DELIVER_TO_2);
    referenceDataDeliveryOptions1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 9);
    referenceDataDeliveryOptions1.setShortCode(DELIVERY_OPTIONS_1_SHORTCODE);
    referenceDataDeliveryOptions1.setDescription(DELIVERY_OPTIONS_1);
    referenceDataDeliveryOptions2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 10);
    referenceDataDeliveryOptions2.setShortCode(DELIVERY_OPTIONS_2_SHORTCODE);
    referenceDataDeliveryOptions2.setDescription(DELIVERY_OPTIONS_2);
    referenceDataLocalAuthority1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.LA.getGroupKey(), 13);
    referenceDataLocalAuthority1.setShortCode(LA_1_SHORTCODE);
    referenceDataLocalAuthority1.setDescription(LA_1);
    referenceDataLocalAuthority2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.LA.getGroupKey(), 14);
    referenceDataLocalAuthority2.setShortCode(LA_2_SHORTCODE);
    referenceDataLocalAuthority2.setDescription(LA_2);
    referenceDataWalkingDifficulty1 =
        ReferenceDataUtils.buildReferenceData(
            RefDataGroupEnum.WALKING_DIFFICULTIES.getGroupKey(), 15);
    referenceDataWalkingDifficulty1.setShortCode(WALKING_DIFFICULTY_1_SHORTCODE);
    referenceDataWalkingDifficulty1.setDescription(WALKING_DIFFICULTY_1);
    referenceDataWalkingDifficulty2 =
        ReferenceDataUtils.buildReferenceData(
            RefDataGroupEnum.WALKING_DIFFICULTIES.getGroupKey(), 16);
    referenceDataWalkingDifficulty2.setShortCode(WALKING_DIFFICULTY_2_SHORTCODE);
    referenceDataWalkingDifficulty2.setDescription(WALKING_DIFFICULTY_2);
    ReferenceData referenceDataWalkingSpeed1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.WALKING_SPEED.getGroupKey(), 17);
    referenceDataWalkingSpeed1.setShortCode(WALKING_SPEED_1_SHORTCODE);
    referenceDataWalkingSpeed1.setDescription(WALKING_SPEED_1);
    ReferenceData referenceDataWalkingSpeed2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.WALKING_SPEED.getGroupKey(), 18);
    referenceDataWalkingSpeed2.setShortCode(WALKING_SPEED_2_SHORTCODE);
    referenceDataWalkingSpeed2.setDescription(WALKING_SPEED_2);
    List<ReferenceData> badgeReferenceDataList =
        Lists.newArrayList(
            referenceDataEligibility1,
            referenceDataEligibility2,
            referenceDataGender1,
            referenceDataGender2,
            referenceDataApplicationChannel1,
            referenceDataApplicationChannel2,
            referenceDataDeliverTo1,
            referenceDataDeliverTo2,
            referenceDataDeliveryOptions1,
            referenceDataDeliveryOptions2,
            statusNew,
            statusCancelled,
            statusReplaced,
            referenceDataLocalAuthority1,
            referenceDataLocalAuthority2,
            ReferenceData.builder()
                .shortCode("NOLONG")
                .groupShortCode(RefDataGroupEnum.CANCEL.getGroupKey())
                .description("No longer needed")
                .build(),
            ReferenceData.builder()
                .shortCode("LOST")
                .groupShortCode(RefDataGroupEnum.REPLACE.getGroupKey())
                .description("Lost")
                .build());

    List<ReferenceData> applicationReferenceDataList =
        Lists.newArrayList(
            referenceDataEligibility1,
            referenceDataEligibility2,
            referenceDataGender1,
            referenceDataGender2,
            referenceDataWalkingDifficulty1,
            referenceDataWalkingDifficulty2,
            referenceDataWalkingSpeed1,
            referenceDataWalkingSpeed2);
    when(referenceDataManagementApiClientMock.retrieve(RefDataDomainEnum.BADGE))
        .thenReturn(badgeReferenceDataList);
    when(referenceDataManagementApiClientMock.retrieve(RefDataDomainEnum.APP))
        .thenReturn(applicationReferenceDataList);
  }

  @Test
  public void retrieveBadgeEligibilities_ShouldReturnEligibilities() {
    List<ReferenceData> eligibilities = referenceDataService.retrieveBadgeEligilities();
    assertThat(eligibilities)
        .containsExactlyInAnyOrder(referenceDataEligibility1, referenceDataEligibility2);
  }

  @Test
  public void retrieveBadgeGenders_ShouldReturnGender() {
    List<ReferenceData> gender = referenceDataService.retrieveBadgeGenders();
    assertThat(gender).containsExactlyInAnyOrder(referenceDataGender1, referenceDataGender2);
  }

  @Test
  public void retrieveBadgeApplicationChannels_ShouldReturnApplicationChannels() {
    List<ReferenceData> applicationChannels =
        referenceDataService.retrieveBadgeApplicationChannels();
    assertThat(applicationChannels)
        .containsExactlyInAnyOrder(
            referenceDataApplicationChannel1, referenceDataApplicationChannel2);
  }

  @Test
  public void retrieveBadgeDeliverTos_ShouldReturnDeliverTos() {
    List<ReferenceData> deliverTos = referenceDataService.retrieveBadgeDeliverTos();
    assertThat(deliverTos)
        .containsExactlyInAnyOrder(referenceDataDeliverTo1, referenceDataDeliverTo2);
  }

  @Test
  public void retrieveBadgeDeliveryOptions_ShouldReturnDeliveryOptions() {
    List<ReferenceData> deliveryOptions = referenceDataService.retrieveBadgeDeliveryOptions();
    assertThat(deliveryOptions)
        .containsExactlyInAnyOrder(referenceDataDeliveryOptions1, referenceDataDeliveryOptions2);
  }

  @Test
  public void retrieveBadgeStatuses_ShouldReturnStatuses() {
    List<ReferenceData> statuses = referenceDataService.retrieveBadgeStatuses();
    assertThat(statuses).containsExactlyInAnyOrder(statusCancelled, statusNew, statusReplaced);
  }

  @Test
  public void retrieveBadgeLocalAuthorities_ShouldReturnStatuses() {
    List<ReferenceData> statuses = referenceDataService.retrieveBadgeLocalAuthorities();
    assertThat(statuses)
        .containsExactlyInAnyOrder(referenceDataLocalAuthority1, referenceDataLocalAuthority2);
  }

  @Test
  public void retrieveApplicationWalkingDifficulties_ShouldReturnStatuses() {
    List<ReferenceData> statuses = referenceDataService.retrieveApplicationWalkingDifficulties();
    assertThat(statuses)
        .containsExactlyInAnyOrder(
            referenceDataWalkingDifficulty1, referenceDataWalkingDifficulty2);
  }

  @Test
  public void retrieveBadgeEligibilityDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveBadgeEligibilityDisplayValue(ELIGIBILITY_1_SHORTCODE))
        .isEqualTo(ELIGIBILITY_1);
  }

  @Test
  public void retrieveBadgeGenderDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveBadgeGenderDisplayValue(GENDER_1_SHORTCODE))
        .isEqualTo(GENDER_1);
  }

  @Test
  public void retrieveBadgeApplicationChannelDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveBadgeApplicationChannelDisplayValue(
                APPLICATION_CHANNEL_1_SHORTCODE))
        .isEqualTo(APPLICATION_CHANNEL_1);
  }

  @Test
  public void retrieveBadgeDeliverToDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveBadgeDeliverToDisplayValue(DELIVER_TO_1_SHORTCODE))
        .isEqualTo(DELIVER_TO_1);
  }

  @Test
  public void retrieveBadgeDeliveryOptionsDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveBadgeDeliveryOptionDisplayValue(
                DELIVERY_OPTIONS_1_SHORTCODE))
        .isEqualTo(DELIVERY_OPTIONS_1);
  }

  @Test
  public void retrieveBadgeStatusDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveBadgeStatusDisplayValue("NEW"))
        .isEqualTo(statusNew.getDescription());
  }

  @Test
  public void retrieveBadgeLocalAuthorityDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveBadgeLocalAuthorityDisplayValue(LA_1_SHORTCODE))
        .isEqualTo(LA_1);
  }

  @Test
  public void retrieveApplicationGenderDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveApplicationGenderDisplayValue(GENDER_1_SHORTCODE))
        .isEqualTo(GENDER_1);
  }

  @Test
  public void retrieveApplicationEligibilityDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveApplicationEligibilityDisplayValue(
                ELIGIBILITY_1_SHORTCODE))
        .isEqualTo(ELIGIBILITY_1);
  }

  @Test
  public void retrieveApplicationWalkingDifficultyDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveApplicationWalkingDifficultyDisplayValue(
                WALKING_DIFFICULTY_1_SHORTCODE))
        .isEqualTo(WALKING_DIFFICULTY_1);
  }

  @Test
  public void retrieveApplicationWalkingSpeedDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveApplicationWalkingSpeedDisplayValue(
                WALKING_SPEED_1_SHORTCODE))
        .isEqualTo(WALKING_SPEED_1);
  }

  @Test
  public void displayedUserRoles_whenNotDft_thenExpectedRoles() {
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(false);
    List<ReferenceData> referenceDatas = referenceDataService.displayedUserRoles();
    assertThat(referenceDatas)
        .extracting("shortCode")
        .containsOnly(Role.LA_ADMIN.name(), Role.LA_EDITOR.name(), Role.LA_READ.name());
  }

  @Test
  public void displayedUserRoles_whenDft_thenExpectedRolesIncludesDftAdmin() {
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);
    List<ReferenceData> referenceDatas = referenceDataService.displayedUserRoles();
    assertThat(referenceDatas)
        .extracting("shortCode")
        .containsOnly(
            Role.LA_ADMIN.name(),
            Role.LA_EDITOR.name(),
            Role.LA_READ.name(),
            Role.DFT_ADMIN.name());
  }

  @Test
  public void retrieveBadgeStatusDisplayValue() {
    Badge badge = new Badge();
    badge.setStatusCode("NEW");

    String result = referenceDataService.retrieveBadgeStatusDisplayValue(badge);
    assertThat(result).isEqualTo("New");

    badge.setStatusCode("REPLACED");
    badge.setReplaceReasonCode("LOST");
    result = referenceDataService.retrieveBadgeStatusDisplayValue(badge);
    assertThat(result).isEqualTo("Replaced (Lost)");

    badge.setStatusCode("CANCELLED");
    badge.setCancelReasonCode("NOLONG");
    result = referenceDataService.retrieveBadgeStatusDisplayValue(badge);
    assertThat(result).isEqualTo("Cancelled (No longer needed)");
  }

  @Test
  public void retrieveBadgeReferenceDataList() {
    List<ReferenceData> expectedResult =
        Lists.newArrayList(referenceDataLocalAuthority1, referenceDataLocalAuthority2);
    List<ReferenceData> result = referenceDataService.retrieveBadgeLocalAuthorities();
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void retrieveBadgeReferenceDataItem() {
    Optional<ReferenceData> referenceData =
        referenceDataService.retrieveBadgeReferenceDataItem(RefDataGroupEnum.LA, LA_1_SHORTCODE);
    Optional<ReferenceData> expectedReferenceData = Optional.of(referenceDataLocalAuthority1);
    assertThat(referenceData).isEqualTo(expectedReferenceData);
  }

  @Test
  public void updateLocalAuthority_WhenShortCodeIsValid_ThenShouldUpdateLocalAuthority() {
    referenceDataService.updateLocalAuthority(LA_1_SHORTCODE, LOCAL_AUTHORITY);
    verify(referenceDataManagementApiClientMock, times(1))
        .updateLocalAuthority(LA_1_SHORTCODE, LOCAL_AUTHORITY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalAuthority_WhenShortCodeIsInvalid_ThenShouldUpdateLocalAuthority() {
    referenceDataService.updateLocalAuthority(null, LOCAL_AUTHORITY);
    verify(referenceDataManagementApiClientMock, times(0)).updateLocalAuthority(any(), any());
  }

  @Test
  public void updateLocalCouncil_WhenShortCodeIsValid_ThenShouldUpdateLocalAuthority() {
    LocalCouncil lc = LocalCouncil.builder().description("desc").build();
    referenceDataService.updateLocalCouncil("LC1", lc);
    verify(referenceDataManagementApiClientMock, times(1)).updateLocalCouncil("LC1", lc);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateLocalCouncil_WhenShortCodeIsInvalid_ThenShouldUpdateLocalAuthority() {
    referenceDataService.updateLocalCouncil(
        null, LocalCouncil.builder().description("ABC").build());
    verify(referenceDataManagementApiClientMock, times(0)).updateLocalCouncil(any(), any());
  }
}
