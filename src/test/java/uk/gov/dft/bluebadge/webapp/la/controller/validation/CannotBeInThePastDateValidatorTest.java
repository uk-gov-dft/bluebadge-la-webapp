package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidatorContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class CannotBeInThePastDateValidatorTest extends BaseDateValidatorTest {

  @Mock private ConstraintValidatorContext context;

  @Before
  public void setUp() {
    super.setUp();
    validator = new CannotBeInTheFutureDateValidator();
  }

  @Test
  public void isValid_shouldReturnTrue_WhenDateInThePast() {
    String value = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("d/M/y"));
    Boolean result = validator.isValid(value, context);
    assertTrue(value + " should be valid (true)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenDateInTheFuture() {
    String value = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("d/M/y"));
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }
}
