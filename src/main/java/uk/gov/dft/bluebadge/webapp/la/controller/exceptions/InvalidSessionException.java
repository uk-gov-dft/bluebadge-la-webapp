package uk.gov.dft.bluebadge.webapp.la.controller.exceptions;

import java.util.Optional;

public class InvalidSessionException extends RuntimeException {
  private final String redirectUrl;

  public InvalidSessionException(String message) {
    this(message, null);
  }

  public InvalidSessionException(String message, String redirectUrl) {
    super(message);
    this.redirectUrl = redirectUrl;
  }

  public Optional<String> getRedirectUrl() {
    return Optional.ofNullable(redirectUrl);
  }
}
