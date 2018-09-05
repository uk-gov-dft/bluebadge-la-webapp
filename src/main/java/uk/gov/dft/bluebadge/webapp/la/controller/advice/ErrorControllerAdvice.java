package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.controller.ErrorHandlerController;

@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

  private static final String REDIRECT_URL = "redirect:/" + ErrorHandlerController.ERROR_500_URL;

  @ExceptionHandler(Exception.class)
  public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
    String message = ex.getClass().getName();
    logException(message, ex);
    redirectAttributes.addFlashAttribute("exception", ex);
    return REDIRECT_URL;
  }

  private void logException(String title, Exception ex) {
    log.debug(title + " exception, exception [()], details [()]", ex.getMessage(), ex);
  }
}
