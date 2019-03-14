package uk.gov.dft.bluebadge.webapp.la.service;

import com.google.common.collect.Lists;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeReplaceRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataCancellationEnum;

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

  public List<BadgeSummary> findBadgeByPostcode(String postcode) {
    if (StringUtils.isEmpty(postcode)) {
      return Lists.newArrayList();
    }
    return badgeManagementApiClient.findBadgeByPostCode(postcode);
  }

  public List<BadgeSummary> findBadgeByName(String name) {
    if (StringUtils.isEmpty(name)) {
      return Lists.newArrayList();
    }
    return badgeManagementApiClient.findBadgeByName(name);
  }

  public ResponseEntity<byte[]> exportBadgesByLa(String localAuthorityShortCode) {
    Assert.notNull(localAuthorityShortCode, "localAuthorityShortCode should not be null");
    return badgeManagementApiClient.exportBadgesByLa(localAuthorityShortCode);
  }

  public void cancelBadge(String badgeNumber, RefDataCancellationEnum reason) {
    Assert.notNull(badgeNumber, "Badge number should not be null");
    Assert.notNull(reason, "cancellation reason should not be null");
    badgeManagementApiClient.cancelBadge(badgeNumber, reason.getValue());
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
