package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgePersonFormsToOrderBadgeCheckOrderViewModelTest
    extends OrderBadgeBaseFormsToOrderBadgeCheckOrderViewModelTest {

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
