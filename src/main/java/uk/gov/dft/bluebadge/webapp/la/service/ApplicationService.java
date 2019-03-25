package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.applications.ApplicationsApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTransfer;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.FindApplicationsParameters;
import uk.gov.dft.bluebadge.webapp.la.comparator.ApplicationSummaryComparatorBySubmittedDateDescendingOrder;

@Service
@Slf4j
public class ApplicationService {

  private ApplicationsApiClient applicationsApiClient;

  @Autowired
  public ApplicationService(ApplicationsApiClient applicationsApiClient) {
    this.applicationsApiClient = applicationsApiClient;
  }

  public ApplicationSummaryResponse find(FindApplicationsParameters params) {

    Optional<String> name = params.getName();
    Optional<String> postcode = params.getPostcode();
    Optional<LocalDateTime> from = params.getFrom();
    Optional<LocalDateTime> to = params.getTo();
    Optional<ApplicationTypeCodeField> applicationTypeCode = params.getApplicationTypeCode();
    PagingInfo pageInfo = params.getPageInfo();

    log.debug(
        "find applications with name=[{}], postcode=[{}], from=[{}], to=[{}], applicationTypeCode=[{}]",
        name,
        postcode,
        from,
        to,
        applicationTypeCode);
    Assert.isTrue(
        name.isPresent()
            || postcode.isPresent()
            || from.isPresent()
            || to.isPresent()
            || applicationTypeCode.isPresent(),
        "Either name or postcode or from or to or applicationTypeCode should be non empty");

    ApplicationSummaryResponse response =
        this.applicationsApiClient.find(name, postcode, from, to, applicationTypeCode, pageInfo);
    if (!response.getData().isEmpty()) {
      response.getData().sort(new ApplicationSummaryComparatorBySubmittedDateDescendingOrder());
    }
    return response;
  }

  public Application retrieve(String applicationId) {
    return applicationsApiClient.retrieve(applicationId);
  }

  public ApplicationSummaryResponse findNewApplicationsByName(String name, PagingInfo pageInfo) {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.of(name))
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(pageInfo)
            .build();
    return find(searchParams);
  }

  public ApplicationSummaryResponse findNewApplicationsByPostCode(
      String postcode, PagingInfo pageInfo) {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.empty())
            .postcode(Optional.of(postcode))
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(pageInfo)
            .build();

    return find(searchParams);
  }

  public ApplicationSummaryResponse findAllNew(PagingInfo pageInfo) {
    FindApplicationsParameters searchParams =
        FindApplicationsParameters.builder()
            .name(Optional.empty())
            .postcode(Optional.empty())
            .from(Optional.empty())
            .to(Optional.empty())
            .applicationTypeCode(Optional.of(ApplicationTypeCodeField.NEW))
            .pageInfo(pageInfo)
            .build();

    return find(searchParams);
  }

  public void update(ApplicationUpdate applicationUpdateRequest) {
    Assert.notNull(applicationUpdateRequest, "applicationUpdateRequest must be not null");
    Assert.notNull(applicationUpdateRequest.getApplicationId(), "applicationId must be not null");
    Assert.notNull(
        applicationUpdateRequest.getApplicationStatus(), "applicationStatus must be not null");
    applicationsApiClient.update(applicationUpdateRequest);
  }

  public void transfer(String applicationId, ApplicationTransfer applicationTransferRequest) {
    Assert.notNull(applicationId, "applicationId must be not null");
    Assert.notNull(applicationTransferRequest, "applicationUpdateRequest must be not null");
    Assert.notNull(
        applicationTransferRequest.getTransferToLaShortCode(),
        "transferToLaShortCode must be not null");
    applicationsApiClient.transfer(applicationId, applicationTransferRequest);
  }

  public void delete(String applicationId) {
    applicationsApiClient.delete(applicationId);
  }
}
