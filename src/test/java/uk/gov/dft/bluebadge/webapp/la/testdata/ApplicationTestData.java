package uk.gov.dft.bluebadge.webapp.la.testdata;

import com.google.common.collect.Lists;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;

public class ApplicationTestData {
  protected static final ApplicationSummary APPLICATION_SUMMARY_1 =
      new ApplicationSummary()
          .applicationId("1")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.BLIND)
          .name("name1")
          .nino("AA0000A1")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(OffsetDateTime.now(Clock.systemUTC()));
  protected static final ApplicationSummary APPLICATION_SUMMARY_2 =
      new ApplicationSummary()
          .applicationId("2")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.ARMS)
          .name("name2")
          .nino("AA0000A2")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(OffsetDateTime.now(Clock.systemUTC()).plusNanos(1));
  protected static final ApplicationSummary APPLICATION_SUMMARY_3 =
      new ApplicationSummary()
          .applicationId("3")
          .applicationTypeCode(ApplicationTypeCodeField.NEW)
          .eligibilityCode(EligibilityCodeField.CHILDBULK)
          .name("name3")
          .nino("AA0000A3")
          .partyTypeCode(PartyTypeCodeField.PERSON)
          .submissionDate(OffsetDateTime.now(Clock.systemUTC()).plusNanos(2));
  protected static final List<ApplicationSummary> UNORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(APPLICATION_SUMMARY_1, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_3);
  protected static final List<ApplicationSummary> ORDERED_APPLICATION_SUMMARIES =
      Lists.newArrayList(APPLICATION_SUMMARY_3, APPLICATION_SUMMARY_2, APPLICATION_SUMMARY_1);
  protected static final List<ApplicationSummary> APPLICATION_SUMMARIES =
      UNORDERED_APPLICATION_SUMMARIES;
}
