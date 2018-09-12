package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationSummaryViewModel;

public class ApplicationTestData {
  // Application Summary fields
  private static final UUID APPLICATION_ID = UUID.randomUUID();
  private static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
  private static final EligibilityCodeField ELIGIBILITY_CODE = EligibilityCodeField.WALKD;
  protected static final String ELIGIBILITY_SHORT_CODE = EligibilityCodeField.WALKD.toString();
  protected static final String ELIGIBILITY_VIEW_MODEL = "Walking ability";
  protected static final String NAME = "name";
  private static final String NINO = "nino";
  protected static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");
  private static final java.time.OffsetDateTime SUBMISSION_DATE_1 =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
  private static final java.time.OffsetDateTime SUBMISSION_DATE_2 = SUBMISSION_DATE_1.plusDays(1);
  private static final java.time.OffsetDateTime SUBMISSION_DATE_3 = SUBMISSION_DATE_2.plusDays(2);
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
  private static final ApplicationSummary APPLICATION_SUMMARY_2 =
      new ApplicationSummary()
          .applicationId("2")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.ARMS)
          .name("name2")
          .nino("AA0000A2")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_2);
  private static final ApplicationSummary APPLICATION_SUMMARY_3 =
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
  private static final String SUBMISSION_DATE_VIEW_MODEL_1 = "20/06/18 12:10";

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
  protected static final String POSTCODE = "TF8 6GF";
  public static final String NAME_SEARCH_BY = "john";

  public static final ApplicationSummary APPLICATION_JOHN =
      new ApplicationSummary()
          .applicationId("100")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY_CODE)
          .name("John Bates")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_1);

  public static final ApplicationSummary APPLICATION_JOHNSON =
      new ApplicationSummary()
          .applicationId("101")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY_CODE)
          .name("Tom Johnson")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_1);

  public static final ApplicationSummary APPLICATION_LITTLEJOHN =
      new ApplicationSummary()
          .applicationId("102")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY_CODE)
          .name("David Littlejohn")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_2);

  public static final ApplicationSummary APPLICATION_OTHER =
      new ApplicationSummary()
          .applicationId("103")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY_CODE)
          .name("Maria Davis")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_2);

  public static final List<ApplicationSummary> applicationsForSearchByName =
      Arrays.asList(APPLICATION_JOHN, APPLICATION_JOHNSON, APPLICATION_LITTLEJOHN);

  public static final List<ApplicationSummary> unorderedApplicationsForSearchByName =
      Arrays.asList(APPLICATION_JOHN, APPLICATION_LITTLEJOHN, APPLICATION_JOHNSON);

  public static final List<ApplicationSummary> orderdApplicationsForSearchByName =
      Arrays.asList(APPLICATION_LITTLEJOHN, APPLICATION_JOHN, APPLICATION_JOHNSON);

  protected static final String SUBMISSION_DATE_VIEW_1 = "20/06/18 10:10";
  protected static final String SUBMISSION_DATE_VIEW_2 = "21/06/18 10:10";

  public static final ApplicationSummaryViewModel APPLICATION_JOHN_VIEW =
		  ApplicationSummaryViewModel.builder()
          .name("John Bates")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_1)
          .build();

  public static final ApplicationSummaryViewModel APPLICATION_JOHNSON_VIEW =
		  ApplicationSummaryViewModel.builder()
          .name("Tom Johnson")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_1)
          .build();

  public static final ApplicationSummaryViewModel APPLICATION_LITTLEJOHN_VIEW =
		  ApplicationSummaryViewModel.builder()
          .name("David Littlejohn")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_2)
          .build();

  public static final ApplicationSummaryViewModel APPLICATION_OTHER_VIEW =
		  ApplicationSummaryViewModel.builder()
          .name("Maria Davis")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_2)
          .build();

  public static final List<ApplicationSummaryViewModel> applicationsForSearchByNameView =
      Arrays.asList(APPLICATION_JOHN_VIEW, APPLICATION_JOHNSON_VIEW, APPLICATION_LITTLEJOHN_VIEW);
}
