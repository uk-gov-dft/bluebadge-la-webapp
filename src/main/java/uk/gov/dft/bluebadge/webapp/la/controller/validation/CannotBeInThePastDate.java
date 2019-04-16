package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CannotBeInThePastDateValidator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented
public @interface CannotBeInThePastDate {

  String message() default "{Date.cannotBeInThePast}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
