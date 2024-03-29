package uk.gov.dft.bluebadge.webapp.la.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
      throws IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {
      logger.info(
          "User '{}' attempted to access the protected URL: {}",
          auth.getName(),
          request.getRequestURI());
    }

    response.sendRedirect(request.getContextPath() + "/something-went-wrong");
  }
}
