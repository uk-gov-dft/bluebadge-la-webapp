package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgePersonFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest {

  protected static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_PERSON_DETAILS =
      OrderBadgePersonDetailsFormRequest.builder()
          .buildingAndStreet(BUILDING_AND_STREET)
          .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .contactDetailsName(CONTACT_DETAILS_NAME)
          .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .dobDay(Integer.valueOf(DOB_DAY))
          .dobMonth(Integer.valueOf(DOB_MONTH))
          .dobYear(Integer.valueOf(DOB_YEAR))
          .eligibility(ELIGIBILITY_SHORTCODE)
          .name(NAME)
          .gender(GENDER_SHORTCODE)
          .nino(NINO)
          .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
          .postcode(POSTCODE)
          .townOrCity(TOWN_OR_CITY)
          .build();

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_PERSON_PROCESSING =
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
          .numberOfBadges(NUMBER_OF_BADGES_PERSON)
          .build();

  protected static final OrderBadgeCheckOrderViewModel VIEW_MODEL_PERSON =
      OrderBadgeCheckOrderViewModel.builder()
          .fullName(NAME)
          .dob(DOB_VIEW_MODEL)
          .gender(GENDER)
          .nino(NINO)
          .address(ADDRESS)
          .contactFullName(CONTACT_DETAILS_NAME)
          .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .eligibility(ELIGIBILITY)
          .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .badgeStartDate(BADGE_START_DATE)
          .badgeExpiryDate(BADGE_EXPIRY_DATE)
          .applicationDate(APPLICATION_DATE)
          .applicationChannel(APPLICATION_CHANNEL)
          .deliverTo(DELIVER_TO)
          .deliveryOptions(DELIVERY_OPTIONS)
          .numberOfBadges(NUMBER_OF_BADGES_PERSON)
          .build();

  private OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel converter;

  @Before
  public void setUp() {
    super.setUp();

    converter = new OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel(referenceDataServiceMock);
  }

  @Test
  public void convert_ShouldConvert() {
    assertThat(converter.convert(FORM_REQUEST_PERSON_DETAILS, FORM_REQUEST_PERSON_PROCESSING))
        .isEqualTo(VIEW_MODEL_PERSON);
  }
}
