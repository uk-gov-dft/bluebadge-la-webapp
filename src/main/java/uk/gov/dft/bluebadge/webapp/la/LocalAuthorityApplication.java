package uk.gov.dft.bluebadge.webapp.la;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwagger2
public class LocalAuthorityApplication {

  @Bean
  public MessageSource messageSource() {
    final ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setCacheSeconds(0);
    return messageSource;
  }

  public static void main(String[] args) {
    SpringApplication.run(LocalAuthorityApplication.class, args);
  }
}
