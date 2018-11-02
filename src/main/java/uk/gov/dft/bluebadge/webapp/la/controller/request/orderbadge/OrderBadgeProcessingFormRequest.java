package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInTheFutureDate;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInThePastDate;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;

@Data
@Builder
public class OrderBadgeProcessingFormRequest implements Serializable {
  private Integer applicationDateDay;
  private Integer applicationDateMonth;
  private Integer applicationDateYear;

  // This is just a trick to implement date format validation when date is implement with 3 input
  // fields (day, month, year)
  @NotBlank(message = "{NotNull.badge.applicationDate}")
  @CannotBeInTheFutureDate(message = "{Pattern.badge.applicationDate}")
  public String getApplicationDate() {
    return DateValidationUtils.buildDateStringIfValidNullIfInvalid(
        applicationDateDay, applicationDateMonth, applicationDateYear);
  }

  @NotBlank(message = "{NotNull.badge.applicationChannel}")
  private String applicationChannel;

  private String localAuthorityReferenceNumber;

  private Integer badgeStartDateDay;
  private Integer badgeStartDateMonth;
  private Integer badgeStartDateYear;

  // This is just a trick to implement date format validation when date is implement with 3 input
  // fields (day, month, year)
  @NotBlank(message = "{NotNull.badge.startDate}")
  @CannotBeInThePastDate(message = "{Pattern.badge.startDate}")
  public String getBadgeStartDate() {
    return DateValidationUtils.buildDateStringIfValidNullIfInvalid(
        badgeStartDateDay, badgeStartDateMonth, badgeStartDateYear);
  }

  private Integer badgeExpiryDateDay;
  private Integer badgeExpiryDateMonth;
  private Integer badgeExpiryDateYear;

  // In practice this is validation code which should not be here. This is just a trick to implement
  // cross-parameter validation in a way
  // that is compatible with the templates and its fragments from one side and the the way we handle
  // displaying field error messages in the
  // template from the other one (which is mostly based on standard bean validation).
  // Arguably, this is the least ugly (ugliness is just constrained to this method, not spread out
  // all over the code)
  // of all alternatives.
  @AssertTrue(message = "{Pattern.badge.expiryDate}")
  public boolean isBadgeExpiryDateValid() {
    try {
      if (DateValidationUtils.isAnyDatePartMissing(
          badgeExpiryDateDay, badgeExpiryDateMonth, badgeExpiryDateYear)) {
        return false;
      }
      LocalDate expiryDate =
          DateValidationUtils.validateAndBuildLocalDateIfValid(
              badgeExpiryDateDay, badgeExpiryDateMonth, badgeExpiryDateYear);

      if (DateValidationUtils.isAnyDatePartMissing(
          badgeStartDateDay, badgeStartDateMonth, badgeStartDateYear)) {
        return true;
      }
      LocalDate startDate =
          DateValidationUtils.validateAndBuildLocalDateIfValid(
              badgeStartDateDay, badgeStartDateMonth, badgeStartDateYear);

      if (expiryDate.isBefore(startDate)) {
        return false;
      }
      LocalDate startDatePlus3Years = startDate.plusYears(3);
      return expiryDate.isEqual(startDatePlus3Years) || expiryDate.isBefore(startDatePlus3Years);
    } catch (DateTimeException dtex) {
      return false;
    }
  }

  @NotBlank(message = "{NotNull.badge.deliverTo}")
  private String deliverTo;

  @NotBlank(message = "{NotNull.badge.deliveryOptions}")
  private String deliveryOptions;

  @NotNull(message = "{NotNull.badge.numberOfBadges}")
  @Min(value = 1, message = "{Pattern.badge.numberOfBadges}")
  @Max(value = 999, message = "{Pattern.badge.numberOfBadges}")
  private Integer numberOfBadges;
}
