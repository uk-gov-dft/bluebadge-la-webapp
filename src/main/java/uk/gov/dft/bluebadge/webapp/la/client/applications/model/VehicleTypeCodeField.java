package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets VehicleTypeCodeField */
public enum VehicleTypeCodeField {
  CAR("CAR"),

  PEOPLECAR("PEOPLECAR"),

  MINIBUS("MINIBUS"),

  OTHERVEH("OTHERVEH");

  private String value;

  VehicleTypeCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static VehicleTypeCodeField fromValue(String text) {
    for (VehicleTypeCodeField b : VehicleTypeCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
