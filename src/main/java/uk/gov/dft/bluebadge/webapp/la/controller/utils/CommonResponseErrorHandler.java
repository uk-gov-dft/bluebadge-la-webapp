package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import lombok.Builder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.model.usermanagement.Error;

public class CommonResponseErrorHandler {
  private final Error error;
  private final String successTemplate;
  private final String errorTemplate;
  private final BindingResult bindingResult;
  private final Model model;

  /**
   * @param error
   * @param successTemplate
   * @param errorTemplate
   * @param bindingResult
   * @param model
   */
  public CommonResponseErrorHandler(Error error, String successTemplate, String errorTemplate, BindingResult bindingResult, Model model) {
    this.error = error;
    this.successTemplate = successTemplate;
    this.errorTemplate = errorTemplate;
    this.bindingResult = bindingResult;
    this.model = model;
  }

  public Error getError() {
    return error;
  }

  public String getSuccessTemplate() {
    return successTemplate;
  }

  public String getErrorTemplate() {
    return errorTemplate;
  }

  public BindingResult getBindingResult() {
    return bindingResult;
  }

  public Model getModel() {
    return model;
  }


  public static final class CommonResponseErrorHandlerBuilder {
    private Error error;
    private String successTemplate;
    private String errorTemplate;
    private BindingResult bindingResult;
    private Model model;

    private CommonResponseErrorHandlerBuilder() {
    }

    public static CommonResponseErrorHandlerBuilder aCommonResponseErrorHandler() {
      return new CommonResponseErrorHandlerBuilder();
    }

    public CommonResponseErrorHandlerBuilder withError(Error error) {
      this.error = error;
      return this;
    }

    public CommonResponseErrorHandlerBuilder withSuccessTemplate(String successTemplate) {
      this.successTemplate = successTemplate;
      return this;
    }

    public CommonResponseErrorHandlerBuilder withErrorTemplate(String errorTemplate) {
      this.errorTemplate = errorTemplate;
      return this;
    }

    public CommonResponseErrorHandlerBuilder withBindingResult(BindingResult bindingResult) {
      this.bindingResult = bindingResult;
      return this;
    }

    public CommonResponseErrorHandlerBuilder withModel(Model model) {
      this.model = model;
      return this;
    }

    public CommonResponseErrorHandler build() {
      return new CommonResponseErrorHandler(error, successTemplate, errorTemplate, bindingResult, model);
    }
  }
}
