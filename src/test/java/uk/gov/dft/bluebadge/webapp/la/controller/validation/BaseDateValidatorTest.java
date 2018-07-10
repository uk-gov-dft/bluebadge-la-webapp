package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class BaseDateValidatorTest {

  protected ConstraintValidator validator;

  @Mock private ConstraintValidatorContext context;

  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongDayOfFebruary() {
    String value = "1900-2-29";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongDateInMonthsWith30Days() {
    String value = "1900-6-31";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongDayOfMonth() {
    String value = "1900-1-32";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongMonth() {
    String value = "1900-15-15";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_When0Day() {
    String value = "1900-15-0";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_When0Month() {
    String value = "1900-0-15";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnTrue_WhenDateIsValid() {
    String value = "1900-4-25";
    Boolean result = validator.isValid(value, context);
    assertTrue(value + " should be valid (true)", result);
  }

  @Test
  public void isValid_shouldReturnTrue_When29thDayOfFebruaryInLeapYear() {
    String value = "2016-02-29";
    Boolean result = validator.isValid(value, context);
    assertTrue(value + " should be valid (true)", result);
  }
}
