package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DateFormatValidatorTest {

  @Test
  public void tryBuildDate_ShouldReturnNull_WhenValueIsNull() {
    LocalDate date = DateFormatValidatorUtils.tryBuildDate(null);
    assertThat(date).isNull();
  }

  @Test
  public void tryBuildDate_ShouldReturnNull_WhenValueIsBadFormat() {
    LocalDate date = DateFormatValidatorUtils.tryBuildDate("1/2020");
    assertThat(date).isNull();
  }

  @Test
  public void tryBuildDate_ShouldReturnLocalDate_WhenValueHas3ValidDateParts() {
    LocalDate date = DateFormatValidatorUtils.tryBuildDate("1/12/2010");
    assertThat(date).isEqualTo(LocalDate.of(2010, 12, 1));
  }

  @Test(expected = NumberFormatException.class)
  public void tryBuildDate_ShouldThrowNumberForamtException_WhenValueHasSomeWrongDatePart() {
    DateFormatValidatorUtils.tryBuildDate("null/12/2010");
  }

  @Test(expected = DateTimeException.class)
  public void tryBuildDate_ShouldThrowDateTimeException_WhenValueRepresentsANonExistantDay() {
    DateFormatValidatorUtils.tryBuildDate("30/2/2010");
  }

}
