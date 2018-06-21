package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ConsistentDate2Validator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented
public @interface ConsistentDate2 {

  String message() default "Enter a valid date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
