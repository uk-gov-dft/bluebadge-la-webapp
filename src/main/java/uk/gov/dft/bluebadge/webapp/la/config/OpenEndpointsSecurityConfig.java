package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(51)
public class OpenEndpointsSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers()
        .antMatchers("/set***REMOVED***/*")
        .antMatchers("/cookies")
        .antMatchers("/privacy-notice")
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }
}
