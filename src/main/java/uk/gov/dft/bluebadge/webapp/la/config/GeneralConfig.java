package uk.gov.dft.bluebadge.webapp.la.config;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class GeneralConfig {

  @Value("${blue-badge.thumbnail-height-px:300}")
  @NotNull
  private Integer thumbnailHeight;
}
