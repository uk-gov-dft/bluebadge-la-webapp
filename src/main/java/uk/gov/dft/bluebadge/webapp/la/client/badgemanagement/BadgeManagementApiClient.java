package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.common.ServiceConfiguration;

@Slf4j
@Service
public class BadgeManagementApiClient extends BaseApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";

  private static final String ORDER_BADGES_ENDPOINT = BADGES_BASE_ENDPOINT;
  private static final String RETRIEVE_BADGE_ENDPOINT = BADGES_BASE_ENDPOINT;

  private RestTemplateFactory restTemplateFactory;
  private ServiceConfiguration serviceConfiguration;

  @Autowired
  public BadgeManagementApiClient(
      RestTemplateFactory restTemplateFactory, ServiceConfiguration badgeManagementApiConfig) {
    this.restTemplateFactory = restTemplateFactory;
    this.serviceConfiguration = badgeManagementApiConfig;
  }

  public List<String> orderBlueBadges(BadgeOrderRequest badgeOrder) {
    Assert.notNull(badgeOrder, "orderBlueBadges - badgeOrder must be set");

    HttpEntity<BadgeOrderRequest> request = new HttpEntity<>(badgeOrder);

    UriComponentsBuilder builder = getUriComponentsBuilder(ORDER_BADGES_ENDPOINT);

    try {
      return Objects.requireNonNull(
              restTemplateFactory
                  .getInstance()
                  .postForObject(builder.toUriString(), request, BadgeNumbersResponse.class))
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return Lists.newArrayList();
  }

  /**
   * Retrieves a specific badge.
   *
   * @param badgeNumber cannot be null.
   * @return
   */
  public Badge retrieveBadge(String badgeNumber) {
    log.debug("retrieveBadge with badgeNumber={}", badgeNumber);

    Assert.notNull(badgeNumber, "badgeNumber supplied must be not null");

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity entity = new HttpEntity(null, headers);

    UriComponentsBuilder builder = getUriComponentsBuilder(RETRIEVE_BADGE_ENDPOINT);
    builder.pathSegment(badgeNumber);
    try {
      ResponseEntity<BadgeResponse> response =
          restTemplateFactory
              .getInstance()
              .exchange(builder.toUriString(), HttpMethod.GET, entity, BadgeResponse.class);
      return response.getBody().getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  /*
  Creates a builder for a given apiEndpoint using standard configuration
   */
  private UriComponentsBuilder getUriComponentsBuilder(String apiEndpoint) {

    return UriComponentsBuilder.newInstance()
        .host(serviceConfiguration.getHost())
        .scheme(serviceConfiguration.getScheme())
        .port(serviceConfiguration.getPort())
        .path(serviceConfiguration.getContextpath())
        .pathSegment(apiEndpoint);
  }
}
