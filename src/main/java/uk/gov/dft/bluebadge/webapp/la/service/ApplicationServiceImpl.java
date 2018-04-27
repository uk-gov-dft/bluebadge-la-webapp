package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.connector.ApplicationConnector;
import uk.gov.dft.bluebadge.webapp.la.model.Application;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired ApplicationConnector connector;

  public Optional<Application> findById(Long id) {
    return connector.findById(id);
  }

  @Override
  public List<Application> findAll() {
    return connector.findAll();
  }

  @Override
  public int create(Application application) {
    return connector.create(application);
  }

  @Override
  public int update(Application application) {
    return connector.update(application);
  }

  @Override
  public int delete(Long id) {
    return connector.delete(id);
  }
}
