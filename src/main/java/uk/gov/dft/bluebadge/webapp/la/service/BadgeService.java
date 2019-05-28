package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.service.enums.CancelReason;

@Service
@Slf4j
public class BadgeService {

  private BadgeManagementApiClient badgeManagementApiClient;

  @Autowired
  public BadgeService(BadgeManagementApiClient badgeManagementApiClient) {
    this.badgeManagementApiClient = badgeManagementApiClient;
  }

  public List<String> orderABadge(BadgeOrderRequest badgeOrderRequest) {
    Assert.notNull(badgeOrderRequest, "badgeOrderRequest should not be null");
    log.debug("Ordering [{}] badges.", badgeOrderRequest.getNumberOfBadges());
    Assert.notNull(badgeOrderRequest.getNumberOfBadges(), "numberOfBadges should not be null");

    List<String> badgeNumbers = badgeManagementApiClient.orderBlueBadges(badgeOrderRequest);

    Assert.notEmpty(badgeNumbers, "badgeNumbers should not be empty");

    return badgeNumbers;
  }

  public List<String> orderABadgeForAPerson(
      BadgeOrderRequest badgeOrderRequest, byte[] imageByteArray) {
    Assert.notNull(badgeOrderRequest, "badgeOrderRequest should not be null");
    Assert.notNull(imageByteArray, "image byte array cannot be null");

    String base64 = Base64.getEncoder().encodeToString(imageByteArray);
    badgeOrderRequest.setImageFile(base64);

    return orderABadge(badgeOrderRequest);
  }

  public Optional<Badge> retrieve(String badgeNumber) {
    if (StringUtils.isEmpty(badgeNumber)) {
      return Optional.empty();
    }
    try {
      return Optional.of(badgeManagementApiClient.retrieveBadge(badgeNumber));
    } catch (NotFoundException ex) {
      log.debug("Badge number:{} could not be found!", badgeNumber);
      return Optional.empty();
    }
  }

  public BadgesResponse findBadgeByPostcode(String postcode, PagingInfo pageInfo) {
    return badgeManagementApiClient.findBadgeByPostCode(postcode, pageInfo);
  }

  public BadgesResponse findBadgeByName(String name, PagingInfo pageInfo) {
    return badgeManagementApiClient.findBadgeByName(name, pageInfo);
  }

  public ResponseEntity<byte[]> exportBadgesByLa(String localAuthorityShortCode) {
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    return badgeManagementApiClient.exportBadgesByLa(localAuthorityShortCode);
  }

  public void cancelBadge(String badgeNumber, CancelReason reason) {
    Assert.notNull(badgeNumber, "Badge number should not be null");
    Assert.notNull(reason, "cancellation reason should not be null");
    badgeManagementApiClient.cancelBadge(badgeNumber, reason);
  }

  public void deleteBadge(String badgeNumber) {
    Assert.notNull(badgeNumber, "Badge number should not be null");
    badgeManagementApiClient.deleteBadge(badgeNumber);
  }

  public String replaceBadge(BadgeReplaceRequest request) {
    Assert.notNull(request.getBadgeNumber(), "replace badge, badge number not provided");
    Assert.notNull(request.getDeliverToCode(), "replace badge, deliver to not provided");
    Assert.notNull(request.getDeliveryOptionCode(), "replace badge, delivery option not provided");
    Assert.notNull(request.getReplaceReasonCode(), "replace badge,reason code not provided");

    return badgeManagementApiClient.replaceBadge(request);
  }
}
