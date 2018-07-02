package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * It changes all string fields in backing/ form objects from empty strings to null if they are
 * empty strings, if not, it does not make any change.
 *
 * <p>The reason is we need them to be null, to trigger the validation of @Pattern in optional
 * fields, only when there is a value.
 */
@ControllerAdvice
public class GlobalBindingAdvice {
  @InitBinder
  public void customizeBinding(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }
}
