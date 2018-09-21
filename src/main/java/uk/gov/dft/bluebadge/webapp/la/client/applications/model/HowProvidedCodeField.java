package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets HowProvidedCodeField */
public enum HowProvidedCodeField {
  PRESCRIBE("PRESCRIBE"),

  PRIVATE("PRIVATE"),

  SOCIAL("SOCIAL");

  private String value;

  HowProvidedCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static HowProvidedCodeField fromValue(String text) {
    for (HowProvidedCodeField b : HowProvidedCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
