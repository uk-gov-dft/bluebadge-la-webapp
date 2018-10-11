package uk.gov.dft.bluebadge.webapp.la.utilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.Mockito;

public class CookieUtilsTest {

  @Test
  public void When_CookieBanner_IsSet() {

    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);

    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
    Cookie cookie = new Cookie("cookie_banner_seen", "yes");
    mockResp.addCookie(cookie);

    Cookie[] b = new Cookie[] {new Cookie("cookie_banner_seen", "yes")};
    when(mockReq.getCookies()).thenReturn(b);

    assertThat(new CookieUtils(mockReq).isCookieBannerSet()).isTrue();
  }

  @Test
  public void When_CookieBanner_IsNotSet() {

    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);

    when(mockReq.getCookies()).thenReturn(null);

    assertThat(new CookieUtils(mockReq).isCookieBannerSet()).isFalse();
  }
}
