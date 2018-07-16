package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DateValidationUtils {
  private DateValidationUtils() {}

  public static String buildDateStringIfValidNullIfInvalid(
      Integer day, Integer month, Integer year) {
    if (dateShouldBeNull(day, month, year)) {
      return null;
    }
    return day + "/" + month + "/" + year;
  }

  /*
   * If any part of the dob: dobDay, dobMonth, dobYear is null, the whole (dob) should be null, so that we can trigger the validation
   * @NotNull or @NotEmpty on method getDob().
   */
  public static boolean dateShouldBeNull(Integer day, Integer month, Integer year) {
    return day == null && month == null && year == null;
  }

  public static boolean isAnyDatePartMissing(Integer day, Integer month, Integer year) {
    return day == null || month == null || year == null;
  }

  /**
   * If date parts are valid and consistent it will return a LocalDate based on the given input. If
   * date parts are invalid or inconsistent among each other it will throw a DateTimeException
   *
   * @param day
   * @param month
   * @param year
   * @return If date parts are valid, it returns a local date.
   * @throws DateTimeException in case the date parts are invalid, i.e.: days over 31 or month over
   *     12 or inconsistent, i.e. 29/2/1900 is invalid, because in year 1900, february only had 28
   *     days.
   */
  public static LocalDate validateAndBuildLocalDateIfValid(
      Integer day, Integer month, Integer year) {
    return LocalDate.of(year, month, day);
  }
}
