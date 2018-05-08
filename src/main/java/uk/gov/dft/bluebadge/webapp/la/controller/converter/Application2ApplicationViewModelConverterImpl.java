package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Component
public class Application2ApplicationViewModelConverterImpl
    implements Converter<Application, ApplicationViewModel> {

  public ApplicationViewModel convert(Application source) {
    Assert.notNull(source, "Source cannot be null");
    return new ApplicationViewModel(source.getId(), source.getFullname());
  }
}
