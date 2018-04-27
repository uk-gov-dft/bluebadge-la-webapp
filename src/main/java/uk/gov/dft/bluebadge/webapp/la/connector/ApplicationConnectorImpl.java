package uk.gov.dft.bluebadge.webapp.la.connector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.exception.ConnectorException;
import uk.gov.dft.bluebadge.webapp.la.model.Application;

@Component
public class ApplicationConnectorImpl implements ApplicationConnector {

  public Optional<Application> findById(long id) {
    try {
      return Optional.ofNullable(new Application(1L, "my fullname"));
    } catch (ConnectorException ex) {
      return Optional.empty();
    }
  }

  public List<Application> findAll() {
    Application application1 = new Application(1L, "my full name 1");
    Application application2 = new Application(2L, "my full name 2");
    Application application3 = new Application(3L, "my full name 2");
    List<Application> applications = Arrays.asList(application1, application2, application3);
    return applications;
  }

  public int create(Application application) {
    return 1;
  }

  public int update(Application application) {
    return 1;
  }

  public int delete(Long id) {
    return 1;
  }
}
