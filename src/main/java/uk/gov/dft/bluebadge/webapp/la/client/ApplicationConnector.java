package uk.gov.dft.bluebadge.webapp.la.client;

import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

public interface ApplicationConnector {

  Optional<Application> findById(long id);

  List<Application> findAll();

  int create(Application application);

  int update(Application application);

  int delete(Long id);
}
