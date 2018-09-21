package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ErrorControllerAdviceTest {

  @Mock private RedirectAttributes redirectAttributesMock;

  private ErrorControllerAdvice controllerAdvice;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controllerAdvice = new ErrorControllerAdvice();
  }

  @Test
  public void handleException_shouldReturnRedirectToErrorTemplateAndPopulateRedirectAttributes() {
    Exception ex = new Exception("my error message");

    String template = controllerAdvice.handleException(ex, redirectAttributesMock);
    assertThat(template).isEqualTo("redirect:/something-went-wrong");
    verify(redirectAttributesMock, times(1)).addFlashAttribute("exception", ex);
  }
}
