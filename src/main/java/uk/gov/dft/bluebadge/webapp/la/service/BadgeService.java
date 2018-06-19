package uk.gov.dft.bluebadge.webapp.la.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.client.badgemanagement.api.BadgeManagementApiClient;

@Service
public class BadgeService {

  private BadgeManagementApiClient badgeServiceApiClient;

  @Autowired
  public BadgeService(BadgeManagementApiClient badgeServiceApiClient) {
    this.badgeServiceApiClient = badgeServiceApiClient;
  }

  public void validateOrder() {
    //this.badgeServiceApiClient.validateOrder();
    return;
  }
}
