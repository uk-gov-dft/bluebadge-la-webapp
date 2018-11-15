package uk.gov.dft.bluebadge.webapp.la.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Authentication exception for when the authorisation server is unreachable. To allow for specific
 * messaging on login fails.
 */
public class AuthServerConnectionException extends AuthenticationException {
  public AuthServerConnectionException(String msg) {
    super(msg);
  }
}
