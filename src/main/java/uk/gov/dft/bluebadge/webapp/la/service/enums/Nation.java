package uk.gov.dft.bluebadge.webapp.la.service.enums;

public enum Nation {
  ENG("England"),
  SCO("Scotland"),
  WLS("Wales"),
  NIR("Northern Ireland");

  public String getCode() {
    return code;
  }

  private final String code;

  Nation(String code) {
    this.code = code;
  }
}
