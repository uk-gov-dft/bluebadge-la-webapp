package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LookupBadgeViewModel implements Serializable {
  private String badgeNumber;
  private String expiryDate;
  private String status;
}
