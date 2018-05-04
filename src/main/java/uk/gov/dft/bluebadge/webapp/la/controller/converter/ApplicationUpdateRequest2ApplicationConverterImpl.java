package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationUpdateRequest;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Component
public class ApplicationUpdateRequest2ApplicationConverterImpl
    implements Converter<ApplicationUpdateRequest, Application> {

  public Application convert(ApplicationUpdateRequest source) {
    Assert.notNull(source, "Source cannot be null");
    return new Application(source.getId(), source.getFullname());
  }
}
