package uk.gov.dft.bluebadge.webapp.la.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CannotUpdateException extends RuntimeException {
  public CannotUpdateException() {
    super();
  }

  public CannotUpdateException(Exception ex) {
    super(ex);
  }
}
