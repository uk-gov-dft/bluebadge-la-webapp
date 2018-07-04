package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderBadgeIndexFormRequest implements Serializable {
  private String applicantType;
}
