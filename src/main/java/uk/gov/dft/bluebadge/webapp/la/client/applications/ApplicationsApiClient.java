package uk.gov.dft.bluebadge.webapp.la.client.applications;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;

@Slf4j
@Service
public class ApplicationsApiClient extends BaseApiClient {

  private static final String BASE_ENDPOINT = "applications";

  private final RestTemplate restTemplate;

  @Autowired
  public ApplicationsApiClient(@Qualifier("applicationsRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<ApplicationSummary> find(
      Optional<String> name,
      Optional<String> postcode,
      Optional<LocalDate> from,
      Optional<LocalDate> to,
      Optional<ApplicationTypeCodeField> applicationTypeCode) {
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

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BASE_ENDPOINT);
    name.ifPresent(value -> builder.queryParam("name", value));
    postcode.ifPresent(value -> builder.queryParam("postcode", value));
    from.ifPresent(value -> builder.queryParam("from", value));
    to.ifPresent(value -> builder.queryParam("to", value));
    applicationTypeCode.ifPresent(value -> builder.queryParam("applicationTypeCode", value));

    ApplicationSummaryResponse response = new ApplicationSummaryResponse();
    try {
      response =
          restTemplate.getForObject(
              builder.build().toUriString(), ApplicationSummaryResponse.class);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    /*
    ApplicationSummary as1 =
        new ApplicationSummary()
            .applicationId("1")
            .applicationTypeCode(ApplicationTypeCodeField.NEW)
            .eligibilityCode(EligibilityCodeField.BLIND)
            .name("name1")
            .nino("AA0000A1")
            .partyTypeCode(PartyTypeCodeField.PERSON)
            .submissionDate(OffsetDateTime.now());
    ApplicationSummary as2 =
        new ApplicationSummary()
            .applicationId("2")
            .applicationTypeCode(ApplicationTypeCodeField.NEW)
            .eligibilityCode(EligibilityCodeField.ARMS)
            .name("name2")
            .nino("AA0000A2")
            .partyTypeCode(PartyTypeCodeField.PERSON)
            .submissionDate(OffsetDateTime.now());
    ApplicationSummary as3 =
        new ApplicationSummary()
            .applicationId("3")
            .applicationTypeCode(ApplicationTypeCodeField.NEW)
            .eligibilityCode(EligibilityCodeField.CHILDBULK)
            .name("name3")
            .nino("AA0000A3")
            .partyTypeCode(PartyTypeCodeField.PERSON)
            .submissionDate(OffsetDateTime.now());
    List<ApplicationSummary> data = Lists.newArrayList(as1, as2, as3);
    response.data(data);*/
    return response.getData();
  }
}
