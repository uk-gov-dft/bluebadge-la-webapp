package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

@Slf4j
class BaseController {
  private static final String ERROR_SUFFIX = "#error";

  String redirectToOnBindingError(
      String currentFormUrl,
      Object formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr) {
    return redirectToOnBindingError(
        currentFormUrl, formRequest, bindingResult, attr, "formRequest");
  }

  String redirectToOnBindingError(
      String currentFormUrl,
      Object formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr,
      String modelAttributeName) {
    attr.addFlashAttribute("errorSummary", new ErrorViewModel());
    attr.addFlashAttribute(
        "org.springframework.validation.BindingResult." + modelAttributeName, bindingResult);
    attr.addFlashAttribute(modelAttributeName, formRequest);
    return "redirect:" + currentFormUrl + ERROR_SUFFIX;
  }

  String redirectToOnBindingError(
      BadRequestException e,
      Model model,
      String currentFormUrl,
      Object formRequest,
      BindingResult bindingResult,
      RedirectAttributes attr) {
    if (null != e.getCommonResponse()) {
      log.warn(
          "Unexpected bad request from service call. Error:{}, Values{}",
          e.getCommonResponse(),
          formRequest);
    } else {
      log.warn("Unexpected bad request.  No common response. Values causing it:{}", formRequest);
    }
    ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);

    return redirectToOnBindingError(currentFormUrl, formRequest, bindingResult, attr);
  }
}
