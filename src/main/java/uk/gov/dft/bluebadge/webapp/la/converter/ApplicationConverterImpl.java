package uk.gov.dft.bluebadge.webapp.la.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.controller.ApplicationCreateRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.ApplicationUpdateRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.model.Application;

@Component
public class ApplicationConverterImpl {
  public Application toApplication(ApplicationCreateRequest createRequest) {
    return new Application(createRequest.getId(), createRequest.getFullname());
  }

  public Application toApplication(ApplicationUpdateRequest updateRequest) {
    return new Application(updateRequest.getId(), updateRequest.getFullname());
  }

  public ApplicationViewModel toApplicationViewModel(Application application) {
    return new ApplicationViewModel(application.getId(), application.getFullname());
  }

  public List<ApplicationViewModel> toApplicationViewModel(List<Application> applications) {
    return applications
        .stream()
        .map(app -> toApplicationViewModel(app))
        .collect(Collectors.toList());
  }
}
