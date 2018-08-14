package uk.gov.dft.bluebadge.webapp.la.service.referencedata;

public enum RefDataCancellationEnum {
  REVOKE("REVOKE"),
  DECEASE("DECEASE"),
  NOLONG("NOLONG");

  public String getValue() {
    return value;
  }

  private final String value;

  RefDataCancellationEnum(String value) {
    this.value = value;
  }
}
