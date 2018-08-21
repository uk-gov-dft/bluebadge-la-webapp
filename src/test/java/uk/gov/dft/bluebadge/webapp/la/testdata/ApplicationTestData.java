package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.LocalDateTime;
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
  protected static final java.time.LocalDateTime SUBMISSION_DATE_1 =
      LocalDateTime.of(2018, 6, 20, 10, 10);
  protected static final java.time.LocalDateTime SUBMISSION_DATE_2 = SUBMISSION_DATE_1.plusDays(1);
  protected static final java.time.LocalDateTime SUBMISSION_DATE_3 = SUBMISSION_DATE_2.plusDays(2);

  // Application View Model fields
  protected static final String SUBMISSION_DATE_VIEW_MODEL_1 = "20/06/18 10:10";

  // Application Summary objects
  protected static final ApplicationSummary APPLICATION_SUMMARY_1 =
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
      Lists.newArrayList(APPLICATION_SUMMARY_1);
  protected static final List<ApplicationSummary> UNORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(APPLICATION_SUMMARY_1, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_3);
  protected static final List<ApplicationSummary> ORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(APPLICATION_SUMMARY_3, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_1);
  protected static final List<ApplicationSummary> APPLICATION_SUMMARIES =
      UNORDERED_APPLICATION_SUMMARIES;

  // Application View Model Objects
  protected static final ApplicationViewModel APPLICATION_VIEW_MODEL_1 =
      ApplicationViewModel.builder()
          .name(NAME)
          .nino(NINO)
          .eligibility(ELIGIBILITY_VIEW_MODEL)
          .submittedDate(SUBMISSION_DATE_VIEW_MODEL_1)
          .build();
  protected static final List<ApplicationViewModel> APPLICATION_VIEW_MODELS_ONE_ITEM =
      Lists.newArrayList(APPLICATION_VIEW_MODEL_1);
}
