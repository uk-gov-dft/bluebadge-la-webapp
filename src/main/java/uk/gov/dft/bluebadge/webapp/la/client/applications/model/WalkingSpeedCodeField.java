package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Required if able to walk, i.e. Walking length of time not cannot walk. */
public enum WalkingSpeedCodeField {
  MSLOW("MSLOW"),

  SLOW("SLOW"),

  SAME("SAME"),

  FAST("FAST");

  private String value;

  WalkingSpeedCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static WalkingSpeedCodeField fromValue(String text) {
    for (WalkingSpeedCodeField b : WalkingSpeedCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
