package uk.gov.dft.bluebadge.webapp.la.connector;

import java.util.List;
import java.util.Optional;
import uk.gov.dft.bluebadge.webapp.la.model.Application;

public interface ApplicationConnector {

  Optional<Application> findById(long id);

  List<Application> findAll();

  int create(Application application);

  int update(Application application);

  int delete(Long id);
}
