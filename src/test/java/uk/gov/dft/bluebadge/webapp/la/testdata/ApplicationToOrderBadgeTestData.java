package uk.gov.dft.bluebadge.webapp.la.testdata;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Eligibility;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.GenderCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

public class ApplicationToOrderBadgeTestData extends OrderBadgeTestData {

  // Mock application data
  private static final UUID APPLICATION_ID = UUID.randomUUID();
  private static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final OffsetDateTime SUBMISSION_DATE =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
  private static final String EXISTING_BADGE_NO = "ABCDEF";
  private static final LocalDate DOB = LocalDate.of(1980, 3, 15);
  protected static final Eligibility APPLICANT_ELIGIBILITY =
      new Eligibility().typeCode(EligibilityCodeField.fromValue(ELIGIBILITY.toUpperCase()));
  protected static final String APPLICATION_CHANNEL_SHORTCODE = "ONLINE";

  // APPLICANT PERSON
  protected static final AppPerson APP_PERSON =
      new AppPerson()
          .genderCode(GenderCodeField.fromValue(GENDER_SHORTCODE))
          .badgeHolderName(NAME)
          .dob(DOB)
          .nino(NINO);

  // APPLICANT CONTACT
  private static final AppContact APP_CONTACT =
      new AppContact()
          .fullName(OrderBadgeTestData.NAME)
          .primaryPhoneNumber(OrderBadgeTestData.CONTACT_DETAILS_CONTACT_NUMBER)
          .emailAddress(OrderBadgeTestData.CONTACT_DETAILS_EMAIL_ADDRESS)
          .buildingStreet(OrderBadgeTestData.BUILDING_AND_STREET)
          .townCity(OrderBadgeTestData.TOWN_OR_CITY)
          .postCode(OrderBadgeTestData.POSTCODE);

  // APPLICANT PARTY
  private static final AppParty APP_PARTY =
      new AppParty()
          .contact(APP_CONTACT)
          .person(APP_PERSON)
          .organisation(null)
          .typeCode(PartyTypeCodeField.PERSON);

  // APPLICATION
  public static final Application APPLICATION =
      Application.builder()
          .applicationId(APPLICATION_ID.toString())
          .applicationTypeCode(APPLICATION_TYPE)
          .localAuthorityCode(LOCAL_AUTHORITY_SHORT_CODE)
          .eligibility(APPLICANT_ELIGIBILITY)
          .paymentTaken(false)
          .submissionDate(SUBMISSION_DATE)
          .existingBadgeNumber(EXISTING_BADGE_NO)
          .party(APP_PARTY)
          .build();

  // FORM REQUESTS
  public static final OrderBadgeIndexFormRequest APPLICATION_TO_ORDER_BADGE_INDEX_FORM_REQUEST =
      OrderBadgeIndexFormRequest.builder()
          .applicantType(PartyTypeCodeField.PERSON.toString().toLowerCase())
          .build();

  public static final OrderBadgePersonDetailsFormRequest
      APPLICATION_TO_ORDER_BADGE_PERSON_DETAILS_FORM_REQUEST =
          OrderBadgePersonDetailsFormRequest.builder()
              .name(NAME)
              .nino(NINO)
              .gender(GENDER_SHORTCODE)
              .eligibility(ELIGIBILITY_SHORTCODE)
              .dobDay(Integer.parseInt(DOB_DAY))
              .dobMonth(Integer.parseInt(DOB_MONTH))
              .dobYear(Integer.parseInt(DOB_YEAR))
              .buildingAndStreet(BUILDING_AND_STREET)
              .postcode(POSTCODE)
              .townOrCity(TOWN_OR_CITY)
              .contactDetailsName(NAME)
              .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
              .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
              .build();

  public static final OrderBadgeProcessingFormRequest
      APPLICATION_TO_ORDER_BADGE_PROCESSING_FORM_REQUEST =
          OrderBadgeProcessingFormRequest.builder()
              .applicationChannel(APPLICATION_CHANNEL_SHORTCODE)
              .applicationDateDay(Integer.parseInt(APPLICATION_DATE_DAY))
              .applicationDateMonth(Integer.parseInt(APPLICATION_DATE_MONTH))
              .applicationDateYear(Integer.parseInt(APPLICATION_DATE_YEAR))
              .applicationDateDay(SUBMISSION_DATE.getDayOfMonth())
              .applicationDateMonth(SUBMISSION_DATE.getMonthValue())
              .applicationDateYear(SUBMISSION_DATE.getYear())
              .build();
}
