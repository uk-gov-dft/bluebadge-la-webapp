package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.gov.dft.bluebadge.webapp.la.client.common.ClientApiException;
import uk.gov.dft.bluebadge.webapp.la.controller.ErrorHandlerController;
import uk.gov.dft.bluebadge.webapp.la.controller.exceptions.InvalidSessionException;

@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

  private final ObjectMapper objectMapper;
  private static final String REDIRECT_URL = "redirect:" + ErrorHandlerController.ERROR_500_URL;

  public ErrorControllerAdvice(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ExceptionHandler(ClientApiException.class)
  public String handleClientApiException(ClientApiException ex, HttpServletRequest req) {

    log.error("Request: {} raised {}.", req.getRequestURL(), ex.toString(), ex);

    try {
      String commonResponse =
          objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ex.getCommonResponse());
      log.warn("Exception common response:{}", commonResponse);
    } catch (JsonProcessingException e) {
      log.warn("Failed to convert common response from exception.", e);
    }

    return REDIRECT_URL;
  }

  @ExceptionHandler(InvalidSessionException.class)
  public String handleException(InvalidSessionException ex, HttpServletRequest req) {
    String redirectUrl = ex.getRedirectUrl().orElse(ErrorHandlerController.START_AGAIN_URL);
    log.info("Request: {}, had invalid session: {}.", req.getRequestURL(), ex.getMessage());
    return "redirect:" + redirectUrl;
  }

  @ExceptionHandler(Exception.class)
  public String handleException(Exception ex, HttpServletRequest req) {
    log.error("Request: {} raised {}.", req.getRequestURL(), ex.toString(), ex);
    return REDIRECT_URL;
  }
}
