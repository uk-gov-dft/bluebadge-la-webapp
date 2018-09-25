package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.client.common.ClientApiException;

public class ErrorControllerAdviceTest {

  @Mock private RedirectAttributes redirectAttributesMock;
  @Mock private HttpServletRequest reqMock;
  @Mock private ObjectMapper objectMapperMock;

  @Mock ObjectWriter writerMock;

  private ErrorControllerAdvice controllerAdvice;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controllerAdvice = new ErrorControllerAdvice(objectMapperMock);
  }

  @Test
  public void handleException_shouldReturnRedirectToErrorTemplateAndPopulateRedirectAttributes() {
    Exception ex = new Exception("my error message");

    String template = controllerAdvice.handleException(ex, reqMock, redirectAttributesMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
    verify(redirectAttributesMock, times(1)).addFlashAttribute("exception", ex);
  }

  @Test
  public void handleHttpException_shouldReturnRedirectToErrorTemplateAndPopulateRedirectAttributes()
      throws JsonProcessingException {
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

    when(objectMapperMock.writerWithDefaultPrettyPrinter()).thenReturn(writerMock);
    when(writerMock.writeValueAsString(ex.getCommonResponse()))
        .thenReturn("some api client error message");

    String template =
        controllerAdvice.handleClientApiException(ex, reqMock, redirectAttributesMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
    verify(redirectAttributesMock)
        .addFlashAttribute("commonResponse", "some api client error message");
    verify(redirectAttributesMock, times(1)).addFlashAttribute("exception", ex);
  }
}
