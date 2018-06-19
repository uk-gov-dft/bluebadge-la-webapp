package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ErrorControllerAdvice {

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleHttpServerErrorException(Model model, Exception exception) {
    model.addAttribute("exception", exception.getMessage());
    return "http-server-error-exception";
  }
}
