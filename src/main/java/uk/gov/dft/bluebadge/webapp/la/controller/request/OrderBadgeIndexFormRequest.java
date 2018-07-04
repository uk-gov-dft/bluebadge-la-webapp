package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderBadgeIndexFormRequest implements Serializable {
  private String applicantType;
}
