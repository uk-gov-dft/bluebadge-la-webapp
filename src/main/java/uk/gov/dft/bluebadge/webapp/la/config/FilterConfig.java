package uk.gov.dft.bluebadge.webapp.la.config;

import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.INCLUDE;
import static javax.servlet.DispatcherType.REQUEST;

import java.util.Map;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import uk.gov.dft.bluebadge.common.esapi.EsapiFilter;
import uk.gov.dft.bluebadge.common.logging.ExceptionLoggingFilter;
import uk.gov.dft.bluebadge.common.logging.JwtMdcFilter;

@Configuration
public class FilterConfig {

  @Bean
  @Order(500)
  public EsapiFilter getEsapiFilter() {
    return new EsapiFilter();
  }

  @Bean
  public JwtMdcFilter getJwtMdcFilter() {
    return new JwtMdcFilter();
  }

  @Bean
  public FilterRegistrationBean<ExceptionLoggingFilter> exceptionLoggingFilter(
      Map<ExceptionLoggingFilter.ClassMessageKey, String> exceptionLoggingFilterConfig) {
    ExceptionLoggingFilter filter = new ExceptionLoggingFilter(exceptionLoggingFilterConfig);
    FilterRegistrationBean<ExceptionLoggingFilter> filterRegistrationBean =
        new FilterRegistrationBean<>(filter);
    filterRegistrationBean.setName("BBExceptionLoggingFilter");
    filterRegistrationBean.setDispatcherTypes(ASYNC, ERROR, FORWARD, INCLUDE, REQUEST);
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 6);
    return filterRegistrationBean;
  }
}
