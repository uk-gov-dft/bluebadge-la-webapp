package uk.gov.dft.bluebadge.webapp.la.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralConnectorException;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Component
public class ApplicationConnectorImpl implements ApplicationConnector {

  public Optional<Application> findById(long id) {
    try {
      Application application =
          Application.ApplicationBuilder.anApplication()
              .withId(1L)
              .withFullname("my full name")
              .build();
      return Optional.ofNullable(application);
    } catch (Exception ex) {
      if (id != 10) {
        return Optional.empty();
      } else {
        throw new GeneralConnectorException("There was a general connector exception", ex);
      }
    }
  }

  public List<Application> findAll() {
    Application application1 =
        Application.ApplicationBuilder.anApplication()
            .withId(1L)
            .withFullname("my full name 1")
            .build();
    Application application2 =
        Application.ApplicationBuilder.anApplication()
            .withId(2L)
            .withFullname("my full name 2")
            .build();
    Application application3 =
        Application.ApplicationBuilder.anApplication()
            .withId(3L)
            .withFullname("my full name 3")
            .build();
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
