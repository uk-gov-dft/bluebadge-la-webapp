package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils.buildDateStringIfValidNullIfInvalid;
import static uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils.dateShouldBeNull;
import static uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils.isAnyDatePartMissing;
import static uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils.validateAndBuildLocalDateIfValid;

import java.time.LocalDate;
import org.junit.Test;

public class DateValidationUtilsTest {

  @Test
  public void buildDateStringIfValidNullIfInvalid_shouldReturnNull_ifAllPartsAreNull() {
    String result = buildDateStringIfValidNullIfInvalid(null, null, null);
    assertThat(result).isNull();
  }

  @Test
  public void buildDateStringIfValidNullIfInvalid_shouldReturnDateString_ifAllPartsArePresent() {
    String result = buildDateStringIfValidNullIfInvalid(17, 4, 2021);
    assertThat(result).isEqualTo("17/4/2021");
  }

  @Test
  public void dateShouldBeNull_shouldReturnFalse_WhenOneValueIsNull() {
    boolean result = dateShouldBeNull(null, 1, 2011);
    assertThat(result).isEqualTo(false);
  }

  @Test
  public void dateShouldBeNull_shouldReturnTrue_WhenAllValuesAreNull() {
    boolean result = dateShouldBeNull(null, null, null);
    assertThat(result).isEqualTo(true);
  }

  @Test
  public void isAnyDatePartMissing_shouldReturnTrue_WhenDayIsMissing() {
    boolean result = isAnyDatePartMissing(null, 1, 2010);
    assertThat(result).isEqualTo(true);
  }

  @Test
  public void isAnyDatePartMissing_shouldReturnTrue_WhenMonthIsMissing() {
    boolean result = isAnyDatePartMissing(1, null, 2010);
    assertThat(result).isEqualTo(true);
  }

  @Test
  public void isAnyDatePartMissing_shouldReturnTrue_WhenYearIsMissing() {
    boolean result = isAnyDatePartMissing(1, 1, null);
    assertThat(result).isEqualTo(true);
  }

  @Test
  public void isAnyDatePartMissing_shouldReturnTrue_WhenAllDatePartsAreMissing() {
    boolean result = isAnyDatePartMissing(null, null, null);
    assertThat(result).isEqualTo(true);
  }

  @Test
  public void isAnyDatePartMissing_shouldReturnFalse_WhenAllDatePartsArePresent() {
    boolean result = isAnyDatePartMissing(1, 5, 2020);
    assertThat(result).isEqualTo(false);
  }

  @Test
  public void validateAndBuildLocalDateIfValid_ShouldReturnLocalDate() {
    LocalDate date = validateAndBuildLocalDateIfValid(4, 5, 2022);
    assertThat(date).isEqualTo(LocalDate.of(2022, 5, 4));
  }
}
