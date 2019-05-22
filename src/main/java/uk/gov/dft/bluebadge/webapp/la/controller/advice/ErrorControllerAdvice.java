package uk.gov.dft.bluebadge.webapp.la.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.ClientApiException;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.ErrorHandlerController;
import uk.gov.dft.bluebadge.webapp.la.controller.exceptions.InvalidSessionException;

@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

  private final ObjectMapper objectMapper;
  private final MessageSource messageSource;
  private static final String REDIRECT_URL = "redirect:" + ErrorHandlerController.ERROR_500_URL;
  private static final String NOT_FOUND_URL = "redirect:" + ErrorHandlerController.ERROR_404_URL;
  private static final String BAD_REQUEST_URL = "redirect:" + ErrorHandlerController.ERROR_400_URL;

  public ErrorControllerAdvice(ObjectMapper objectMapper, MessageSource messageSource) {
    this.objectMapper = objectMapper;
    this.messageSource = messageSource;
  }

  @ExceptionHandler(NotFoundException.class)
  public String handleNotFoundException(
      NotFoundException ex, RedirectAttributes redirectAttributes, HttpServletRequest req) {
    return handleClientApiException(ex, redirectAttributes, req, NOT_FOUND_URL);
  }

  @ExceptionHandler(BadRequestException.class)
  public String handleBadRequestException(
      BadRequestException ex, RedirectAttributes redirectAttributes, HttpServletRequest req) {
    return handleClientApiException(ex, redirectAttributes, req, BAD_REQUEST_URL);
  }

  /**
   * Extract the error code from the error and check if there is a message for that code. If there
   * is, use this to show a 'nice' error page. Else revert to the something went wrong page.
   */
  private String handleClientApiException(
      ClientApiException ex,
      RedirectAttributes redirectAttributes,
      HttpServletRequest req,
      String redirectUrl) {
    log.error(
        "Request: [{}] {} raised {}.", req.getMethod(), req.getRequestURL(), ex.toString(), ex);

    // If the error code is within the messages, then can show a 'nice' error
    if (null != ex.getCommonResponse() && null != ex.getCommonResponse().getError()) {
      String errorMessage = ex.getCommonResponse().getError().getMessage();
      if (null != errorMessage) {
        try {
          messageSource.getMessage(errorMessage, null, Locale.ENGLISH);
          redirectAttributes.addFlashAttribute("errorCode", errorMessage);
          return redirectUrl;
        } catch (NoSuchMessageException e) {
          // No 'nice' error message. Revert to the default
        }
      }
    }

    return handleClientApiException(ex, req);
  }

  @ExceptionHandler(ClientApiException.class)
  public String handleClientApiException(ClientApiException ex, HttpServletRequest req) {

    log.error(
        "Request: [{}] {} raised {}.", req.getMethod(), req.getRequestURL(), ex.toString(), ex);

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
