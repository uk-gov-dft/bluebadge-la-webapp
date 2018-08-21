package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Organisation;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeOrganisationDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

@Slf4j
public class OrderBadgeTestData {

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
  protected static final String CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD =
      "contactDetailsSecondaryContactNumber";
  protected static final String ELIGIBILITY_FIELD = "eligibility";
  protected static final String NINO_FIELD = "nino";
  protected static final String OPTIONAL_ADDRESS_FIELD_FIELD = "optionalAddressField";
  protected static final String CONTACT_DETAILS_NAME_FIELD = "contactDetailsName";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS_FIELD = "contactDetailsEmailAddress";
  protected static final String GENDER_FIELD = "gender";
  protected static final String PHOTO_FIELD = "photo";

  protected static final String SESSION_FORM_REQUEST_INDEX = "formRequest-order-a-badge-index";
  protected static final String SESSION_FORM_REQUEST_DETAILS = "formRequest-order-a-badge-details";

  // details
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
  protected static final String CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER = "07700900099";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS = "joe@blogs.com";
  protected static final String ELIGIBILITY = "pip";
  protected static final String ELIGIBILITY_SHORTCODE = "PIP";
  protected static final String GENDER = "male";
  protected static final String GENDER_SHORTCODE = "MALE";
  protected static final MockMultipartFile EMPTY_PHOTO =
      new MockMultipartFile("photo", "", "", "".getBytes());

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
  protected static final String CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_WRONG = "345345345";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS_WRONG = "joeblogscom";
  protected static final String ELIGIBILITY_WRONG = "";
  protected static final MockMultipartFile PHOTO_WRONG =
      new MockMultipartFile("photo", "file.pdf", "application/pdf", "pdfData".getBytes());

  protected static final MockMultipartFile PHOTO_CONTENT_WRONG =
      new MockMultipartFile("photo", "file.jpg", "image/jpeg", "pdfData".getBytes());

  protected static final String IMAGE_BASE64 = "base64";

  public static MockMultipartFile PHOTO() {
    try {
      File file = new File(System.getProperty("user.dir") + "/src/test/resources/icon-test.jpg");
      FileInputStream stream = new FileInputStream(file);
      return new MockMultipartFile("photo", "", "image/jpeg", stream);
    } catch (Exception ex) {
      log.warn("Cannot read photo file. Exception: [{}]", ex);
      return null;
    }
  }

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
  protected static final String NUMBER_OF_BADGES_FIELD = "numberOfBadges";

  // processing
  protected static final String APPLICATION_DATE_DAY = "2";
  protected static final String APPLICATION_DATE_MONTH = "7";
  protected static final String APPLICATION_DATE_YEAR = "2018";
  protected static final String APPLICATION_CHANNEL = "paper";
  protected static final String APPLICATION_CHANNEL_SHORTCODE = "PAPER";
  protected static final String LOCAL_AUTHORITY_REFERENCE_NUMBER = "AA110";
  protected static final String BADGE_START_DATE_DAY = "7";
  protected static final String BADGE_START_DATE_MONTH = "8";
  //protected static final String BADGE_START_DATE_YEAR = "2018";
  protected static final String BADGE_START_DATE_YEAR =
      Integer.toString(LocalDate.now().plusYears(1).getYear());
  protected static final String BADGE_EXPIRY_DATE_DAY = "7";
  protected static final String BADGE_EXPIRY_DATE_MONTH = "8";
  //protected static final String BADGE_EXPIRY_DATE_YEAR = "2021";
  protected static final String BADGE_EXPIRY_DATE_YEAR =
      Integer.toString(LocalDate.now().plusYears(2).getYear());
  protected static final String DELIVER_TO = "Home adress";
  protected static final String DELIVER_TO_SHORTCODE = "HOME";
  protected static final String DELIVERY_OPTIONS = "fast";
  protected static final String DELIVERY_OPTIONS_SHORTCODE = "FAST";
  protected static final String NUMBER_OF_BADGES_PERSON = "1";
  protected static final String NUMBER_OF_BADGES_ORGANISATION = "3";

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

  // form requests
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
          .photo(PHOTO())
          .thumbBase64("base64")
          .byteImage("someBytes".getBytes())
          .build();

  protected static final OrderBadgeOrganisationDetailsFormRequest
      FORM_REQUEST_ORGANISATION_DETAILS =
          OrderBadgeOrganisationDetailsFormRequest.builder()
              .buildingAndStreet(BUILDING_AND_STREET)
              .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
              .contactDetailsSecondaryContactNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
              .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
              .contactDetailsName(CONTACT_DETAILS_NAME)
              .name(NAME)
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

  protected static final LocalDate SERVICE_MODEL_APPLICATION_DATE = LocalDate.of(2018, 7, 2);
  protected static final String SERVICE_MODEL_APPLICATION_CHANNEL = "PAPER";
  protected static final String SERVICE_MODEL_DELIVER_TO = DELIVER_TO_SHORTCODE;

