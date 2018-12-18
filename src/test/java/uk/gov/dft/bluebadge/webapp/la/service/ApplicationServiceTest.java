package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.applications.ApplicationsApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
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
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        validPaging);
  }

  @Test
  public void find_shouldReturnEmptyList_WhenThereAreNoResults() {
    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(noNewApplications);
    ApplicationSummaryResponse result =
        applicationService.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging);
    assertThat(result.getData()).isEqualTo(Lists.emptyList());
  }

  @Test
  public void find_shouldReturnResultsOrderedBySubmittedDataDescendingOrder_WhenOneParamIsSet() {
    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(unorderedApplications);
    ApplicationSummaryResponse result =
        applicationService.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging);
    assertThat(result.getData()).isEqualTo(orderdApplicationsForSearchByName);
  }

  @Test
  public void
      find_shouldReturnResultsPartiallyMatchedToNameOrderedBySubmittedDataDescendingOrder_WhenNameIsSet() {
    when(applicationsApiClientMock.find(
            Optional.of(NAME_SEARCH_BY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(unorderedApplications);
    ApplicationSummaryResponse result =
        applicationService.find(
            Optional.of(NAME_SEARCH_BY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging);
    assertThat(result.getData()).isEqualTo(orderdApplicationsForSearchByName);
  }

  @Test
  public void delete_shouldWork() {
    applicationService.delete(APPLICATION_ID);
  }
}
