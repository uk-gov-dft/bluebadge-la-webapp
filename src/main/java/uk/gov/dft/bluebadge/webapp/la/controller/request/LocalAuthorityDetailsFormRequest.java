package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class LocalAuthorityDetailsFormRequest implements Serializable {

  @URL(message = "{URL.localAuthority.differentServiceSignpostUrl}")
  private String differentServiceSignpostUrl;
}
