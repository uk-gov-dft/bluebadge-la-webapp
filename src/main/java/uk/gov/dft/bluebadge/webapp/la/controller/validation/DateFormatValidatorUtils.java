package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.LocalDate;

public class DateFormatValidatorUtils {
  private DateFormatValidatorUtils() {}

  public static LocalDate tryBuildDate(String value) {
    String[] dateParts = value.split("/");
    if (dateParts != null && dateParts.length == 3) {
      Integer day = Integer.valueOf(dateParts[0]);
      Integer month = Integer.valueOf(dateParts[1]);
      Integer year = Integer.valueOf(dateParts[2]);
      return LocalDate.of(year, month, day);
    }
    return null;
  }
}
