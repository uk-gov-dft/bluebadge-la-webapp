package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {
  @Value("${blue-badge.client-time-zone}")
  private String clientTimeZone;

  public ZoneId clientZoneId() {
    return ZoneId.of(clientTimeZone);
  }
}
