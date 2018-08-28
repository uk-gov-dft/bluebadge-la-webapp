package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {
  public OffsetDateTime now() {
    return OffsetDateTime.now();
  }
}
