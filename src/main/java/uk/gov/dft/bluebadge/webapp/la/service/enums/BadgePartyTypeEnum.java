package uk.gov.dft.bluebadge.webapp.la.service.enums;

public enum BadgePartyTypeEnum {
  PERSON("PERSON"),
  ORGANISATION("ORG");

  public String getCode() {
    return code;
  }

  private final String code;

  BadgePartyTypeEnum(String code) {
    this.code = code;
  }
}
