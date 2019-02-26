package uk.gov.dft.bluebadge.webapp.la.service.enums;

public enum ClockType {
  STANDARD("Standard"),
  WALLET("Wallet");

  public String getCode() {
    return code;
  }

  private final String code;

  ClockType(String code) {
    this.code = code;
  }
}
