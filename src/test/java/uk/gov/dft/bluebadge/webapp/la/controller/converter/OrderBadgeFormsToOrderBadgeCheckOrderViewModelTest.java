package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBadgeFormsToOrderBadgeCheckOrderViewModelTest {

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
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS = "joe@blogs.com";
  protected static final String ELIGIBILITY = "PIP";
  protected static final String GENDER = "male";

  // processing
  protected static final String APPLICATION_DATE_DAY = "2";
  protected static final String APPLICATION_DATE_MONTH = "7";
  protected static final String APPLICATION_DATE_YEAR = "2018";
  protected static final String APPLICATION_CHANNEL = "paper";
  protected static final String LOCAL_AUTHORITY_REFERENCE_NUMBER = "AA110";
  protected static final String BADGE_START_DATE_DAY = "7";
  protected static final String BADGE_START_DATE_MONTH = "8";
  protected static final String BADGE_START_DATE_YEAR = "2018";
  protected static final String BADGE_EXPIRY_DATE_DAY = "7";
  protected static final String BADGE_EXPIRY_DATE_MONTH = "8";
  protected static final String BADGE_EXPIRY_DATE_YEAR = "2021";
  protected static final String DELIVER_TO = "badgeHolder";
  protected static final String DELIVERY_OPTIONS = "fast";

  // view model
  protected static final String DOB_VIEW_MODEL = DOB_DAY + "/" + DOB_MONTH + "/" + DOB_YEAR;
  protected static final String ADDRESS = BUILDING_AND_STREET + ", " + OPTIONAL_ADDRESS_FIELD + ", " + TOWN_OR_CITY + ", " + POSTCODE;
  protected static final String BADGE_START_DATE = BADGE_START_DATE_DAY + "/" + BADGE_START_DATE_MONTH + "/" + BADGE_START_DATE_YEAR;
  protected static final String BADGE_EXPIRY_DATE = BADGE_EXPIRY_DATE_DAY + "/" + BADGE_EXPIRY_DATE_MONTH + "/" + BADGE_EXPIRY_DATE_YEAR;
  protected static final String APPLICATION_DATE = APPLICATION_DATE_DAY + "/" + APPLICATION_DATE_MONTH + "/" + APPLICATION_DATE_YEAR;


  protected static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_DETAILS =
    OrderBadgePersonDetailsFormRequest.builder()
      .buildingAndStreet(BUILDING_AND_STREET)
      .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
      .contactDetailsName(CONTACT_DETAILS_NAME)
      .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
      .dobDay(Integer.valueOf(DOB_DAY))
      .dobMonth(Integer.valueOf(DOB_MONTH))
      .dobYear(Integer.valueOf(DOB_YEAR))
      .eligibility(ELIGIBILITY)
      .name(NAME)
      .gender(GENDER)
      .nino(NINO)
      .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
      .postcode(POSTCODE)
      .townOrCity(TOWN_OR_CITY)
      .build();

  protected static final OrderBadgeProcessingFormRequest FORM_REQUEST_PROCESSING =
    OrderBadgeProcessingFormRequest.builder()
      .applicationChannel(APPLICATION_CHANNEL)
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
      .deliverTo(DELIVER_TO)
      .deliveryOptions(DELIVERY_OPTIONS)
      .build();

  protected static final OrderBadgeCheckOrderViewModel VIEW_MODEL = OrderBadgeCheckOrderViewModel.builder()
    .fullName(NAME).dob(DOB_VIEW_MODEL).gender(GENDER).nino(NINO).address(ADDRESS).contactFullName(CONTACT_DETAILS_NAME).contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
    .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS).eligibility(ELIGIBILITY).localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
    .badgeStartDate(BADGE_START_DATE).badgeExpiryDate(BADGE_EXPIRY_DATE).applicationDate(APPLICATION_DATE).applicationChannel(APPLICATION_CHANNEL)
    .deliverTo(DELIVER_TO).deliveryOptions(DELIVERY_OPTIONS).build();

  private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converter;

  @Before
  public void setUp() {
    converter = new OrderBadgeFormsToOrderBadgeCheckOrderViewModel();
  }

  @Test
  public void convert_ShouldConvert() {
    OrderBadgePersonDetailsFormRequest details = OrderBadgePersonDetailsFormRequest.builder().build();
    OrderBadgeProcessingFormRequest processing = OrderBadgeProcessingFormRequest.builder().build();
    OrderBadgeCheckOrderViewModel viewModel = converter.convert(FORM_REQUEST_DETAILS, FORM_REQUEST_PROCESSING);
    OrderBadgeCheckOrderViewModel expectecViewModel = OrderBadgeCheckOrderViewModel.builder().build();
    assertThat(viewModel).isEqualTo(VIEW_MODEL);
  }
}
