package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.LocalDate;

public class DateFormatValidatorUtils {
  public static LocalDate tryBuildDate(String value) {
    String[] dateParts = value.split("-");
    if (dateParts != null && dateParts.length == 3) {
      Integer year = Integer.valueOf(dateParts[0]);
      Integer month = Integer.valueOf(dateParts[1]);
      Integer day = Integer.valueOf(dateParts[2]);
      LocalDate date = LocalDate.of(year, month, day);
      return date;
    }
    return null;
  }
  /*
  public static boolean isValid(String value) {
    // We cannot implement this with LocalDate.parse, because it does not throw exceptions for dates
    // like 1900-02-29, instead it changes that to 1900-02-28.
    try {
      tryBuildDate();
      String[] dateParts = value.split("/");
      if (dateParts != null && dateParts.length == 3) {
        Integer year = Integer.valueOf(dateParts[0]);
        Integer month = Integer.valueOf(dateParts[1]);
        Integer day = Integer.valueOf(dateParts[2]);
        LocalDate date = LocalDate.of(year, month, day);
        return date != null;
      }
      return false;
    } catch (DateTimeException | NumberFormatException ex) {
      return false;
    }
  }*/
}
