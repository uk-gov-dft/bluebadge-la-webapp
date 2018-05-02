package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationCreateRequest;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Component
public class ApplicationCreateRequest2ApplicationConverterImpl
    implements Converter<ApplicationCreateRequest, Application> {

  @Override
  public Application convert(ApplicationCreateRequest source) {
    return new Application(source.getId(), source.getFullname());
  }
}
