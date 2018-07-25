package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;

@Slf4j
@Service
public class BadgeManagementApiClient extends BaseApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";

  private final RestTemplate restTemplate;

  @Autowired
  public BadgeManagementApiClient(
      @Qualifier("badgeManagementRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<String> orderBlueBadges(BadgeOrderRequest badgeOrder) {
    Assert.notNull(badgeOrder, "orderBlueBadges - badgeOrder must be set");

    HttpEntity<BadgeOrderRequest> request = new HttpEntity<>(badgeOrder);

    try {
      return Objects.requireNonNull(
              restTemplate.postForObject(BADGES_BASE_ENDPOINT, request, BadgeNumbersResponse.class))
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

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT, badgeNumber);
    try {
      log.info("retrieveBadge {}", builder.toUriString());
      ResponseEntity<BadgeResponse> response =
          restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BadgeResponse.class);
      return response.getBody().getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }
}
