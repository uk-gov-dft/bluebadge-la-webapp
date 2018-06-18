package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.model.usermanagement.Error;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandlingUtils {

  public static final String handleError(
          Error error,
          String successTemplate,
          String errorTemplate,
          BindingResult bindingResult,
          Model model,
          List<String> errorListOrder) {

    if (error == null || error.getErrors() == null || error.getErrors().isEmpty()) {
      return successTemplate;
    } else {
      TemplateModelUtils.addCustomError("error.form.summary.title", "empty", model);
      BindingResultUtils.addApiErrors(error, bindingResult, errorListOrder);
      // TemplateModelUtils.addApiError(error, model);
      return errorTemplate;
    }

  }

}
