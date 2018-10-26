package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  private static final String APPLICATION_ID = UUID.randomUUID().toString();

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

  @Test
  public void
      find_shouldReturnResultsPartiallyMatchedToNameOrderedBySubmittedDataDescendingOrder_WhenNameIsSet() {
    when(applicationsApiClientMock.find(
            Optional.of(NAME_SEARCH_BY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW)))
        .thenReturn(unorderedApplicationsForSearchByName);
    List<ApplicationSummary> result =
        applicationService.find(
            Optional.of(NAME_SEARCH_BY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW));
    assertThat(result).isEqualTo(orderdApplicationsForSearchByName);
  }

  @Test
  public void delete_shouldWork() {
    applicationService.delete(APPLICATION_ID);
  }
}
