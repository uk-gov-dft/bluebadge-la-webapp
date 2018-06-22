package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.Error;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.ErrorErrors;

public class ErrorHandlingUtils {

  public static String handleError(
      Error error,
      String successTemplate,
      String errorTemplate,
      BindingResult bindingResult,
      Model model,
      List<String> errorListOrder) {

    return ErrorHandlingUtils.internalHandleError(
        error, successTemplate, errorTemplate, bindingResult, model, errorListOrder);
  }

  public static String handleError(
      Error error,
      String successTemplate,
      String errorTemplate,
      BindingResult bindingResult,
      Model model) {

    return ErrorHandlingUtils.internalHandleError(
        error, successTemplate, errorTemplate, bindingResult, model, null);
  }

  private static String internalHandleError(
      Error error,
      String successTemplate,
      String errorTemplate,
      BindingResult bindingResult,
      Model model,
      List<String> errorListOrder) {

    if (hasNoErrors(error)) {
      return successTemplate;
    }

    TemplateModelUtils.addCustomError("error.form.summary.title", "empty", model);

    if (errorListOrder != null) {
      sortAndFilterErrors(error, errorListOrder);
    }

    BindingResultUtils.addApiErrors(error, bindingResult);

    // TemplateModelUtils.addApiError(error, model);
    return errorTemplate;
  }

  private static void sortAndFilterErrors(Error error, List<String> errorListOrder) {
    List<ErrorErrors> filteredAndSorted =
        error
            .getErrors()
            .stream()
            .filter(e -> errorListOrder.contains(e.getField()))
            .sorted(new ErrorComparator((errorListOrder)))
            .collect(Collectors.toList());

    error.setErrors(filteredAndSorted);
  }

  private static boolean hasNoErrors(Error error) {
    return error == null || error.getErrors() == null || error.getErrors().isEmpty();
  }
}
