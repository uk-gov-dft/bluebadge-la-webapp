package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;

@Slf4j
public class ErrorHandlingUtils {

  private ErrorHandlingUtils() {}

  public static void bindBadRequestException(
      BadRequestException c, BindingResult bindingResult, Model model) {

    TemplateModelUtils.addCustomError("error.form.summary.title", "empty", model);
    Assert.notNull(c, "400 response without CommonResponse");
    try {
      for (ErrorErrors errorItem : c.getCommonResponse().getError().getErrors()) {
        bindingResult.rejectValue(errorItem.getField(), errorItem.getMessage());
      }
    } catch (NullPointerException npe) {
      log.warn("bindBadRequestException called with empty error list. BindRequestException:{}", c);
    }
  }

  public static void sortAndFilterErrors(Error error, List<String> errorListOrder) {
    List<ErrorErrors> filteredAndSorted =
        error
            .getErrors()
            .stream()
            .filter(e -> errorListOrder.contains(e.getField()))
            .sorted(new ErrorComparator((errorListOrder)))
            .collect(Collectors.toList());

    error.setErrors(filteredAndSorted);
  }
}
