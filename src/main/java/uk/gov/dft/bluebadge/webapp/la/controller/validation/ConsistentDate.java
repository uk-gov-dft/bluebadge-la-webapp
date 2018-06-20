package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ConsistentDateValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ConsistentDate {

  String message() default "End date must be after begin date and both must be in the future";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
