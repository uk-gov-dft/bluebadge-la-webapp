package uk.gov.dft.bluebadge.webapp.la.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.ApplicationConnector;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralConnectorException;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private ApplicationConnector connector;

  @Autowired
  public ApplicationServiceImpl(ApplicationConnector connector) {
    this.connector = connector;
  }

  public Optional<Application> findById(Long id) {
    try {
      return connector.findById(id);
    } catch (GeneralConnectorException ex) {
      throw new GeneralServiceException("There was a general service exception", ex);
    }
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
