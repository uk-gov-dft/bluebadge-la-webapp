package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;

@Slf4j
@Service
public class BadgeManagementApiClient extends BaseApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";

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

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT, badgeNumber);
    try {
      log.info("retrieveBadge {}", builder.toUriString());
      BadgeResponse response =
          restTemplate.getForObject(builder.toUriString(), BadgeResponse.class);
      return response.getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
    return null;
  }

  public List<BadgeSummary> findBadgeByPostCode(String postcode) {
    Assert.notNull(postcode, "Post code supplied must be not null");

    return findBadgeBy(FindBadgeAttribute.POSTCODE, postcode);
  }

  private List<BadgeSummary> findBadgeBy(FindBadgeAttribute attribute, String value) {
    log.debug("retrieveBadge with " + attribute, value);
    Assert.notNull(attribute, "Attribute supplied must be not null");
    Assert.notNull(value, "Value supplied must be not null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT);
    builder.queryParam(attribute.getDescription(), value);

    try {
      BadgesResponse response =
          restTemplate.getForObject(builder.toUriString(), BadgesResponse.class);
      return response.getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }

    return Lists.newArrayList();
  }
}
