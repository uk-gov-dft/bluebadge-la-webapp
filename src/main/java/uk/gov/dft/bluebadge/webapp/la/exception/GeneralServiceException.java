package uk.gov.dft.bluebadge.webapp.la.exception;

public class GeneralServiceException extends RuntimeException {

  public GeneralServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
