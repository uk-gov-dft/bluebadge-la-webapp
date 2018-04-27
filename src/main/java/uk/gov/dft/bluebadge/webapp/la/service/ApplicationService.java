package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.webapp.la.model.Application;

public interface ApplicationService {
  Optional<Application> findById(Long id);

  List<Application> findAll();

  int create(Application application);

  int update(Application application);

  int delete(Long id);
}
