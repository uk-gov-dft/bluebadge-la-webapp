package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.BadgeManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;

@Service
public class BadgeService {

  private BadgeManagementApiClient badgeManagementApiClient;

  @Autowired
  public BadgeService(BadgeManagementApiClient badgeManagementApiClient) {
    this.badgeManagementApiClient = badgeManagementApiClient;
  }

  public String orderABadgeForAPerson(BadgeOrderRequest badgeOrderRequest) {
    Assert.notNull(badgeOrderRequest, "badgeOrderRequest should not be null");

    List<String> badgeNumbers =
        badgeManagementApiClient.orderBlueBadges(badgeOrderRequest.numberOfBadges(1));

    Assert.notEmpty(badgeNumbers, "badgeNumbers should not be empty");

    return badgeNumbers.get(0);
  }

  public Optional<Badge> retrieve(String badgeNumber) {
    if (badgeNumber == null || badgeNumber.isEmpty()) {
      return Optional.empty();
    } else {
      try {
        return Optional.of(badgeManagementApiClient.retrieveBadge(badgeNumber));
      } catch (NotFoundException ex) {
        return Optional.empty();
      }
    }
  }

  public List<BadgeSummary> findBadgesByPostcode(String postcode) {
    if (postcode == null || postcode.isEmpty()) {
      return Lists.newArrayList();
    }

    try {
        return badgeManagementApiClient.findBadgeBy("postCode", postcode);
    } catch (NotFoundException ex) {
        return Lists.newArrayList();
    }
  }
}
