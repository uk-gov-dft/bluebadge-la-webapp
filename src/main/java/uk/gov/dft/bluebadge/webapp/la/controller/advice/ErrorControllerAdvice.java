package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;

@ControllerAdvice
public class ErrorControllerAdvice {

  private static final String URL = "/unexpected-error";

  private static final String REDIRECT_URL = "redirect:/unexpected-error";

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleHttpServerErrorException(Exception exception) {
    return REDIRECT_URL + "?message=server error";
  }

  @ExceptionHandler(BadRequestException.class)
  public String handleBadRequestException(Exception exception) {
    return REDIRECT_URL + "?message=client error";
  }
}
