package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
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
  private static final String STATUS_1_SHORTCODE = "status 1 short code";
  private static final String STATUS_2_SHORTCODE = "status 2 short code";
  private static final String STATUS_1 = "status 1";
  private static final String STATUS_2 = "status 2";
  private static final String LA_1_SHORTCODE = "ABERD";
  private static final String LA_2_SHORTCODE = "BARNS";
  private static final String LA_1 = "Aberdeenshire council";
  private static final String LA_2 = "Barnsley MBC";

  @Mock private ReferenceDataApiClient referenceDataManagementApiClientMock;

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
  private ReferenceData referenceDataStatus1;
  private ReferenceData referenceDataStatus2;
  private ReferenceData referenceDataLocalAuthority1;
  private ReferenceData referenceDataLocalAuthority2;

  private List<ReferenceData> referenceDataList;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    referenceDataService = new ReferenceDataService(referenceDataManagementApiClientMock);

    referenceDataEligibility1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1)
            .shortCode(ELIGIBILITY_1_SHORTCODE)
            .description(ELIGIBILITY_1);
    referenceDataEligibility2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 2)
            .shortCode(ELIGIBILITY_2_SHORTCODE)
            .description(ELIGIBILITY_2);
    referenceDataGender1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 3)
            .shortCode(GENDER_1_SHORTCODE)
            .description(GENDER_1);
    referenceDataGender2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4)
            .shortCode(GENDER_2_SHORTCODE)
            .description(GENDER_2);
    referenceDataApplicationChannel1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 5)
            .shortCode(APPLICATION_CHANNEL_1_SHORTCODE)
            .description(APPLICATION_CHANNEL_1);
    referenceDataApplicationChannel2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 6)
            .shortCode(APPLICATION_CHANNEL_2_SHORTCODE)
            .description(APPLICATION_CHANNEL_2);
    referenceDataDeliverTo1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 7)
            .shortCode(DELIVER_TO_1_SHORTCODE)
            .description(DELIVER_TO_1);
    referenceDataDeliverTo2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 8)
            .shortCode(DELIVER_TO_2_SHORTCODE)
            .description(DELIVER_TO_2);
    referenceDataDeliveryOptions1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 9)
            .shortCode(DELIVERY_OPTIONS_1_SHORTCODE)
            .description(DELIVERY_OPTIONS_1);
    referenceDataDeliveryOptions2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 10)
            .shortCode(DELIVERY_OPTIONS_2_SHORTCODE)
            .description(DELIVERY_OPTIONS_2);
    referenceDataStatus1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.STATUS.getGroupKey(), 11)
            .shortCode(STATUS_1_SHORTCODE)
            .description(STATUS_1);
    referenceDataStatus2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.STATUS.getGroupKey(), 12)
            .shortCode(STATUS_2_SHORTCODE)
            .description(STATUS_2);
    referenceDataLocalAuthority1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.LA.getGroupKey(), 13)
            .shortCode(LA_1_SHORTCODE)
            .description(LA_1);
    referenceDataLocalAuthority2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.LA.getGroupKey(), 14)
            .shortCode(LA_2_SHORTCODE)
            .description(LA_2);
    referenceDataList =
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
            referenceDataStatus1,
            referenceDataStatus2,
            referenceDataLocalAuthority1,
            referenceDataLocalAuthority2);
    when(referenceDataManagementApiClientMock.retrieveReferenceData(RefDataDomainEnum.BADGE))
        .thenReturn(referenceDataList);
  }

  @Test
  public void retrieveEligibilities_ShouldReturnEligibilities() {
    List<ReferenceData> eligibilities = referenceDataService.retrieveEligilities();
    assertThat(eligibilities)
        .containsExactlyInAnyOrder(referenceDataEligibility1, referenceDataEligibility2);
  }

  @Test
  public void retrieveGenders_ShouldReturnGender() {
    List<ReferenceData> gender = referenceDataService.retrieveGenders();
    assertThat(gender).containsExactlyInAnyOrder(referenceDataGender1, referenceDataGender2);
  }

  @Test
  public void retrieveApplicationChannels_ShouldReturnApplicationChannels() {
    List<ReferenceData> applicationChannels = referenceDataService.retrieveApplicationChannels();
    assertThat(applicationChannels)
        .containsExactlyInAnyOrder(
            referenceDataApplicationChannel1, referenceDataApplicationChannel2);
  }

  @Test
  public void retrieveDeliverTos_ShouldReturnDeliverTos() {
    List<ReferenceData> deliverTos = referenceDataService.retrieveDeliverTos();
    assertThat(deliverTos)
        .containsExactlyInAnyOrder(referenceDataDeliverTo1, referenceDataDeliverTo2);
  }

  @Test
  public void retrieveDeliveryOptions_ShouldReturnDeliveryOptions() {
    List<ReferenceData> deliveryOptions = referenceDataService.retrieveDeliveryOptions();
    assertThat(deliveryOptions)
        .containsExactlyInAnyOrder(referenceDataDeliveryOptions1, referenceDataDeliveryOptions2);
  }

  @Test
  public void retrieveStatuses_ShouldReturnStatuses() {
    List<ReferenceData> statuses = referenceDataService.retrieveStatuses();
    assertThat(statuses).containsExactlyInAnyOrder(referenceDataStatus1, referenceDataStatus2);
  }

  @Test
  public void retrieveLocalAuthorities_ShouldReturnStatuses() {
    List<ReferenceData> statuses = referenceDataService.retrieveLocalAuthorities();
    assertThat(statuses)
        .containsExactlyInAnyOrder(referenceDataLocalAuthority1, referenceDataLocalAuthority2);
  }

  @Test
  public void retrieveEligibilityDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveEligibilityDisplayValue(ELIGIBILITY_1_SHORTCODE))
        .isEqualTo(ELIGIBILITY_1);
  }

  @Test
  public void retrieveGenderDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveGenderDisplayValue(GENDER_1_SHORTCODE))
        .isEqualTo(GENDER_1);
  }

  @Test
  public void retrieveApplicationChannelDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveApplicationChannelDisplayValue(
                APPLICATION_CHANNEL_1_SHORTCODE))
        .isEqualTo(APPLICATION_CHANNEL_1);
  }

  @Test
  public void retrieveDeliverToDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveDeliverToDisplayValue(DELIVER_TO_1_SHORTCODE))
        .isEqualTo(DELIVER_TO_1);
  }

  @Test
  public void retrieveDeliveryOptionsDisplayValue_ShouldWork() {
    assertThat(
            referenceDataService.retrieveDeliveryOptionDisplayValue(DELIVERY_OPTIONS_1_SHORTCODE))
        .isEqualTo(DELIVERY_OPTIONS_1);
  }

  @Test
  public void retrieveStatusDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveStatusDisplayValue(STATUS_1_SHORTCODE))
        .isEqualTo(STATUS_1);
  }

  @Test
  public void retrieveLocalAuthorityDisplayValue_ShouldWork() {
    assertThat(referenceDataService.retrieveLocalAuthorityDisplayValue(LA_1_SHORTCODE))
        .isEqualTo(LA_1);
  }
}
