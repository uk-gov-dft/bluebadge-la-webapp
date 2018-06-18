package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.model.usermanagement.Error;

import java.util.Collections;
import java.util.List;

public class ErrorHandlingUtils {


  /**
   *
   * @param commonResponseErrorHandler@return
   */
  public static final String handleError(CommonResponseErrorHandler commonResponseErrorHandler) {
    return ErrorHandlingUtils.internalHandleError(commonResponseErrorHandler.getError(), commonResponseErrorHandler.getSuccessTemplate(), commonResponseErrorHandler.getErrorTemplate(), commonResponseErrorHandler.getBindingResult(), commonResponseErrorHandler.getModel(), null);
  }

  /**
   *
   * @param error
   * @param successTemplate
   * @param errorTemplate
   * @param bindingResult
   * @param model
   * @return
   */
  public static final String handleError(
    Error error,
    String successTemplate,
    String errorTemplate,
    BindingResult bindingResult,
    Model model,
    List<String> errorListOrder) {

    // validate arguments and include checking the errorListOrder is not null

    return ErrorHandlingUtils.internalHandleError(error, successTemplate, errorTemplate, bindingResult, model, null);
  }

  /**
   * @param error
   * @param successTemplate
   * @param errorTemplate
   * @param bindingResult
   * @param model
   * @param errorListOrder
   * @return
   */
  private static final String internalHandleError(
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

    //sort
    sortAndFilterErrors(error, errorListOrder);

    BindingResultUtils.addApiErrors(error, bindingResult);

    // TemplateModelUtils.addApiError(error, model);
    return errorTemplate;

  }

  private static void sortAndFilterErrors(Error error, List<String> errorListOrder) {
    if(errorListOrder != null || errorListOrder.size() > 0) {
      Collections.sort(error.getErrors(), new ErrorComparator(errorListOrder));
    }
  }

  private static boolean hasNoErrors(Error error) {
    return error == null || error.getErrors() == null || error.getErrors().isEmpty();
  }

}
