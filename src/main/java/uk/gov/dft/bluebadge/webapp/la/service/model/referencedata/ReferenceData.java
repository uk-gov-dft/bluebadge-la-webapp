package uk.gov.dft.bluebadge.webapp.la.service.model.referencedata;

import lombok.Data;

@Data
public class ReferenceData {
  private String value;
  private String label;
  private Integer order;

  public ReferenceData(String value, String label, Integer order) {
    this.value = value;
    this.label = label;
    this.order = order;
  }
}
