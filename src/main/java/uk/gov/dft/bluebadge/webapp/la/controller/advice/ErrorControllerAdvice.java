package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ErrorControllerAdvice {

  private static final String TEMPLATE_HTTP_SERVER_ERROR = "http-server-error-exception";

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleHttpServerErrorException(Model model, Exception exception) {
    model.addAttribute("exception", exception.getMessage());
    return TEMPLATE_HTTP_SERVER_ERROR;
  }
}
