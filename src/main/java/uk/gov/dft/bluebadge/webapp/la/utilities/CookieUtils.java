package uk.gov.dft.bluebadge.webapp.la.utilities;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieUtils {

  private HttpServletRequest req;
  public static final String COOKIE_BANNER_KEY = "cookie_banner_seen";

  @Autowired
  public CookieUtils(HttpServletRequest req) {
    this.req = req;
  }

  public Boolean isCookieBannerSet() {
    return WebUtils.getCookie(req, COOKIE_BANNER_KEY) != null;
  }
}
