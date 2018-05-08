package uk.gov.dft.bluebadge.webapp.la.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GeneralControllerException extends RuntimeException {

  public GeneralControllerException(String message, Exception cause) {
    super(message, cause);
  }
}
