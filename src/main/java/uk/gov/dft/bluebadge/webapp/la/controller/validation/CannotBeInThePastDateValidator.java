package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CannotBeInThePastDateValidator
    implements ConstraintValidator<CannotBeInThePastDate, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    try {
      LocalDate date = DateFormatValidatorUtils.tryBuildDate(value);
      if (date == null) {
        return false;
      }
      return !date.isBefore(LocalDate.now());
    } catch (DateTimeException | NumberFormatException ex) {
      return false;
    }
  }
}
