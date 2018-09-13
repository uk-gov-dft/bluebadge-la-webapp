package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public abstract class OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeTestData {

  // view model
  protected static final String DOB_VIEW_MODEL = DOB_DAY + "/" + DOB_MONTH + "/" + DOB_YEAR;
  protected static final String ADDRESS =
      BUILDING_AND_STREET + ", " + OPTIONAL_ADDRESS_FIELD + ", " + TOWN_OR_CITY + ", " + POSTCODE;
  protected static final String BADGE_START_DATE =
      BADGE_START_DATE_DAY + "/" + BADGE_START_DATE_MONTH + "/" + BADGE_START_DATE_YEAR;
  protected static final String BADGE_EXPIRY_DATE =
      BADGE_EXPIRY_DATE_DAY + "/" + BADGE_EXPIRY_DATE_MONTH + "/" + BADGE_EXPIRY_DATE_YEAR;
  protected static final String APPLICATION_DATE =
      APPLICATION_DATE_DAY + "/" + APPLICATION_DATE_MONTH + "/" + APPLICATION_DATE_YEAR;

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_PROCESSING =
      OrderBadgeProcessingFormRequest.builder()
          .applicationChannel(APPLICATION_CHANNEL_SHORTCODE)
          .applicationDateDay(Integer.valueOf(APPLICATION_DATE_DAY))
          .applicationDateMonth(Integer.valueOf(APPLICATION_DATE_MONTH))
          .applicationDateYear(Integer.valueOf(APPLICATION_DATE_YEAR))
          .localAuthorityReferenceNumber(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .badgeStartDateDay(Integer.valueOf(BADGE_START_DATE_DAY))
          .badgeStartDateMonth(Integer.valueOf(BADGE_START_DATE_MONTH))
          .badgeStartDateYear(Integer.valueOf(BADGE_START_DATE_YEAR))
          .badgeExpiryDateDay(Integer.valueOf(BADGE_EXPIRY_DATE_DAY))
          .badgeExpiryDateMonth(Integer.valueOf(BADGE_EXPIRY_DATE_MONTH))
          .badgeExpiryDateYear(Integer.valueOf(BADGE_EXPIRY_DATE_YEAR))
          .deliverTo(DELIVER_TO_SHORTCODE)
          .deliveryOptions(DELIVERY_OPTIONS_SHORTCODE)
          .build();

  // ReferenceData
  private ReferenceData referenceData1;
  private ReferenceData referenceData2;
  private ReferenceData referenceData3;
  private ReferenceData referenceData4;
  private ReferenceData referenceData5;

  @Mock ReferenceDataService referenceDataServiceMock;

  public void setUp() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    referenceData1 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1)
            .shortCode(ELIGIBILITY_SHORTCODE)
            .description(ELIGIBILITY);
    referenceData2 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 2)
            .shortCode(GENDER_SHORTCODE)
            .description(GENDER);
    referenceData3 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 3)
            .shortCode(APPLICATION_CHANNEL_SHORTCODE)
            .description(APPLICATION_CHANNEL);
    referenceData4 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 4)
            .shortCode(DELIVER_TO_SHORTCODE)
            .description(DELIVER_TO);
    referenceData5 =
        ReferenceDataUtils.buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 5)
            .shortCode(DELIVERY_OPTIONS_SHORTCODE)
            .description(DELIVERY_OPTIONS);

    when(referenceDataServiceMock.retrieveBadgeEligibilityDisplayValue(ELIGIBILITY_SHORTCODE))
        .thenReturn(ELIGIBILITY);
    when(referenceDataServiceMock.retrieveBadgeGenderDisplayValue(GENDER_SHORTCODE))
        .thenReturn(GENDER);
    when(referenceDataServiceMock.retrieveBadgeApplicationChannelDisplayValue(
            APPLICATION_CHANNEL_SHORTCODE))
        .thenReturn(APPLICATION_CHANNEL);
    when(referenceDataServiceMock.retrieveBadgeDeliverToDisplayValue(DELIVER_TO_SHORTCODE))
        .thenReturn(DELIVER_TO);
    when(referenceDataServiceMock.retrieveBadgeDeliveryOptionDisplayValue(
            DELIVERY_OPTIONS_SHORTCODE))
        .thenReturn(DELIVERY_OPTIONS);
  }
}
