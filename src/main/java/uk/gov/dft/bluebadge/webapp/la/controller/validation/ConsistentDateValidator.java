package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgePersonDetailsFormRequest;

public class ConsistentDateValidator
    implements ConstraintValidator<ConsistentDate, OrderABadgePersonDetailsFormRequest> {

  @Override
  public boolean isValid(
      OrderABadgePersonDetailsFormRequest value, ConstraintValidatorContext context) {
    try {
      if (value.getDobYear() != null && value.getDobMonth() != null && value.getDobYear() != null) {
        LocalDate dob = LocalDate.of(value.getDobYear(), value.getDobMonth(), value.getDobYear());
        if (dob.isBefore(LocalDate.now())) {
          return true;
        }
      }
      return false;
    } catch (DateTimeException dtex) {
      return false;
    }
  }
}
