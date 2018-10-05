package uk.gov.dft.bluebadge.webapp.la.filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CookieFilterTest {

  @Test
  public void doFilter_ShouldSetCookieBanner_WhenCookieIsNotSet() throws ServletException, IOException {

    CookieFilter filter = new CookieFilter();

    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);

    FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
    FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);

    // mock the getRequestURI() response
    when(mockReq.getRequestURI()).thenReturn("/");
    when(mockReq.getCookies()).thenReturn(new Cookie[] {});

    filter.init(mockFilterConfig);
    filter.doFilter(mockReq, mockResp, mockFilterChain);
    filter.destroy();

    verify(mockReq).getCookies();
    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    verify(mockResp).addCookie(captor.capture());

    assertThat(captor.getValue().getName()).isEqualTo("cookie_banner_seen");
  }

  @Test
  public void doFilter_ShouldNotSetCookieBanner_WhenCookieIsSet() throws ServletException, IOException {

    CookieFilter filter = new CookieFilter();

    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);

    FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
    FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);

    // mock the getRequestURI() response
    when(mockReq.getRequestURI()).thenReturn("/");
    when(mockReq.getCookies()).thenReturn(new Cookie[] { new Cookie("cookie_banner_seen", "yes") });

    filter.init(mockFilterConfig);
    filter.doFilter(mockReq, mockResp, mockFilterChain);
    filter.destroy();

    verify(mockReq).getCookies();
    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    verify(mockResp, times(0)).addCookie(captor.capture());
  }
  
}
