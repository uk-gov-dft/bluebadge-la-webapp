package uk.gov.dft.bluebadge.webapp.la.controller.exceptions;

import java.util.Optional;

public class InvalidSessionException extends RuntimeException {
  private Optional<String> redirectUrl;

  public InvalidSessionException(String message) {
    this(message, null);
  }

  public InvalidSessionException(String message, String redirectUrl) {
    super(message);
    this.redirectUrl = Optional.ofNullable(redirectUrl);
  }

  public Optional<String> getRedirectUrl() {
    return redirectUrl;
  }
}
