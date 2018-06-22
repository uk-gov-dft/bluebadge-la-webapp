package uk.gov.dft.bluebadge.webapp.la.service.model.referencedata;

import lombok.Data;

@Data
public class Eligibility {
  private String value;
  private String label;

  public Eligibility(String value, String label) {
    this.value = value;
    this.label = label;
  }
}
