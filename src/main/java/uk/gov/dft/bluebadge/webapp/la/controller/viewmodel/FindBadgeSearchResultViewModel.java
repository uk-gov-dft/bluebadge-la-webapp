package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBadgeSearchResultViewModel {
  private String badgeNumber;
  private String name;
  private String postCode;
  private String localAuthority;
  private String expiryDate;
  private String status;
}
