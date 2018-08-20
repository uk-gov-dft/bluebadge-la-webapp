package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets EligibilityCodeField */
public enum EligibilityCodeField {
  PIP("PIP"),

  DLA("DLA"),

  AFRFCS("AFRFCS"),

  WPMS("WPMS"),

  BLIND("BLIND"),

  WALKD("WALKD"),

  ARMS("ARMS"),

  CHILDBULK("CHILDBULK"),

  CHILDVEHIC("CHILDVEHIC"),

  TERMILL("TERMILL");

  private String value;

  EligibilityCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static EligibilityCodeField fromValue(String text) {
    for (EligibilityCodeField b : EligibilityCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
