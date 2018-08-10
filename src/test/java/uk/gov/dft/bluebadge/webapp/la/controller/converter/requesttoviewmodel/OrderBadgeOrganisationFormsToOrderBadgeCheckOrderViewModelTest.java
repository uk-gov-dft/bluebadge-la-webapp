package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest {

  protected static final OrderBadgeOrganisationDetailsFormRequest
      FORM_REQUEST_ORGANISATION_DETAILS =
          OrderBadgeOrganisationDetailsFormRequest.builder()
              .buildingAndStreet(BUILDING_AND_STREET)
              .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
              .contactDetailsSecondaryContactNumber((CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER))
              .contactDetailsName(CONTACT_DETAILS_NAME)
              .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
              .name(NAME)
              .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
              .postcode(POSTCODE)
              .townOrCity(TOWN_OR_CITY)
              .build();

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_ORGANISATION_PROCESSING =
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
          .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION)
          .build();

  protected static final OrderBadgeCheckOrderViewModel VIEW_MODEL_ORGANISATION =
      OrderBadgeCheckOrderViewModel.builder()
          .fullName(NAME)
          .address(ADDRESS)
          .contactFullName(CONTACT_DETAILS_NAME)
          .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .secondaryContactNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .badgeStartDate(BADGE_START_DATE)
          .badgeExpiryDate(BADGE_EXPIRY_DATE)
          .applicationDate(APPLICATION_DATE)
          .applicationChannel(APPLICATION_CHANNEL)
          .deliverTo(DELIVER_TO)
          .deliveryOptions(DELIVERY_OPTIONS)
          .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION)
          .build();
  private OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel converter;

  @Before
  public void setUp() {
    super.setUp();
    converter =
        new OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel(referenceDataServiceMock);
  }

  @Test
  public void convert_ShouldConvert() {
    assertThat(
            converter.convert(
                FORM_REQUEST_ORGANISATION_DETAILS, FORM_REQUEST_ORGANISATION_PROCESSING))
        .isEqualTo(VIEW_MODEL_ORGANISATION);
  }
}
