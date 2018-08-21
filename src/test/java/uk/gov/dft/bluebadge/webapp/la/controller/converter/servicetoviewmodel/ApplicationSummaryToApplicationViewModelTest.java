package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ApplicationSummaryToApplicationViewModelTest {

  private static final EligibilityCodeField ELIGIBILITY = EligibilityCodeField.DLA;
  private static final String ELIGIBILITY_SHORT_CODE = EligibilityCodeField.DLA.toString();
  private static final String ELIGIBILITY_VIEW_MODEL = "DLA";
  private static final String APPLICATION_ID = "1";
  private static final String NAME = "name";
  private static final String NINO = "nino";
  private static final java.time.OffsetDateTime SUBMISSION_DATE = LocalDateTime.of(2018, 6, 20, 10, 10).atOffset(ZoneOffset.MIN);
  private static final String SUBMISSION_DATE_VIEW_MODEL = "20/06/18 10:10";
  private static final ApplicationSummary APPLICATION_SUMMARY = new ApplicationSummary().applicationId(APPLICATION_ID).eligibilityCode(ELIGIBILITY).nino(NINO).name(NAME).submissionDate(SUBMISSION_DATE);
  private static final ApplicationViewModel APPLICATION_VIEW_MODEL = ApplicationViewModel.builder().name(NAME).nino(NINO).eligibility(ELIGIBILITY_VIEW_MODEL).submittedDate(SUBMISSION_DATE_VIEW_MODEL).build();

  @Mock
  ReferenceDataService referenceDataServiceMock;

  ApplicationSummaryToApplicationViewModel converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    converter = new ApplicationSummaryToApplicationViewModel(referenceDataServiceMock);
    when(referenceDataServiceMock.retrieveEligibilityDisplayValue(ELIGIBILITY_SHORT_CODE)).thenReturn(ELIGIBILITY_VIEW_MODEL);
  }

  @Test
  public void convert_shouldWork() {
    ApplicationViewModel viewModel = converter.convert(APPLICATION_SUMMARY);
    assertThat(viewModel).isEqualTo(APPLICATION_VIEW_MODEL);
  }
}
