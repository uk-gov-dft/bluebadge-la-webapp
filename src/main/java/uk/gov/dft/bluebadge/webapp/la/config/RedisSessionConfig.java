package uk.gov.dft.bluebadge.webapp.la.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
  RedisSessionConfig() {}

  @Bean
  public static ConfigureRedisAction configureRedisAction() {
    return ConfigureRedisAction.NO_OP;
  }
}
