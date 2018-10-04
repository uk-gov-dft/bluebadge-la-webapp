package uk.gov.dft.bluebadge.webapp.la.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.utilities.CookieUtils;

@Component
@Order(999)
public class CookieFilter implements Filter {

  public static final String COOKIE_BANNER_VALUE = "yes";
  public static final int SECONDS_IN_MONTH = 2592000;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    if (!CookieUtils.isCookieBannerSet(req)) {
      Cookie newCookie = new Cookie(CookieUtils.COOKIE_BANNER_KEY, COOKIE_BANNER_VALUE);
      newCookie.setSecure(true);
      newCookie.setMaxAge(SECONDS_IN_MONTH);
      res.addCookie(newCookie);
    }

    chain.doFilter(request, res);
  }

  @Override
  public void destroy() {}
}
