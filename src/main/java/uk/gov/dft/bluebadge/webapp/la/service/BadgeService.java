package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.service.converters.BadgeOrderToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.service.model.badge.BadgeOrder;

@Service
public class BadgeService {

  private BadgeManagementApiClient badgeManagementApiClient;
  private BadgeOrderToBadgeOrderRequest converter;

  @Autowired
  public BadgeService(
      BadgeManagementApiClient badgeManagementApiClient, BadgeOrderToBadgeOrderRequest converter) {
    this.badgeManagementApiClient = badgeManagementApiClient;
    this.converter = converter;
  }

  public String orderABadge(BadgeOrder badgeOrder) {
    Assert.notNull(badgeOrder, "badgeOrder should not be null");

    BadgeOrderRequest request = converter.convert(badgeOrder);

    List<String> badgeNumbers = badgeManagementApiClient.orderBlueBadges(request);

    Assert.notEmpty(badgeNumbers, "badgeNumbers should not be empty");

    return badgeNumbers.get(0);
  }
}
