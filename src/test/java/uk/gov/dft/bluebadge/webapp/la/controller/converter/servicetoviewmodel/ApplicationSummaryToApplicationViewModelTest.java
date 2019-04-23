package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationSummaryViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.DateTimeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationSummaryToApplicationViewModelTest extends ApplicationTestData {

  @Mock ReferenceDataService referenceDataServiceMock;
  @Mock DateTimeService dateTimeServiceMock;

  ApplicationSummaryToApplicationViewModel converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    converter =
        new ApplicationSummaryToApplicationViewModel(referenceDataServiceMock, dateTimeServiceMock);
    when(referenceDataServiceMock.retrieveApplicationEligibilityDisplayValue(
            ELIGIBILITY_SHORT_CODE))
        .thenReturn(ELIGIBILITY_VIEW_MODEL);
    when(dateTimeServiceMock.clientZoneId()).thenReturn(TIME_ZONE);
    when(referenceDataServiceMock.retrieveAppEnumDisplayValueByString("APPTYPE", "NEW"))
        .thenReturn("New");
  }

  @Test
  public void convert_shouldWork_whenPerson() {
    ApplicationSummaryViewModel viewModel = converter.convert(APPLICATION_SUMMARY_PERSON_1);
    assertThat(viewModel).isEqualTo(APPLICATION_PERSON_VIEW_MODEL_1);
  }

  @Test
  public void convert_shouldWork_whenOrganisation() {
    ApplicationSummaryViewModel viewModel = converter.convert(APPLICATION_SUMMARY_ORGANISATION_1);
    assertThat(viewModel).isEqualTo(APPLICATION_ORGANISATION_VIEW_MODEL_1);
  }
}
