package uk.gov.dft.bluebadge.webapp.la.controller.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.SetPasswordFormRequest;

/**
 * Constraint validator that implements PasswordMatch constraint annotation.
 */
public class PasswordsMatchValidator
    implements ConstraintValidator<PasswordsMatch, SetPasswordFormRequest> {
  @Override
  public boolean isValid(
      SetPasswordFormRequest form, ConstraintValidatorContext constraintValidatorContext) {
    return StringUtils.equals(form.getPassword(), form.getPasswordConfirm());
  }
}
