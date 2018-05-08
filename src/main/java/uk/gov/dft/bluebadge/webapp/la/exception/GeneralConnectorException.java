package uk.gov.dft.bluebadge.webapp.la.exception;

public class GeneralConnectorException extends RuntimeException {

  public GeneralConnectorException(String message, Exception cause) {
    super(message, cause);
  }
}
