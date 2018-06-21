package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDate2Validator implements ConstraintValidator<ConsistentDate, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      if (value == null) return false;
      String[] dateParts = value.split("/");
      if (dateParts == null || dateParts.length == 3) {
        Integer day = Integer.valueOf(dateParts[0]);
        Integer month = Integer.valueOf(dateParts[1]);
        Integer year = Integer.valueOf(dateParts[2]);

        LocalDate date = LocalDate.of(year, month, day);
        if (date.isBefore(LocalDate.now())) {
          return true;
        }
      }
      return false;
    } catch (DateTimeException dtex) {
      return false;
    } catch (NumberFormatException nfex) {
      return false;
    }
  }
}
