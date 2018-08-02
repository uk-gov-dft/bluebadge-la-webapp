package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBadgeSearchResultViewModel implements Serializable {
  private String badgeNumber;
  private String name;
  private String postCode;
  private String localAuthority;
  private String expiryDate;
  private String status;
}
