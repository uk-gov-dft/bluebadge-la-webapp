package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;

public class TemplateModelUtils {

  private TemplateModelUtils() {}

  public static void addApiError(final Error error, final Model model) {
    String message = error.getMessage();
    addCustomError(message, message, model);
  }

  public static void addCustomError(
      final String title, final String description, final Model model) {
    model.addAttribute("errorSummary", new ErrorViewModel(title, description));
  }
}
