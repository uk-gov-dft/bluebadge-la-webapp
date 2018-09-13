package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets WalkingLengthOfTimeCodeField */
public enum WalkingLengthOfTimeCodeField {
  CANTWALK("CANTWALK"),

  LESSMIN("LESSMIN"),

  FEWMIN("FEWMIN"),

  MORETEN("MORETEN");

  private String value;

  WalkingLengthOfTimeCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static WalkingLengthOfTimeCodeField fromValue(String text) {
    for (WalkingLengthOfTimeCodeField b : WalkingLengthOfTimeCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
