package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement;

import com.google.common.net.MediaType;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeCancelRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumberResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeNumbersResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeResponse;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;

@Slf4j
@Service
public class BadgeManagementApiClient {

  private static final String BADGES_BASE_ENDPOINT = "badges";
  private static final String CANCEL_ENDPOINT = "/badges/{badgeNumber}/cancellations";
  private static final String DELETE_ENDPOINT = "/badges/{badgeNumber}";
  private static final String RETRIEVE_ENDPOINT = "/badges/{badgeNumber}";
  private static final String REPLACE_ENDPOINT = "/badges/{badgeNumber}/replacements";

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

    return Objects.requireNonNull(
            restTemplate.postForObject(builder.toUriString(), request, BadgeNumbersResponse.class))
        .getData();
  }

  /**
   * Retrieves a specific badge.
   *
   * @param badgeNumber cannot be null.
   * @return
   */
  public Badge retrieveBadge(String badgeNumber) {
    Assert.notNull(badgeNumber, "badgeNumber supplied must be not null");

    log.info("retrieveBadge {}", badgeNumber);
    BadgeResponse response =
        restTemplate.getForObject(RETRIEVE_ENDPOINT, BadgeResponse.class, badgeNumber);
    return response.getData();
  }

  public BadgesResponse findBadgeByPostCode(String postcode, PagingInfo pageInfo) {
    Assert.notNull(postcode, "Post code supplied must not be null");

    return findBadgeBy(FindBadgeAttribute.POSTCODE, postcode, pageInfo);
  }

  public BadgesResponse findBadgeByName(String name, PagingInfo pageInfo) {
    Assert.notNull(name, "Name supplied must not be null");

    return findBadgeBy(FindBadgeAttribute.NAME, name, pageInfo);
  }

  private BadgesResponse findBadgeBy(
      FindBadgeAttribute attribute, String value, PagingInfo pageInfo) {
    log.debug("retrieveBadge with " + attribute, value);
    Assert.notNull(attribute, "Attribute supplied must not be null");
    Assert.notNull(value, "Value supplied must be not null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT);
    builder.queryParam(attribute.getDescription(), value);
    builder.queryParam("pageSize", pageInfo.getPageSize());
    builder.queryParam("pageNum", pageInfo.getPageNum());

    return restTemplate.getForObject(builder.build().toUriString(), BadgesResponse.class);
  }

  public ResponseEntity<byte[]> exportBadgesByLa(String localAuthorityShortCode) {
    log.debug("exportBadgesByLa with la [{}]", localAuthorityShortCode);
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode supplied must not be null");

    UriComponentsBuilder builder =
        UriComponentsBuilder.newInstance().path("/").pathSegment(BADGES_BASE_ENDPOINT);
    builder.queryParam("laShortCode", localAuthorityShortCode);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.ZIP.toString());
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    return restTemplate.exchange(
        builder.build().toUriString(), HttpMethod.GET, requestEntity, byte[].class);
  }

  public void cancelBadge(String badgeNumber, CancelReason reason) {
    Assert.notNull(badgeNumber, "cancel badge, badge number not provided");
    Assert.notNull(reason, "reason for cancellation is not provided");

    String uri = UriComponentsBuilder.fromUriString(CANCEL_ENDPOINT).build().toUriString();

    BadgeCancelRequest badgeCancelRequest =
        BadgeCancelRequest.builder().badgeNumber(badgeNumber).cancelReasonCode(reason).build();

    HttpEntity<BadgeCancelRequest> httpRequest = new HttpEntity<>(badgeCancelRequest);
    restTemplate.postForEntity(uri, httpRequest, CommonResponse.class, badgeNumber);
  }

  public void deleteBadge(String badgeNumber) {
    Assert.notNull(badgeNumber, "delete badge, badge number not provided");
    restTemplate.delete(DELETE_ENDPOINT, badgeNumber);
  }

  public String replaceBadge(BadgeReplaceRequest request) {
    Assert.notNull(request.getBadgeNumber(), "replace badge, badge number not provided");
    Assert.notNull(request.getDeliverToCode(), "replace badge, deliver to not provided");
    Assert.notNull(request.getDeliveryOptionCode(), "replace badge, delivery option not provided");
    Assert.notNull(request.getReplaceReasonCode(), "replace badge,reason code not provided");
    String uri = UriComponentsBuilder.fromUriString(REPLACE_ENDPOINT).build().toUriString();

    return Objects.requireNonNull(
            restTemplate.postForObject(
                uri, request, BadgeNumberResponse.class, request.getBadgeNumber()))
        .getData();
  }
}
