package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.Error;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.ErrorErrors;

public class ErrorHandlingUtilsTest {
  private static final String SUCCESS_TEMPLATE = "successTemplate";
  private static final String ERROR_TEMPLATE = "errorTemplate";

  @Before
  public void setUp() {}

  @Test
  public void handleError_shouldReturnSuccessTemplate_WhenErrorIsNull() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("field1", "value1");
    map.put("field2", "value2");
    BindingResult bindingResult = new MapBindingResult(map, "result");
    Model model = new ExtendedModelMap();
    String template =
        ErrorHandlingUtils.handleError(
            null, SUCCESS_TEMPLATE, ERROR_TEMPLATE, bindingResult, model, null);
    assertThat(template).isEqualTo(SUCCESS_TEMPLATE);
  }

  @Test
  public void
      handleError_shouldReturnErrorTemplateAndSetErrorsInModelAndBindingResult_WhenErrorIsNotNull() {
    Error error =
        new Error()
            .errors(Lists.newArrayList(new ErrorErrors().field("field1").message("error message")));
    Map<String, String> map = new HashMap<String, String>();
    map.put("field1", "value1");
    map.put("field2", "value2");
    BindingResult bindingResult = new MapBindingResult(map, "result");
    Model model = new ExtendedModelMap();
    String template =
        ErrorHandlingUtils.handleError(
            error, SUCCESS_TEMPLATE, ERROR_TEMPLATE, bindingResult, model, null);
    assertThat(template).isEqualTo(ERROR_TEMPLATE);
  }
}
