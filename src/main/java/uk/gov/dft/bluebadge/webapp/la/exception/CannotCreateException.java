package uk.gov.dft.bluebadge.webapp.la.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CannotCreateException extends RuntimeException {
  public CannotCreateException() {
    super();
  }

  public CannotCreateException(Exception ex) {
    super(ex);
  }
}
