package uk.gov.dft.bluebadge.webapp.la.utilities;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieUtils {

  private HttpServletRequest req;
  public static final String COOKIE_BANNER_KEY = "cookie_banner_seen";

  @Autowired
  public CookieUtils(HttpServletRequest req) {
    this.req = req;
  }

  public Boolean isCookieBannerSet() {
    return isCookieBannerSet(req);
  }

  public static Boolean isCookieBannerSet(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    Stream<Cookie> stream = Objects.nonNull(cookies) ? Arrays.stream(cookies) : Stream.empty();
    return stream.filter(c -> COOKIE_BANNER_KEY.equals(c.getName())).findFirst().isPresent();
  }
}
