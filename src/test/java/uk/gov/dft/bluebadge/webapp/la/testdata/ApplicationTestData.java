package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;

public class ApplicationTestData {
  // Application Summary fields
  protected static final String APPLICATION_ID = "1";
  protected static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
  protected static final EligibilityCodeField ELIGIBILITY = EligibilityCodeField.DLA;
  protected static final String ELIGIBILITY_SHORT_CODE = EligibilityCodeField.DLA.toString();
  protected static final String ELIGIBILITY_VIEW_MODEL = "DLA";
  protected static final String NAME = "name";
  protected static final String NINO = "nino";
  protected static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");
  protected static final OffsetDateTime NOW =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.ofHours(1));
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_1 =
      OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_2 = SUBMISSION_DATE_1.plusDays(1);
  protected static final java.time.OffsetDateTime SUBMISSION_DATE_3 = SUBMISSION_DATE_2.plusDays(2);

  // Application View Model fields
  protected static final String SUBMISSION_DATE_VIEW_MODEL_1 = "20/06/18 12:10";

  // Application Summary objects
  protected static final ApplicationSummary APPLICATION_SUMMARY_PERSON_1 =
      new ApplicationSummary()
          .applicationId(APPLICATION_ID)
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY)
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
          .applicationId(APPLICATION_ID)
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(null)
          .name(NAME)
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.ORG)
          .submissionDate(SUBMISSION_DATE_1);

  // Application View Model Objects
  protected static final ApplicationViewModel APPLICATION_PERSON_VIEW_MODEL_1 =
      ApplicationViewModel.builder()
          .name(NAME)
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_MODEL_1)
          .build();
  protected static final List<ApplicationViewModel> APPLICATION_VIEW_MODELS_ONE_ITEM =
      Lists.newArrayList(APPLICATION_PERSON_VIEW_MODEL_1);

  protected static final ApplicationViewModel APPLICATION_ORGANISATION_VIEW_MODEL_1 =
      ApplicationViewModel.builder()
          .name(NAME)
          .nino(NINO)
          .eligibility("Organisation")
          .submittedDate(SUBMISSION_DATE_VIEW_MODEL_1)
          .build();

  public static final String NAME_SEARCH_BY = "john";

  public static final ApplicationSummary APPLICATION_JOHN =
      new ApplicationSummary()
          .applicationId("100")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY)
          .name("John Bates")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_1);

  public static final ApplicationSummary APPLICATION_JOHNSON =
      new ApplicationSummary()
          .applicationId("101")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY)
          .name("Tom Johnson")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_1);

  public static final ApplicationSummary APPLICATION_LITTLEJOHN =
      new ApplicationSummary()
          .applicationId("102")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY)
          .name("David Littlejohn")
          .nino(NINO)
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(SUBMISSION_DATE_2);

  public static final ApplicationSummary APPLICATION_OTHER =
      new ApplicationSummary()
          .applicationId("103")
          .applicationTypeCode(APPLICATION_TYPE)
          .eligibilityCode(ELIGIBILITY)
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

  public static final ApplicationViewModel APPLICATION_JOHN_VIEW =
      ApplicationViewModel.builder()
          .name("John Bates")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_1)
          .build();

  public static final ApplicationViewModel APPLICATION_JOHNSON_VIEW =
      ApplicationViewModel.builder()
          .name("Tom Johnson")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_1)
          .build();

  public static final ApplicationViewModel APPLICATION_LITTLEJOHN_VIEW =
      ApplicationViewModel.builder()
          .name("David Littlejohn")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_2)
          .build();

  public static final ApplicationViewModel APPLICATION_OTHER_VIEW =
      ApplicationViewModel.builder()
          .name("Maria Davis")
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_2)
          .build();

  public static final List<ApplicationViewModel> applicationsForSearchByNameView =
      Arrays.asList(APPLICATION_JOHN_VIEW, APPLICATION_JOHNSON_VIEW, APPLICATION_LITTLEJOHN_VIEW);
}
