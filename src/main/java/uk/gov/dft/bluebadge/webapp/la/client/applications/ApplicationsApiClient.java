package uk.gov.dft.bluebadge.webapp.la.client.applications;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTransfer;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationUpdate;

@Slf4j
@Service
public class ApplicationsApiClient {

  private static final String BASE_ENDPOINT = "applications";
  private static final String TRANSFER_ENDPOINT = "/applications/{uuid}/transfers";

  private final RestTemplate restTemplate;

  @Autowired
  public ApplicationsApiClient(@Qualifier("applicationsRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ApplicationSummaryResponse find(
      Optional<String> name,
      Optional<String> postcode,
      Optional<LocalDateTime> from,
      Optional<LocalDateTime> to,
      Optional<ApplicationTypeCodeField> applicationTypeCode,
      PagingInfo pageInfo) {
    log.debug(
        "find applications with name=[{}], postcode=[{}], from=[{}], to=[{}], applicationTypeCode=[{}]",
        name,
        postcode,
        from,
        to,
        applicationTypeCode);
    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BASE_ENDPOINT);
    name.ifPresent(value -> builder.queryParam("name", value));
    postcode.ifPresent(value -> builder.queryParam("postcode", value));
    from.ifPresent(value -> builder.queryParam("from", value));
    to.ifPresent(value -> builder.queryParam("to", value));
    applicationTypeCode.ifPresent(value -> builder.queryParam("applicationTypeCode", value));

    builder.queryParam("pageSize", pageInfo.getPageSize());
    builder.queryParam("pageNum", pageInfo.getPageNum());

    return restTemplate.getForObject(
        builder.build().toUriString(), ApplicationSummaryResponse.class);
  }

  public Application retrieve(String applicationId) {
    Assert.notNull(applicationId, "applicationId supplied must be not null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BASE_ENDPOINT, applicationId);

    log.debug("retrieveApplication {}", applicationId);
    ApplicationResponse response =
        restTemplate.getForObject(builder.toUriString(), ApplicationResponse.class);
    return response.getData();
  }

  public void delete(String applicationId) {
    Assert.notNull(applicationId, "applicationId supplied must be not null");

    log.debug("delete {}", applicationId);

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BASE_ENDPOINT, applicationId);
    restTemplate.delete(builder.toUriString());
  }

  public void update(ApplicationUpdate applicationUpdateRequest) {
    UUID applicationId = applicationUpdateRequest.getApplicationId();
    Assert.notNull(applicationUpdateRequest, "applicationUpdateRequest must be not null");
    Assert.notNull(applicationId, "applicationId must be not null");
    Assert.notNull(
        applicationUpdateRequest.getApplicationStatus(), "applicationStatus must be not null");

    log.debug(
        "update application {} to status {}",
        applicationId,
        applicationUpdateRequest.getApplicationStatus().name());

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance()
            .path("/")
            .pathSegment(BASE_ENDPOINT, applicationId.toString());
    restTemplate.put(builder.toUriString(), applicationUpdateRequest);
  }

  public void transfer(String applicationId, ApplicationTransfer applicationTransferRequest) {
    String transferToLaShortCode = applicationTransferRequest.getTransferToLaShortCode();
    Assert.notNull(applicationTransferRequest, "applicationTransferRequest must be not null");
    Assert.notNull(transferToLaShortCode, "transferToLaShortCode must be not null");

    String uri = UriComponentsBuilder.fromUriString(TRANSFER_ENDPOINT).build().toUriString();

    log.debug("transfer application {} to LA {}", transferToLaShortCode);

    restTemplate.postForEntity(
        uri, applicationTransferRequest, CommonResponse.class, applicationId);
  }
}
