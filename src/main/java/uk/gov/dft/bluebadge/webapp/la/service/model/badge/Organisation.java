package uk.gov.dft.bluebadge.webapp.la.service.model.badge;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organisation {
  private String badgeHolderName = null;
}