  protected static final String BADGE_NUMBER = "MyBadgeNumber123";
  protected static final List<String> BADGE_NUMBERS = Lists.newArrayList(BADGE_NUMBER);
  protected static final LocalDate SERVICE_MODEL_DOB = LocalDate.of(1980, 3, 15);
  protected static final int SERVICE_MODEL_NUMBER_OF_BADGES_ORGANISATION =
      Integer.valueOf(NUMBER_OF_BADGES_ORGANISATION);
  protected static final int SERVICE_MODEL_NUMBER_OF_BADGES_PERSON =
      Integer.valueOf(NUMBER_OF_BADGES_PERSON);
  protected static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  protected static final LocalDate SERVICE_MODEL_START_DATE =
      LocalDate.of(LocalDate.now().getYear(), 8, 7).plusYears(1);
  protected static final LocalDate SERVICE_MODEL_EXPIRY_DATE =
      LocalDate.of(LocalDate.now().getYear(), 8, 7).plusYears(2);
  protected static final int SERVICE_MODEL_NUMBER_OF_BADGES =
      Integer.valueOf(NUMBER_OF_BADGES_PERSON);

  static final Contact contactOrganisation =
      new Contact()
          .buildingStreet(BUILDING_AND_STREET)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .fullName(CONTACT_DETAILS_NAME)
          .line2(OPTIONAL_ADDRESS_FIELD)
          .postCode(POSTCODE)
          .townCity(TOWN_OR_CITY)
          .primaryPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .secondaryPhoneNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER);
  static final Organisation organisation = new Organisation().badgeHolderName(NAME);
  static final Party partyOrganisation =
      new Party()
          .contact(contactOrganisation)
          .organisation(organisation)
          .typeCode("ORG")
          .person(null);
  protected static final BadgeOrderRequest BADGE_ORDER_REQUEST_ORGANISATION =
      new BadgeOrderRequest()
          .applicationDate(SERVICE_MODEL_APPLICATION_DATE)
          .applicationChannelCode(SERVICE_MODEL_APPLICATION_CHANNEL)
          .deliverToCode(SERVICE_MODEL_DELIVER_TO)
          .deliveryOptionCode(DELIVERY_OPTIONS_SHORTCODE)
          .startDate(SERVICE_MODEL_START_DATE)
          .expiryDate(SERVICE_MODEL_EXPIRY_DATE)
          .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
          .localAuthorityRef(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .numberOfBadges(SERVICE_MODEL_NUMBER_OF_BADGES_ORGANISATION)
          .party(partyOrganisation);

  static final Contact CONTACT_PERSON =
      new Contact()
          .buildingStreet(BUILDING_AND_STREET)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .fullName(CONTACT_DETAILS_NAME)
          .line2(OPTIONAL_ADDRESS_FIELD)
          .postCode(POSTCODE)
          .townCity(TOWN_OR_CITY)
          .primaryPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER);
  static final Person PERSON =
      new Person()
          .genderCode(GENDER_SHORTCODE)
          .badgeHolderName(NAME)
          .dob(SERVICE_MODEL_DOB)
          .nino(NINO);
  static final Party PARTY_PERSON =
      new Party().contact(CONTACT_PERSON).person(PERSON).typeCode("PERSON").organisation(null);
  protected static final BadgeOrderRequest BADGE_ORDER_REQUEST_PERSON =
      new BadgeOrderRequest()
          .applicationDate(SERVICE_MODEL_APPLICATION_DATE)
          .applicationChannelCode(SERVICE_MODEL_APPLICATION_CHANNEL)
          .deliverToCode(SERVICE_MODEL_DELIVER_TO)
          .deliveryOptionCode(DELIVERY_OPTIONS_SHORTCODE)
          .eligibilityCode(ELIGIBILITY_SHORTCODE)
          .startDate(SERVICE_MODEL_START_DATE)
          .expiryDate(SERVICE_MODEL_EXPIRY_DATE)
          .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
          .localAuthorityRef(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .numberOfBadges(SERVICE_MODEL_NUMBER_OF_BADGES_PERSON)
          .party(PARTY_PERSON);

  protected static final BadgeOrderRequest BADGE_ORDER_REQUEST_PERSON_WITH_IMAGE = BADGE_ORDER_REQUEST_PERSON;

  protected static final String VIEW_MODEL_APPLICATION_DATE = "2/7/2018";
  protected static final String VIEW_MODEL_BADGE_START_DATE =
      "7/8/" + LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));
  protected static final String VIEW_MODEL_BADGE_EXPIRY_DATE =
      "7/8/" + LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yyyy"));
  protected static final String VIEW_MODEL_DOB = "1/1/1990";
  protected static final String VIEW_MODEL_ADDRESS =
      "Building and street, Optional address field, Town or city, TF8 6GF";
  protected static final String NUMBER_OF_BADGES_PERSON_VIEW_MODEL =
      String.valueOf(NUMBER_OF_BADGES_PERSON);
  protected static final String NUMBER_OF_BADGES_ORGANISATION_VIEW_MODEL =
      String.valueOf(NUMBER_OF_BADGES_ORGANISATION);

  protected static final OrderBadgeCheckOrderViewModel CHECK_ORDER_ORGANISATION_VIEW_MODEL =
      OrderBadgeCheckOrderViewModel.builder()
          .deliveryOptions(DELIVERY_OPTIONS)
          .applicationChannel(APPLICATION_CHANNEL)
          .applicationDate(VIEW_MODEL_APPLICATION_DATE)
          .applicationChannel(APPLICATION_CHANNEL)
          .deliverTo(DELIVER_TO)
          .badgeStartDate(VIEW_MODEL_BADGE_START_DATE)
          .badgeExpiryDate(VIEW_MODEL_BADGE_EXPIRY_DATE)
          .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .contactFullName(CONTACT_DETAILS_NAME)
          .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .secondaryContactNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
          .fullName(NAME)
          .address(VIEW_MODEL_ADDRESS)
          .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION_VIEW_MODEL)
          .build();
}
