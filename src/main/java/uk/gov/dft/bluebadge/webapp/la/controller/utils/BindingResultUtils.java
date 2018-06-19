package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.Error;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.ErrorErrors;

public class BindingResultUtils {
  /**
   * Populates BindingResult with the given error.
   *
   * @param error the error to add to binding result, it must refer to an existing field name.
   * @param bindingResult the binding result to which we add the error, should contain the field
   *     name referred by the error.
   */
  public static void addApiErrors(final Error error, final BindingResult bindingResult) {
    for (ErrorErrors errorItem : error.getErrors()) {
      addCustomError(errorItem.getField(), errorItem.getMessage(), bindingResult);
    }
  }

  /**
   * Populates BindingResult with an error on the field identified by formField with the given
   * message.
   *
   * @param formField the id of the field in the form where we want to assign the error.
   * @param message the message with the error to display.
   * @param bindingResult the binding result whose field with id formField will get the error
   *     message.
   */
  public static void addCustomError(
      final String formField, final String message, final BindingResult bindingResult) {
    bindingResult.rejectValue(formField, message);
  }

}
