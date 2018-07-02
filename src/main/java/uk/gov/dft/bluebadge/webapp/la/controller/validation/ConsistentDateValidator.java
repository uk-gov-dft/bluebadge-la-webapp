package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDateValidator implements ConstraintValidator<ConsistentDate, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (value == null) {
      return true;
    }

    try {
      LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
      return date.isBefore(LocalDate.now());
    } catch (DateTimeException dtex) {
      return false;
    }
  }
}
