package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationSummaryToApplicationViewModelTest extends ApplicationTestData {

  @Mock ReferenceDataService referenceDataServiceMock;

  ApplicationSummaryToApplicationViewModel converter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    converter = new ApplicationSummaryToApplicationViewModel(referenceDataServiceMock);
    when(referenceDataServiceMock.retrieveEligibilityDisplayValue(ELIGIBILITY_SHORT_CODE))
        .thenReturn(ELIGIBILITY_VIEW_MODEL);
  }

  @Test
  public void convert_shouldWork_whenPerson() {
    ApplicationViewModel viewModel = converter.convert(APPLICATION_SUMMARY_PERSON_1);
    assertThat(viewModel).isEqualTo(APPLICATION_PERSON_VIEW_MODEL_1);
  }

  @Test
  public void convert_shouldWork_whenOrganisation() {
    ApplicationViewModel viewModel = converter.convert(APPLICATION_SUMMARY_ORGANISATION_1);
    assertThat(viewModel).isEqualTo(APPLICATION_ORGANISATION_VIEW_MODEL_1);
  }
}
