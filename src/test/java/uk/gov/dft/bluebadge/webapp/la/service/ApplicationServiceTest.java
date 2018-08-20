package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.applications.ApplicationsApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationServiceTest extends ApplicationTestData {

  @Mock private ApplicationsApiClient applicationsApiClientMock;

  private ApplicationService applicationService;

  @Before
  public void setup() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    applicationService = new ApplicationService(applicationsApiClientMock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void find_shouldThrowIllegalArgumentException_WhenNoParamIsSet() {
    applicationService.find(
        Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
  }

  @Test
  public void find_shouldReturnEmptyList_WhenThereAreNoResults() {
    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW)))
        .thenReturn(Lists.emptyList());
    List<ApplicationSummary> result =
        applicationService.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW));
    assertThat(result).isEqualTo(Lists.emptyList());
  }

  @Test
  public void find_shouldReturnResultsOrderedBySubmittedDataDescendingOrder_WhenOneParamIsSet() {
    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW)))
        .thenReturn(UNORDERED_APPLICATION_SUMMARIES);
    List<ApplicationSummary> result =
        applicationService.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW));
    assertThat(result).isEqualTo(ORDERED_APPLICATION_SUMMARIES);
  }
}
