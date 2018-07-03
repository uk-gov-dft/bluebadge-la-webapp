package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
      LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("y-M-d"));
      return date.isAfter(LocalDate.now());
    } catch (DateTimeException dtex) {
      return false;
    }
  }
}
