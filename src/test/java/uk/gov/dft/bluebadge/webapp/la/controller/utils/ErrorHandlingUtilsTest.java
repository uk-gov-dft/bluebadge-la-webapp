package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.Error;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.ErrorErrors;

public class ErrorHandlingUtilsTest {

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock BindingResult bindingResult;

  @Mock Model model;

  @Test
  public void bindBadRequestException() {
    CommonResponse commonResponse = new CommonResponse();
    Error error = new Error();
    ErrorErrors errorErrors = new ErrorErrors();
    errorErrors.setField("theField");
    errorErrors.setMessage("the.message.key");
    error.addErrorsItem(errorErrors);
    commonResponse.setError(error);
    BadRequestException e = new BadRequestException(commonResponse);

    ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
    verify(bindingResult, times(1)).rejectValue(eq("theField"), eq("the.message.key"));
    verify(model, times(1)).addAttribute(eq("errorSummary"), any());
  }

  @Test
  public void bindBadRequestException_invalidException() {
    CommonResponse commonResponse = new CommonResponse();
    BadRequestException e = new BadRequestException(commonResponse);

    ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
    // No exception and error gets in to model
    verify(model, times(1)).addAttribute(eq("errorSummary"), any());
  }
}
