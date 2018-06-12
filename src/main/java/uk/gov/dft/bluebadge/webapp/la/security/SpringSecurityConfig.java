package uk.gov.dft.bluebadge.webapp.la.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableOAuth2Sso
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private AccessDeniedHandler accessDeniedHandler;

  // roles admin allow to access /admin/**
  // roles user allow to access /user/**
  // custom 403 access denied handler
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/**")
        .fullyAuthenticated()
        .and()
        .formLogin()
        .permitAll()
    //        .and()
    //          .exceptionHandling()
    //          .accessDeniedHandler(accessDeniedHandler)
    ;
  }

  // create two users, admin and user
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication()
        .withUser("user")
        . ***REMOVED***)
        .roles("USER")
        .and()
        .withUser("admin")
        . ***REMOVED***)
        .roles("ADMIN");
  }

  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
}
