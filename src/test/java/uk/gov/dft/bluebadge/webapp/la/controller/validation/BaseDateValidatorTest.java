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
    String value = "29/2/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongDateInMonthsWith30Days() {
    String value = "31/6/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongDayOfMonth() {
    String value = "32/1/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_WhenWrongMonth() {
    String value = "15/15/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_When0Day() {
    String value = "0/15/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnFalse_When0Month() {
    String value = "15/0/1900";
    Boolean result = validator.isValid(value, context);
    assertFalse(value + " should be invalid (false)", result);
  }

  @Test
  public void isValid_shouldReturnTrue_WhenDateIsValid() {
    String value = "25/4/1900";
    Boolean result = validator.isValid(value, context);
    assertTrue(value + " should be valid (true)", result);
  }

  @Test
  public void isValid_shouldReturnTrue_When29thDayOfFebruaryInLeapYear() {
    String value = "29/02/2016";
    Boolean result = validator.isValid(value, context);
    assertTrue(value + " should be valid (true)", result);
  }
}
