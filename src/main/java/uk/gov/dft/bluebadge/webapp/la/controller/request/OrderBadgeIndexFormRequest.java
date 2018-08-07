package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBadgeIndexFormRequest implements Serializable {
  private String applicantType;
}
