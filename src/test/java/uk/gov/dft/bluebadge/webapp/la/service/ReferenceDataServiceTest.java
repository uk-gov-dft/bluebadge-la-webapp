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

  public static final String ELIGIBILITY_1_SHORTCODE = "eligibilityShortCode1";
  public static final String GENDER_1_SHORTCODE = "MALE";
  public static final String ELIGIBILITY_2_SHORTCODE = "eligibilityShortCode2";
  public static final String GENDER_2_SHORTCODE = "FEMALE";
  public static final String APPLICATION_CHANNEL_1_SHORTCODE = "APPLICATION_CHANNEL1";
  public static final String APPLICATION_CHANNEL_2_SHORTCODE = "APPLICATION_CHANNEL2";
  public static final String DELIVER_TO_1_SHORTCODE = "DELIVER_TO1";
  public static final String DELIVER_TO_2_SHORTCODE = "DELIVER_TO2";
  public static final String DELIVERY_OPTIONS_1_SHORTCODE = "DELIVERY_OPTIONS1";
  public static final String DELIVERY_OPTIONS_2_SHORTCODE = "DELIVERY_OPTIONS2";
  public static final String ELIGIBILITY_1 = "eligibilityValue1";
  public static final String GENDER_1 = "male";
  public static final String ELIGIBILITY_2 = "eligibilityValue2";
  public static final String GENDER_2 = "female";
  public static final String APPLICATION_CHANNEL_1 = "application channel 1";
  public static final String APPLICATION_CHANNEL_2 = "application channel 2";
  public static final String DELIVER_TO_1 = "deliver to 1";
  public static final String DELIVER_TO_2 = "deliver to 2";
  public static final String DELIVERY_OPTIONS_1 = "delivery options 1";
  public static final String DELIVERY_OPTIONS_2 = "delivery options 2";
  @Mock private ReferenceDataApiClient referenceDataManagementApiClientMock;

  private ReferenceDataService referenceDataService;

  private ReferenceData referenceData1;
  private ReferenceData referenceData2;
  private ReferenceData referenceData3;
  private ReferenceData referenceData4;
  private ReferenceData referenceData5;
  private ReferenceData referenceData6;
  private ReferenceData referenceData7;
  private ReferenceData referenceData8;
  private ReferenceData referenceData9;
  private ReferenceData referenceData10;

  private List<ReferenceData> referenceData;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    referenceDataService = new ReferenceDataService(referenceDataManagementApiClientMock);

    referenceData1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1)
            .shortCode(ELIGIBILITY_1_SHORTCODE)
            .description(ELIGIBILITY_1);
    referenceData2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 2)
            .shortCode(GENDER_1_SHORTCODE)
            .description(GENDER_1);
    referenceData3 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 3)
            .shortCode(ELIGIBILITY_2_SHORTCODE)
            .description(ELIGIBILITY_2);
    referenceData4 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4)
            .shortCode(GENDER_2_SHORTCODE)
            .description(GENDER_2);
    referenceData5 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 5)
            .shortCode(APPLICATION_CHANNEL_1_SHORTCODE)
            .description(APPLICATION_CHANNEL_1);
    referenceData6 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 6)
            .shortCode(APPLICATION_CHANNEL_2_SHORTCODE)
            .description(APPLICATION_CHANNEL_2);
    referenceData7 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 7)
            .shortCode(DELIVER_TO_1_SHORTCODE)
            .description(DELIVER_TO_1);
    referenceData8 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 8)
            .shortCode(DELIVER_TO_2_SHORTCODE)
            .description(DELIVER_TO_2);
    referenceData9 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 9)
            .shortCode(DELIVERY_OPTIONS_1_SHORTCODE)
            .description(DELIVERY_OPTIONS_1);
    referenceData10 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 10)
            .shortCode(DELIVERY_OPTIONS_2_SHORTCODE)
            .description(DELIVERY_OPTIONS_2);
    referenceData =
        Lists.newArrayList(
            referenceData1,
            referenceData2,
            referenceData3,
            referenceData4,
            referenceData5,
            referenceData6,
            referenceData7,
            referenceData8,
            referenceData9,
            referenceData10);
    when(referenceDataManagementApiClientMock.retrieveReferenceData(RefDataDomainEnum.BADGE))
        .thenReturn(referenceData);
  }

  @Test
  public void retrieveEligibilities_ShouldReturnEligibilities() {
    List<ReferenceData> eligibilities = referenceDataService.retrieveEligilities();
    assertThat(eligibilities).containsExactlyInAnyOrder(referenceData1, referenceData3);
  }

  @Test
  public void retrieveGender_ShouldReturnGender() {
    List<ReferenceData> gender = referenceDataService.retrieveGender();
    assertThat(gender).containsExactlyInAnyOrder(referenceData2, referenceData4);
  }

  @Test
  public void retrieveApplicationChannel_ShouldReturnApplicationChannels() {
    List<ReferenceData> applicationChannels = referenceDataService.retrieveApplicationChannel();
    assertThat(applicationChannels).containsExactlyInAnyOrder(referenceData5, referenceData6);
  }

  @Test
  public void retrieveDeliverTo_ShouldReturnDeliverTos() {
    List<ReferenceData> deliverTos = referenceDataService.retrieveDeliverTo();
    assertThat(deliverTos).containsExactlyInAnyOrder(referenceData7, referenceData8);
  }

  @Test
  public void retrieveDeliveryOption_ShouldReturnDeliveryOptions() {
    List<ReferenceData> deliveryOptions = referenceDataService.retrieveDeliveryOptions();
    assertThat(deliveryOptions).containsExactlyInAnyOrder(referenceData9, referenceData10);
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
            referenceDataService.retrieveDeliveryOptionsDisplayValue(DELIVERY_OPTIONS_1_SHORTCODE))
        .isEqualTo(DELIVERY_OPTIONS_1);
  }
}
