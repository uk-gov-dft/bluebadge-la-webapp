package uk.gov.service.bluebadge.test.acceptance.steps;

public enum ValidationMessageEnum {
   ***REMOVED***);

  private final String assertFailedMessage;
  private final String elementUiPath;

  ValidationMessageEnum(String assertFailedMessage, String elementUiPath) {
    this.assertFailedMessage = assertFailedMessage;
    this.elementUiPath = elementUiPath;
  }

  public String getAssertFailedMessage() {
    return assertFailedMessage;
  }

  public String getElementUiPath() {
    return elementUiPath;
  }
}
