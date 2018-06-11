package uk.gov.dft.bluebadge.webapp.la.controller.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This is a cross-parameter constraint validation annotation to be use to match password and its password confirmation.
 */
@Documented
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface PasswordsMatch {

  String message() default "{error.form.setPassword.passwords.dont.match}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
