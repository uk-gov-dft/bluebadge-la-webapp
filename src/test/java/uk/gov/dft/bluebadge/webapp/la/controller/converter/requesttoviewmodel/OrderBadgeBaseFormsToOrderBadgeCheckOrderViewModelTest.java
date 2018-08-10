package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public abstract class OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest {

  // details
  protected static final String NAME = "My Name";
  protected static final String DOB_DAY = "15";
  protected static final String DOB_MONTH = "3";
  protected static final String DOB_YEAR = "1980";
  protected static final String NINO = "BN102966C";
  protected static final String BUILDING_AND_STREET = "Building and street";
  protected static final String OPTIONAL_ADDRESS_FIELD = "Optional address field";
  protected static final String TOWN_OR_CITY = "Town or city";
  protected static final String POSTCODE = "TF8 6GF";
  protected static final String CONTACT_DETAILS_NAME = "Contact details name";
  protected static final String CONTACT_DETAILS_CONTACT_NUMBER = "07700900077";
  protected static final String CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER = "07700900099";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS = "joe@blogs.com";
  protected static final String ELIGIBILITY = "pip";
  protected static final String ELIGIBILITY_SHORTCODE = "PIP";
  protected static final String GENDER = "male";
  protected static final String GENDER_SHORTCODE = "MALE";

  // processing
  protected static final String APPLICATION_DATE_DAY = "2";
  protected static final String APPLICATION_DATE_MONTH = "7";
  protected static final String APPLICATION_DATE_YEAR = "2018";
  protected static final String APPLICATION_CHANNEL = "paper";
  protected static final String APPLICATION_CHANNEL_SHORTCODE = "PAPER";
  protected static final String LOCAL_AUTHORITY_REFERENCE_NUMBER = "AA110";
  protected static final String BADGE_START_DATE_DAY = "7";
  protected static final String BADGE_START_DATE_MONTH = "8";
  protected static final String BADGE_START_DATE_YEAR = "2018";
  protected static final String BADGE_EXPIRY_DATE_DAY = "7";
  protected static final String BADGE_EXPIRY_DATE_MONTH = "8";
  protected static final String BADGE_EXPIRY_DATE_YEAR = "2021";
  protected static final String DELIVER_TO = "badgeHolder";
  protected static final String DELIVER_TO_SHORTCODE = "HOLDER";
  protected static final String DELIVERY_OPTIONS = "fast";
  protected static final String DELIVERY_OPTIONS_SHORTCODE = "FAST";
  protected static final String NUMBER_OF_BADGES_PERSON = "1";
  protected static final String NUMBER_OF_BADGES_ORGANISATION = "3";

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

    when(referenceDataServiceMock.retrieveEligibilityDisplayValue(ELIGIBILITY_SHORTCODE))
        .thenReturn(ELIGIBILITY);
    when(referenceDataServiceMock.retrieveGenderDisplayValue(GENDER_SHORTCODE)).thenReturn(GENDER);
    when(referenceDataServiceMock.retrieveApplicationChannelDisplayValue(
            APPLICATION_CHANNEL_SHORTCODE))
        .thenReturn(APPLICATION_CHANNEL);
    when(referenceDataServiceMock.retrieveDeliverToDisplayValue(DELIVER_TO_SHORTCODE))
        .thenReturn(DELIVER_TO);
    when(referenceDataServiceMock.retrieveDeliveryOptionDisplayValue(DELIVERY_OPTIONS_SHORTCODE))
        .thenReturn(DELIVERY_OPTIONS);
  }
}
