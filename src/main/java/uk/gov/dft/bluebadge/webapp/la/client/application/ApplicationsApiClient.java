package uk.gov.dft.bluebadge.webapp.la.client.application;

import com.google.common.collect.Lists;
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
import uk.gov.dft.bluebadge.webapp.la.client.application.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.application.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;

@Slf4j
@Service
public class ApplicationsApiClient extends BaseApiClient {

  private static final String BASE_ENDPOINT = "applications";

  private final RestTemplate restTemplate;

  public enum FindBadgeAttribute {
    POSTCODE("postCode"),
    NAME("name");

    private String description;

    FindBadgeAttribute(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

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
    // TODO: Verify if my assumption is true
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

    List<ApplicationSummary> results = Lists.newArrayList();

    //    Assert.notNull(attribute, "Attribute supplied must not be null");
    //    Assert.notNull(value, "Value supplied must be not null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BASE_ENDPOINT);
    name.ifPresent(
        value -> {
          builder.queryParam("name", value);
        });
    postcode.ifPresent(
        value -> {
          builder.queryParam("postcode", value);
        });
    from.ifPresent(
        value -> {
          builder.queryParam("from", value);
        });
    to.ifPresent(
        value -> {
          builder.queryParam("to", value);
        });
    applicationTypeCode.ifPresent(
        value -> {
          builder.queryParam("applicationTypeCode", value);
        });

    try {
      //restTemplate.getForObject(builder.build().toUriString(), BadgesResponse.class);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }

    List<ApplicationSummary> data = Lists.newArrayList();

    return data;
  }
}
