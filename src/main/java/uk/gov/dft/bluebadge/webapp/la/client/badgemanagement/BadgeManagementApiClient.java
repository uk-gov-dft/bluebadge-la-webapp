package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeCancelRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumberResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.BaseApiClient;

@Slf4j
@Service
public class BadgeManagementApiClient extends BaseApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";
  public static final String CANCEL_ENDPOINT = "/badges/{badgeNumber}/cancellations";
  public static final String DELETE_ENDPOINT = "/badges/{badgeNumber}";
  public static final String REPLACE_ENDPOINT = "/badges/{badgeNumber}/replacements";

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
    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT);

    try {
      return Objects.requireNonNull(
              restTemplate.postForObject(
                  builder.toUriString(), request, BadgeNumbersResponse.class))
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
    Assert.notNull(postcode, "Post code supplied must not be null");

    return findBadgeBy(FindBadgeAttribute.POSTCODE, postcode);
  }

  public List<BadgeSummary> findBadgeByName(String name) {
    Assert.notNull(name, "Name supplied must not be null");

    return findBadgeBy(FindBadgeAttribute.NAME, name);
  }

  private List<BadgeSummary> findBadgeBy(FindBadgeAttribute attribute, String value) {
    log.debug("retrieveBadge with " + attribute, value);
    Assert.notNull(attribute, "Attribute supplied must not be null");
    Assert.notNull(value, "Value supplied must be not null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT);
    builder.queryParam(attribute.getDescription(), value);

    BadgesResponse response = new BadgesResponse();
    response.setData(Lists.newArrayList());

    try {
      response = restTemplate.getForObject(builder.build().toUriString(), BadgesResponse.class);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }

    return response.getData();
  }

  public void cancelBadge(String badgeNumber, String reason) {
    Assert.notNull(badgeNumber, "cancel badge, badge number not provided");
    Assert.notNull(reason, "reason for cancellation is not provided");

    String uri = UriComponentsBuilder.fromUriString(CANCEL_ENDPOINT).build().toUriString();

    BadgeCancelRequest badgeCancelRequest = new BadgeCancelRequest();
    badgeCancelRequest.setBadgeNumber(badgeNumber);
    badgeCancelRequest.setCancelReasonCode(reason);

    HttpEntity<BadgeCancelRequest> httpRequest = new HttpEntity<>(badgeCancelRequest);

    try {
      restTemplate.postForEntity(uri, httpRequest, CommonResponse.class, badgeNumber);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }

  public void deleteBadge(String badgeNumber) {
    Assert.notNull(badgeNumber, "delete badge, badge number not provided");
    try {
      restTemplate.delete(DELETE_ENDPOINT, badgeNumber);
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }
  }

  public String replaceBadge(BadgeReplaceRequest request) {
    Assert.notNull(request.getBadgeNumber(), "replace badge, badge number not provided");
    Assert.notNull(request.getDeliverToCode(), "replace badge, deliver to not provided");
    Assert.notNull(request.getDeliveryOptionCode(), "replace badge, delivery option not provided");
    Assert.notNull(request.getReplaceReasonCode(), "replace badge,reason code not provided");
    String uri = UriComponentsBuilder.fromUriString(REPLACE_ENDPOINT).build().toUriString();

    try {
      return Objects.requireNonNull(
              restTemplate.postForObject(
                  uri, request, BadgeNumberResponse.class, request.getBadgeNumber()))
          .getData();
    } catch (HttpClientErrorException c) {
      handleHttpClientException(c);
    }

    return StringUtils.EMPTY;
  }
}
