package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.ClientApiException;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;

public class ErrorControllerAdviceTest {

  @Mock private HttpServletRequest reqMock;
  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock MessageSource mockMessageSource;

  private ErrorControllerAdvice controllerAdvice;

  @Before
  public void setup() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);
    controllerAdvice = new ErrorControllerAdvice(objectMapper, mockMessageSource);
  }

  @Test
  public void handleException_shouldReturnRedirectToErrorTemplateAndPopulateRedirectAttributes() {
    Exception ex = new Exception("my error message");

    String template = controllerAdvice.handleException(ex, reqMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
  }

  @Test
  public void handleClientApiException_shouldReturnRedirectToGeneralError() {
    CommonResponse commonResponse = new CommonResponse();
    commonResponse
        .error(
            new Error()
                .message("some message")
                .code(500)
                .reason("no reason")
                .errors(Lists.newArrayList(new ErrorErrors())))
        .id("someId")
        .context("context");

    ClientApiException ex = new ClientApiException(commonResponse);

    String template = controllerAdvice.handleClientApiException(ex, reqMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
  }

  @Test
  public void handleNotFoundException_whenKnownErrorCode_shouldRedirectToNotFound() {
    CommonResponse commonResponse = new CommonResponse();
    commonResponse
        .error(
            new Error()
                .message("know.error.code")
                .code(404)
                .reason("no reason")
                .errors(Lists.newArrayList(new ErrorErrors())))
        .id("someId")
        .context("context");

    NotFoundException ex = new NotFoundException(commonResponse);

    when(mockMessageSource.getMessage(eq("know.error.code"), isNull(), any())).thenReturn("bob");

    RedirectAttributes ra = new RedirectAttributesModelMap();
    String template = controllerAdvice.handleNotFoundException(ex, ra, reqMock);
    assertThat(template).isEqualTo("redirect:/not-found");
    assertThat(ra.getFlashAttributes().get("errorCode")).isEqualTo("know.error.code");
  }

  @Test
  public void handleNotFoundException_whenUnknownError_shouldRedirectToGeneralError() {
    CommonResponse commonResponse = new CommonResponse();
    commonResponse
        .error(
            new Error()
                .message("unknown.error.code")
                .code(404)
                .reason("no reason")
                .errors(Lists.newArrayList(new ErrorErrors())))
        .id("someId")
        .context("context");

    NotFoundException ex = new NotFoundException(commonResponse);

    when(mockMessageSource.getMessage(eq("unknown.error.code"), isNull(), any()))
        .thenThrow(new NoSuchMessageException("unknown.error.code"));

    RedirectAttributes ra = new RedirectAttributesModelMap();
    String template = controllerAdvice.handleNotFoundException(ex, ra, reqMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
    assertThat(ra.getFlashAttributes().get("errorCode")).isNull();
  }

  @Test
  public void handleNotFoundException_whenNullError_shouldRedirectToGeneralError() {
    CommonResponse commonResponse = new CommonResponse();
    commonResponse.id("someId").context("context");
    NotFoundException ex = new NotFoundException(commonResponse);
    RedirectAttributes ra = new RedirectAttributesModelMap();

    String template = controllerAdvice.handleNotFoundException(ex, ra, reqMock);

    assertThat(template).isEqualTo("redirect:/something-went-wrong");
    assertThat(ra.getFlashAttributes().get("errorCode")).isNull();

    verifyZeroInteractions(mockMessageSource);
  }

  @Test
  public void handleBadRequestException_whenKnownErrorCode_shouldRedirectToNotFound() {
    CommonResponse commonResponse = new CommonResponse();
    commonResponse
        .error(
            new Error()
                .message("know.error.code")
                .code(400)
                .reason("no reason")
                .errors(Lists.newArrayList(new ErrorErrors())))
        .id("someId")
        .context("context");

    BadRequestException ex = new BadRequestException(commonResponse);

    when(mockMessageSource.getMessage(eq("know.error.code"), isNull(), any())).thenReturn("bob");

    RedirectAttributes ra = new RedirectAttributesModelMap();
    String template = controllerAdvice.handleBadRequestException(ex, ra, reqMock);
    assertThat(template).isEqualTo("redirect:/bad-request");
    assertThat(ra.getFlashAttributes().get("errorCode")).isEqualTo("know.error.code");
  }
}
