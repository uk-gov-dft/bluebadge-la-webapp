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
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationStatusField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTransfer;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.FindApplicationsParameters;
import uk.gov.dft.bluebadge.webapp.la.testdata.ApplicationTestData;

public class ApplicationServiceTest extends ApplicationTestData {

  private static final String APPLICATION_ID = UUID.randomUUID().toString();

  private static final ApplicationUpdate APPLICATION_UPDATE =
      ApplicationUpdate.builder()
          .applicationId(UUID.fromString(APPLICATION_ID))
          .applicationStatus(ApplicationStatusField.INPROGRESS)
          .build();

  private static final ApplicationTransfer APPLICATION_TRANSFER =
      ApplicationTransfer.builder().transferToLaShortCode("KENTCC").build();

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
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.empty())
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.empty())
            .pageInfo(validPaging)
            .build();

    applicationService.find(searchParams);
  }

  @Test
  public void find_shouldReturnEmptyList_WhenThereAreNoResults() {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.empty())
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(validPaging)
            .build();

    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(noNewApplications);
    ApplicationSummaryResponse result = applicationService.find(searchParams);
    assertThat(result.getData()).isEqualTo(Lists.emptyList());
  }

  @Test
  public void find_shouldReturnResultsOrderedBySubmittedDataDescendingOrder_WhenOneParamIsSet() {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.empty())
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(validPaging)
            .build();

    when(applicationsApiClientMock.find(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(unorderedApplications);
    ApplicationSummaryResponse result = applicationService.find(searchParams);
    assertThat(result.getData()).isEqualTo(orderdApplicationsForSearchByName);
  }

  @Test
  public void
      find_shouldReturnResultsPartiallyMatchedToNameOrderedBySubmittedDataDescendingOrder_WhenNameIsSet() {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.of(NAME_SEARCH_BY))
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(validPaging)
            .build();
    when(applicationsApiClientMock.find(
            Optional.of(NAME_SEARCH_BY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(ApplicationTypeCodeField.NEW),
            validPaging))
        .thenReturn(unorderedApplications);
    ApplicationSummaryResponse result = applicationService.find(searchParams);
    assertThat(result.getData()).isEqualTo(orderdApplicationsForSearchByName);
  }

  @Test
  public void delete_shouldWork() {
    applicationService.delete(APPLICATION_ID);
  }

  @Test
  public void update_shouldWork() {
    applicationService.update(APPLICATION_UPDATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_shouldThrowIllegalArgumentException_whenApplicationUpdateRequestIsNull() {
    applicationService.update(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_shouldThrowIllegalArgumentException_whenApplicationIdIsNull() {
    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(null)
            .applicationStatus(ApplicationStatusField.INPROGRESS)
            .build();

    applicationService.update(applicationUpdate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_shouldThrowIllegalArgumentException_whenApplicationStatusIsNull() {
    ApplicationUpdate applicationUpdate =
        ApplicationUpdate.builder()
            .applicationId(UUID.fromString(APPLICATION_ID))
            .applicationStatus(null)
            .build();

    applicationService.update(applicationUpdate);
  }

  @Test
  public void transfer_shouldWork() {
    applicationService.transfer(APPLICATION_ID, APPLICATION_TRANSFER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void transfer_shouldThrowIllegalArgumentException_whenApplicationIdIsNull() {
    applicationService.transfer(null, APPLICATION_TRANSFER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void transfer_shouldThrowIllegalArgumentException_whenApplicationTransferRequestIsNull() {
    applicationService.transfer(APPLICATION_ID, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void transfer_shouldThrowIllegalArgumentException_whenTransferToLaShortCodeIsNull() {
    ApplicationTransfer applicationTransfer =
        ApplicationTransfer.builder().transferToLaShortCode(null).build();

    applicationService.transfer(APPLICATION_ID, applicationTransfer);
  }
}
