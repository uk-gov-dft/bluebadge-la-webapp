package uk.gov.dft.bluebadge.webapp.la.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Authentication exception for when the user enters an invalid email address (username). To allow
 * for specific messaging on login fails.
 */
public class InvalidEmailFormatException extends AuthenticationException {
  public InvalidEmailFormatException(String msg) {
    super(msg);
  }
}
