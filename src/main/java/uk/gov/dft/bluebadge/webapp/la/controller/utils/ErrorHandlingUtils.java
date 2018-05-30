package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.model.usermanagement.Error;

public class ErrorHandlingUtils {

  public static final String handleError(
      Error error,
      String successTemplate,
      String errorTemplate,
      BindingResult bindingResult,
      Model model) {
    if (error == null || error.getErrors() == null || error.getErrors().isEmpty()) {
      return successTemplate;
    } else {
      TemplateModelUtils.addCustomError("Fix the following errors:", "", model);
      BindingResultUtils.addApiErrors(error, bindingResult);
      // TemplateModelUtils.addApiError(error, model);
      return errorTemplate;
    }
  }
}
