package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LookupBadgeViewModel implements Serializable {
  private String badgeNumber;
  private String expiryDate;
  private String status;
}
