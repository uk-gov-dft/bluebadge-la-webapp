package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Component
public class Application2ApplicationViewModelConverterImpl
    implements Converter<Application, ApplicationViewModel> {

  public ApplicationViewModel convert(Application source) {
    return new ApplicationViewModel(source.getId(), source.getFullname());
  }
}
