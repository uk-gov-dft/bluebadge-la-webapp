package uk.gov.dft.bluebadge.webapp.la.controller;

import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

public abstract class OrderBadgeBaseControllerTest {

  // details
  protected static final String NAME_FIELD = "name";
  protected static final String DOB_DAY_FIELD = "dobDay";
  protected static final String DOB_MONTH_FIELD = "dobMonth";
  protected static final String DOB_YEAR_FIELD = "dobYear";
  protected static final String DOB_FIELD = "dob";
  protected static final String BUILDING_AND_STREET_FIELD = "buildingAndStreet";
  protected static final String TOWN_OR_CITY_FIELD = "townOrCity";
  protected static final String POSTCODE_FIED = "postcode";
  protected static final String CONTACT_DETAILS_CONTACT_NUMBER_FIELD =
      "contactDetailsContactNumber";
  protected static final String ELIGIBILITY_FIELD = "eligibility";
  protected static final String NINO_FIELD = "nino";
  protected static final String OPTIONAL_ADDRESS_FIELD_FIELD = "optionalAddressField";
  protected static final String CONTACT_DETAILS_NAME_FIELD = "contactDetailsName";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS_FIELD = "contactDetailsEmailAddress";
  protected static final String GENDER_FIELD = "gender";

  protected static final String NAME = "My Name";
  protected static final String DOB_DAY = "15";
  protected static final String DOB_MONTH = "3";
  protected static final String DOB_YEAR = "1980";
  protected static final String DOB = "";
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

  protected static final String NAME_WRONG = "  My Na me 2";
  protected static final String DOB_DAY_WRONG = "32";
  protected static final String DOB_MONTH_WRONG = "13";
  protected static final String DOB_YEAR_WRONG = "2100";
  protected static final String DOB_WRONG = "";
  protected static final String NINO_WRONG = "BN10296";
  protected static final String BUILDING_AND_STREET_WRONG = "";
  protected static final String TOWN_OR_CITY_WRONG = "";
  protected static final String POSTCODE_WRONG = "TF8 ";
  protected static final String CONTACT_DETAILS_NAME_WRONG = "   mu name 2";
  protected static final String CONTACT_DETAILS_CONTACT_NUMBER_WRONG = "07700900";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS_WRONG = "joeblogscom";
  protected static final String ELIGIBILITY_WRONG = "";

  // processing
  protected static final String APPLICATION_DATE_DAY_FIELD = "applicationDateDay";
  protected static final String APPLICATION_DATE_MONTH_FIELD = "applicationDateMonth";
  protected static final String APPLICATION_DATE_YEAR_FIELD = "applicationDateYear";
  protected static final String APPLICATION_DATE_FIELD = "applicationDate";
  protected static final String APPLICATION_CHANNEL_FIELD = "applicationChannel";
  protected static final String LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD =
      "localAuthorityReferenceNumber";
  protected static final String BADGE_START_DATE_DAY_FIELD = "badgeStartDateDay";
  protected static final String BADGE_START_DATE_MONTH_FIELD = "badgeStartDateMonth";
  protected static final String BADGE_START_DATE_YEAR_FIELD = "badgeStartDateYear";
  protected static final String BADGE_START_DATE_FIELD = "badgeStartDate";
  protected static final String BADGE_EXPIRY_DATE_DAY_FIELD = "badgeExpiryDateDay";
  protected static final String BADGE_EXPIRY_DATE_MONTH_FIELD = "badgeExpiryDateMonth";
  protected static final String BADGE_EXPIRY_DATE_YEAR_FIELD = "badgeExpiryDateYear";
  protected static final String BADGE_EXPIRY_DATE_VALID_FIELD = "badgeExpiryDateValid";

  protected static final String DELIVER_TO_FIELD = "deliverTo";
  protected static final String DELIVERY_OPTIONS_FIELD = "deliveryOptions";

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

  protected static final String APPLICATION_DATE_DAY_WRONG = "32";
  protected static final String APPLICATION_DATE_MONTH_WRONG = "13";
  protected static final String APPLICATION_DATE_YEAR_WRONG = "201";
  protected static final String APPLICATION_CHANNEL_WRONG = "paper";
  protected static final String BADGE_START_DATE_DAY_WRONG = "7";
  protected static final String BADGE_START_DATE_MONTH_WRONG = "6";
  protected static final String BADGE_START_DATE_YEAR_WRONG = "2018";
  protected static final String BADGE_EXPIRY_DATE_DAY_WRONG = "5";
  protected static final String BADGE_EXPIRY_DATE_MONTH_WRONG = "6";
  protected static final String BADGE_EXPIRY_DATE_YEAR_WRONG = "2018";
  protected static final String DELIVER_TO_WRONG = "deliverTo";
  protected static final String DELIVERY_OPTIONS_WRONG = "deliveryOptions";

  protected static final String VIEW_MODEL_APPLICATION_DATE = "1/7/2018";
  protected static final String VIEW_MODEL_BADGE_START_DATE = "1/10/2045";
  protected static final String VIEW_MODEL_BADGE_EXPIRY_DATE = "1/10/2047";
  protected static final String VIEW_MODEL_DOB = "1/1/1990";
  protected static final String VIEW_MODEL_ADDRESS = "An address, a place, a postcode";

  protected static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_DETAILS =
      OrderBadgePersonDetailsFormRequest.builder()
          .buildingAndStreet(BUILDING_AND_STREET)
          .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .contactDetailsName(CONTACT_DETAILS_NAME)
          .dobDay(Integer.valueOf(DOB_DAY))
          .dobMonth(Integer.valueOf(DOB_MONTH))
          .dobYear(Integer.valueOf(DOB_YEAR))
          .eligibility(ELIGIBILITY)
          .name(NAME)
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

  protected MockMvc mockMvc;

  @Mock protected SecurityUtils securityUtilsMock;
  @Mock protected ReferenceDataService referenceDataServiceMock;
  @Mock protected BadgeService badgeServiceMock;
}
