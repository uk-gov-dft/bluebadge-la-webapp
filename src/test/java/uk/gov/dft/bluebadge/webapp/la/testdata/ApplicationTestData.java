package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Artifacts;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Eligibility;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.HealthcareProfessional;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Medication;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Treatment;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingAid;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingDifficulty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingDifficultyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingLengthOfTimeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.WalkingSpeedCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationSummaryViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;

public class ApplicationTestData {
  // Application Summary fields
  protected static final UUID APPLICATION_ID = UUID.randomUUID();
  protected static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
  protected static final EligibilityCodeField ELIGIBILITY_CODE = EligibilityCodeField.WALKD;
  protected static final String ELIGIBILITY_SHORT_CODE = EligibilityCodeField.WALKD.toString();
  protected static final String ELIGIBILITY_VIEW_MODEL = "Walking ability";
  protected static final String NAME = "name";
  protected static final String NINO = "nino";
  protected static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");
  protected static final OffsetDateTime NOW =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.ofHours(1));
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_1 =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_2 = SUBMISSION_DATE_1.plusDays(1);
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_3 = SUBMISSION_DATE_2.plusDays(2);
  protected static final String WALKING_DIFFICULTY_1_SHORTCODE = "SOMELSE";
  protected static final String WALKING_DIFFICULTY_2_SHORTCODE = "STRUGGLE";

  // Application Summary objects
  protected static final ApplicationSummary APPLICATION_SUMMARY_PERSON_1 =
      new ApplicationSummary()
          .applicationId(APPLICATION_ID.toString())
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY_CODE)
          .name(NAME)
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_1);
  protected static final ApplicationSummary APPLICATION_SUMMARY_2 =
      new ApplicationSummary()
          .applicationId("2")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.ARMS)
          .name("name2")
          .nino("AA0000A2")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_2);
  protected static final ApplicationSummary APPLICATION_SUMMARY_3 =
      new ApplicationSummary()
          .applicationId("3")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.CHILDBULK)
          .name("name3")
          .nino("AA0000A3")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_3);
  protected static final List<ApplicationSummary> APPLICATION_SUMMARIES_ONE_ITEM =
      Lists.newArrayList(APPLICATION_SUMMARY_PERSON_1);
  protected static final List<ApplicationSummary> UNORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(
          APPLICATION_SUMMARY_PERSON_1, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_3);
  protected static final List<ApplicationSummary> ORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(
          APPLICATION_SUMMARY_3, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_PERSON_1);
  protected static final List<ApplicationSummary> APPLICATION_SUMMARIES =
      UNORDERED_APPLICATION_SUMMARIES;

  protected static final ApplicationSummary APPLICATION_SUMMARY_ORGANISATION_1 =
      new ApplicationSummary()
          .applicationId(APPLICATION_ID.toString())
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(null)
          .name(NAME)
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.ORG)
          .submissionDate(SUBMISSION_DATE_1);

  // Application View Model fields
  protected static final String SUBMISSION_DATE_VIEW_MODEL_1 = "20/06/18 12:10";

  // Application View Model Objects
  protected static final ApplicationSummaryViewModel APPLICATION_PERSON_VIEW_MODEL_1 =
      ApplicationSummaryViewModel.builder()
          .applicationId(APPLICATION_ID.toString())
          .name(NAME)
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_MODEL_1)
          .build();
  protected static final List<ApplicationSummaryViewModel> APPLICATION_VIEW_MODELS_ONE_ITEM =
      Lists.newArrayList(APPLICATION_PERSON_VIEW_MODEL_1);

  protected static final ApplicationSummaryViewModel APPLICATION_ORGANISATION_VIEW_MODEL_1 =
      ApplicationSummaryViewModel.builder()
          .applicationId(APPLICATION_ID.toString())
          .name(NAME)
          .nino(NINO)
          .eligibility("Organisation")
          .submittedDate(SUBMISSION_DATE_VIEW_MODEL_1)
          .build();

  // Application fields
  protected static final String BUILDING_AND_STREET = "Building and street";
  protected static final String OPTIONAL_ADDRESS_FIELD = "Optional address field";
  protected static final String TOWN_OR_CITY = "Town or city";
  protected static final String POSTCODE = "TF8 6GF";
  protected static final String CONTACT_DETAILS_NAME = "Contact details name";
  protected static final String CONTACT_DETAILS_CONTACT_NUMBER = "07700900077";
  protected static final String CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER = "07700900099";
  protected static final String CONTACT_DETAILS_EMAIL_ADDRESS = "joe@blogs.com";
  protected static final String GENDER_SHORTCODE = "MALE";
  protected static final LocalDate DOB = LocalDate.of(1980, 3, 15);

  protected static final String EXISTING_BADGE_NUMBER = "KKKKK1";
  protected static final String LOCAL_AUTHORITY_CODE = "ABERD";
  protected static final String BADGE_PHOTO = "badgePhoto";
  protected static final String BADGE_PHOTO_URL =
      "https://winklevosscapital.com/wp-content/uploads/2014/10/2014-09-16-Anoynmous-The-Rise-of-Personal-Networks.jpg";
  protected static final String PROOF_OF_ADDRESS = "proofOfAddress";
  protected static final String PROOF_OF_ADDRESS_URL = "http://localhost:8080/proofOfAddressUrl";
  protected static final String PROOF_OF_IDENTITY = "proofOfIdentity";
  protected static final String PROOF_OF_IDENTITY_URL = "http://localhost:8080/proofOfIdentityUrl";

  protected static final String WALKING_AID_DESCRIPTION_1 = "Needs travelator";
  protected static final String WALKING_AID_DESCRIPTION_2 = "Needs crutches";

  protected static final String DESCRIPTION_OF_CONDITIONS = "Several illneses and disseases";

  protected static final WalkingSpeedCodeField WALKING_SPEED_SHORT_CODE =
      WalkingSpeedCodeField.SLOW;

  // Application
  protected static final Contact CONTACT_PERSON =
      new Contact()
          .buildingStreet(BUILDING_AND_STREET)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .fullName(CONTACT_DETAILS_NAME)
          .line2(OPTIONAL_ADDRESS_FIELD)
          .postCode(POSTCODE)
          .townCity(TOWN_OR_CITY)
          .primaryPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER);

  protected static final Person PERSON =
      new Person().genderCode(GENDER_SHORTCODE).badgeHolderName(NAME).dob(DOB).nino(NINO);

  protected static final Party PARTY_PERSON =
      new Party().contact(CONTACT_PERSON).person(PERSON).typeCode("PERSON").organisation(null);

  protected static final Treatment TREATMENT_1 =
      new Treatment().description("Needs treatment 1").time("20/4/2018");
  protected static final Treatment TREATMENT_2 =
      new Treatment().description("Needs treatment 2").time("18/3/2017");
  protected static final List<Treatment> TREATMENTS = Lists.newArrayList(TREATMENT_1, TREATMENT_2);
  protected static final Medication MEDICATION_1 =
      new Medication()
          .name("medication name 1")
          .frequency("daily")
          .quantity("1 pill")
          .isPrescribed(true);
  protected static final Medication MEDICATION_2 =
      new Medication()
          .name("medication name 2")
          .frequency("monthly")
          .quantity("2 pill")
          .isPrescribed(false);
  protected static final List<Medication> MEDICATIONS =
      Lists.newArrayList(MEDICATION_1, MEDICATION_2);

  protected static final Artifacts ARTIFACTS =
      new Artifacts()
          .badgePhoto(BADGE_PHOTO)
          .badgePhotoUrl(BADGE_PHOTO_URL)
          .proofOfAddress(PROOF_OF_ADDRESS)
          .proofOfAddressUrl(PROOF_OF_ADDRESS_URL)
          .proofOfIdentity(PROOF_OF_IDENTITY)
          .proofOfIdentityUrl(PROOF_OF_IDENTITY_URL);

  protected static final WalkingAid WALKING_AID_1 =
      new WalkingAid().description(WALKING_AID_DESCRIPTION_1);
  protected static final WalkingAid WALKING_AID_2 =
      new WalkingAid().description(WALKING_AID_DESCRIPTION_2);
  protected static final WalkingDifficulty WALKING_DIFFICULTY =
      new WalkingDifficulty()
          .typeCodes(
              Lists.newArrayList(
                  WalkingDifficultyTypeCodeField.fromValue(WALKING_DIFFICULTY_1_SHORTCODE),
                  WalkingDifficultyTypeCodeField.fromValue(WALKING_DIFFICULTY_2_SHORTCODE)))
          .walkingAids(Lists.newArrayList(WALKING_AID_1, WALKING_AID_2))
          .walkingSpeedCode(WALKING_SPEED_SHORT_CODE)
          .walkingLengthOfTimeCode(WalkingLengthOfTimeCodeField.FEWMIN)
          .treatments(TREATMENTS)
          .medications(MEDICATIONS);

  protected static final HealthcareProfessional HEALTHCARE_PROFESSIONAL_1 =
      new HealthcareProfessional().name("Doctor 1").location("London");
  protected static final HealthcareProfessional HEALTHCARE_PROFESSIONAL_2 =
      new HealthcareProfessional().name("Doctor 2").location("Manchester");
  protected static final HealthcareProfessional HEALTHCARE_PROFESSIONAL_3 =
      new HealthcareProfessional().name("Doctor 3").location("Edinburgh");
  protected static final List<HealthcareProfessional> HEALTHCARE_PROFESSIONALS =
      Lists.newArrayList(
          HEALTHCARE_PROFESSIONAL_1, HEALTHCARE_PROFESSIONAL_2, HEALTHCARE_PROFESSIONAL_3);

  protected static final Eligibility ELIGIBILITY_WALKING_DIFFICULTY =
      new Eligibility()
          .typeCode(EligibilityCodeField.WALKD)
          .walkingDifficulty(WALKING_DIFFICULTY)
          .healthcareProfessionals(HEALTHCARE_PROFESSIONALS)
          .descriptionOfConditions(DESCRIPTION_OF_CONDITIONS);

  protected static final Application APPLICATION_FOR_PERSON_WALKING_DIFFICULTIES =
      new Application()
          .applicationId(APPLICATION_ID.toString())
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .party(PARTY_PERSON)
          .artifacts(ARTIFACTS)
          .eligibility(ELIGIBILITY_WALKING_DIFFICULTY)
          .existingBadgeNumber(EXISTING_BADGE_NUMBER)
          .localAuthorityCode(LOCAL_AUTHORITY_CODE)
          .paymentTaken(true);

  // Application View Model - fields
  protected static final String GENDER_VIEW_MODEL = "Male";
  protected static final String DOB_VIEW_MODEL = "15/03/80";
  protected static final String ADDRESS_VIEW_MODEL =
      "Building and street, Optional address field, Town or city, TF8 6GF";
  protected static final String WALKING_DIFFICULTY_1 = "Something else";
  protected static final String WALKING_DIFFICULTY_2 = "Struggle planning or following a journey";
  protected static final List<String> WALKING_DIFFICULTIES =
      Lists.newArrayList(WALKING_DIFFICULTY_1, WALKING_DIFFICULTY_2);
  protected static final List<String> MOBILITY_ADIS_VIEW_MODEL =
      Lists.newArrayList(WALKING_AID_DESCRIPTION_1, WALKING_AID_DESCRIPTION_2);
  protected static final String WALKING_SPEED_VIEW_MODEL = "Slower";

  // Application View Model - objects
  protected static final ApplicationViewModel
      APPLICATION_VIEW_MODEL_FOR_PERSON_WALKING_DIFFICULTIES =
          ApplicationViewModel.builder()
              .applicationId(APPLICATION_ID.toString())
              .fullName(NAME)
              .gender(GENDER_VIEW_MODEL)
              .nino(NINO)
              .dob(DOB_VIEW_MODEL)
              .address(ADDRESS_VIEW_MODEL)
              .contactDetailsName(CONTACT_DETAILS_NAME)
              .contactDetailsPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER)
              .contactDetailsEmail(CONTACT_DETAILS_EMAIL_ADDRESS)
              .proofOfIdentityUrl(PROOF_OF_IDENTITY_URL)
              .proofOfAddressUrl(PROOF_OF_ADDRESS_URL)
              .photoUrl(BADGE_PHOTO_URL)
              .reasonForApplying(ELIGIBILITY_VIEW_MODEL)
              .walkingDifficulties(WALKING_DIFFICULTIES)
              .mobilityAids(MOBILITY_ADIS_VIEW_MODEL)
              .walkingSpeed(WALKING_SPEED_VIEW_MODEL)
              .healthCondition(DESCRIPTION_OF_CONDITIONS)
              .build();
}
