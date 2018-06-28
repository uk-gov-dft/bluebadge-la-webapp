package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.RestTemplateFactory;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;

@Service
public class BadgeManagementApiClient {

  private static final Logger log = LoggerFactory.getLogger(BadgeManagementApiClient.class);

  private static final String BADGES_API_ENDPOINT = "badges";
  private static final String QUERY_PARAM_NAME = "name";
  private static final String QUERY_PARAM_BADGE_NUMBER = "badgeNumber";
  private static final String QUERY_PARAM_NI = "ni";

  private RestTemplateFactory restTemplateFactory;
  private BadgeManagementServiceConfiguration serviceConfiguration;

  @Autowired
  public BadgeManagementApiClient(
      RestTemplateFactory restTemplateFactory,
      BadgeManagementServiceConfiguration serviceConfiguration) {
    this.restTemplateFactory = restTemplateFactory;
    this.serviceConfiguration = serviceConfiguration;
  }

  /**
   * Call the Badge Management Service to find badges. You can use any combination of parameters..
   *
   * @param name can be null
   * @param niNumber can be null
   * @param badgeNumber can be null
   * @return
   */
  public List<BadgeSummary> findBadges(String name, String niNumber, String badgeNumber) {
    log.debug("findBages with name={}, niNumber={}, badgeNumber={}", name, niNumber, badgeNumber);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity entity = new HttpEntity("parameters", headers);

    UriComponentsBuilder builder = getUriComponentsBuilder(BADGES_API_ENDPOINT);
    addParameterMethod(name, builder, QUERY_PARAM_NAME);
    addParameterMethod(badgeNumber, builder, QUERY_PARAM_BADGE_NUMBER);
    addParameterMethod(niNumber, builder, QUERY_PARAM_NI);

    ResponseEntity<BadgesResponse> response =
        restTemplateFactory
            .getInstance()
            .exchange(builder.toUriString(), HttpMethod.GET, entity, BadgesResponse.class);
    return response.getBody().getData();
  }

  /**
   * Get a specific badge.
   *
   * @param badgeNumber cannot be null.
   * @return
   */
  public Badge retrieveBadge(String badgeNumber) {
    log.debug("retrieveBadge with badgeNumber={}", badgeNumber);

    Assert.notNull(badgeNumber, "badgeNumber supplied must be not null");

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity entity = new HttpEntity("parameters", headers);

    UriComponentsBuilder builder = getUriComponentsBuilder(BADGES_API_ENDPOINT);
    builder.pathSegment(badgeNumber); // Appends badgeNumber to the path

    ResponseEntity<BadgeResponse> response =
        restTemplateFactory
            .getInstance()
            .exchange(builder.toUriString(), HttpMethod.GET, entity, BadgeResponse.class);
    return response.getBody().getData();
  }

  /*
  Creates a builder for a given apiEndpoint using standard configuration
   */
  private UriComponentsBuilder getUriComponentsBuilder(String apiEndpoint) {

    return UriComponentsBuilder.newInstance()
        .host(serviceConfiguration.getHost())
        .scheme(serviceConfiguration.getScheme())
        .port(serviceConfiguration.getPort())
        .path(serviceConfiguration.getContextPath())
        .pathSegment(apiEndpoint);
  }

  /*
  Convenience method to add non-null parametes to an endpoint
   */
  private void addParameterMethod(
      String name, UriComponentsBuilder builder, String queryParamName) {
    if (StringUtils.isNotBlank(name)) {
      builder.queryParam(queryParamName, name);
    }
  }
}
