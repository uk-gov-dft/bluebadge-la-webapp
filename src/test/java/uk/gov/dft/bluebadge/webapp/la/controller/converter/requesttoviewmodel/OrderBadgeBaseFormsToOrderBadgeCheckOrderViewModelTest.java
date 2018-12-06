package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public abstract class OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeTestData {

  // view model
  static final String DOB_VIEW_MODEL = DOB_DAY + "/" + DOB_MONTH + "/" + DOB_YEAR;
  static final String ADDRESS =
      BUILDING_AND_STREET + ", " + OPTIONAL_ADDRESS_FIELD + ", " + TOWN_OR_CITY + ", " + POSTCODE;
  static final String BADGE_START_DATE =
      BADGE_START_DATE_DAY + "/" + BADGE_START_DATE_MONTH + "/" + BADGE_START_DATE_YEAR;
  static final String BADGE_EXPIRY_DATE =
      BADGE_EXPIRY_DATE_DAY + "/" + BADGE_EXPIRY_DATE_MONTH + "/" + BADGE_EXPIRY_DATE_YEAR;
  static final String APPLICATION_DATE =
      APPLICATION_DATE_DAY + "/" + APPLICATION_DATE_MONTH + "/" + APPLICATION_DATE_YEAR;

  @Mock ReferenceDataService referenceDataServiceMock;

  public void setUp() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);
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
