package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets WalkingDifficultyTypeCodeField */
public enum WalkingDifficultyTypeCodeField {
  PAIN("PAIN"),

  BREATH("BREATH"),

  BALANCE("BALANCE"),

  LONGTIME("LONGTIME"),

  DANGER("DANGER"),

  STRUGGLE("STRUGGLE"),

  SOMELSE("SOMELSE");

  private String value;

  WalkingDifficultyTypeCodeField(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  // @JsonCreator
  public static WalkingDifficultyTypeCodeField fromValue(String text) {
    for (WalkingDifficultyTypeCodeField b : WalkingDifficultyTypeCodeField.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
