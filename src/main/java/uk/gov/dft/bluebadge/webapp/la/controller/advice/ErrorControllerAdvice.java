package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;

@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

  private static final String URL = "/unexpected-error";

  private static final String REDIRECT_URL = "redirect:" + URL;

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleHttpServerErrorException(HttpServerErrorException exception) {
    log.debug("Server error, exception [()], details [()]", exception.getMessage(), exception);

    return REDIRECT_URL + "?message=server error";
  }

  @ExceptionHandler(BadRequestException.class)
  public String handleBadRequestException(BadRequestException exception) {
    log.debug("Bad Request, exception [()], details [()]", exception.getMessage(), exception);

    return REDIRECT_URL + "?message=client error";
  }

  // When a server is down, i.e. usermanagement or badgemanagement
  @ExceptionHandler(ResourceAccessException.class)
  public String handleResourceAccessException(ResourceAccessException exception) {
    log.debug(
        "Resource access exception, exception [()], details [()]",
        exception.getMessage(),
        exception);

    return REDIRECT_URL + "?message=resource access exception";
  }
}
