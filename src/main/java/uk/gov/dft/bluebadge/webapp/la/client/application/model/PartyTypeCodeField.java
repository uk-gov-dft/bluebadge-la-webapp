package uk.gov.dft.bluebadge.webapp.la.client.application.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets PartyTypeCodeField */
public enum PartyTypeCodeField {
  PERSON("PERSON"),

  ORG("ORG");

  private String value;

  PartyTypeCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static PartyTypeCodeField fromValue(String text) {
    for (PartyTypeCodeField b : PartyTypeCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
