package uk.gov.service.bluebadge.test.acceptance.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class LocalDateGenerator {

  private long minDay;
  private long maxDay;

  public LocalDateGenerator() {
    minDay = LocalDate.of(1900, 1, 1).toEpochDay();
    maxDay = LocalDate.now().minusDays(1).toEpochDay();
  }

  public LocalDate get_local_date() {
    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
    return randomDate;
  }
}
