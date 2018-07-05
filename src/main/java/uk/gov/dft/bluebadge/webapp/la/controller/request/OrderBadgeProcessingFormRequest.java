package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInTheFutureDate;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInThePastDate;

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
    if (applicationDateDay == null && applicationDateMonth == null && applicationDateYear == null) {
      return null;
    }
    return applicationDateYear + "-" + applicationDateMonth + "-" + applicationDateDay;
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
    if (badgeStartDateDay == null && badgeStartDateMonth == null && badgeStartDateYear == null) {
      return null;
    }
    return badgeStartDateYear + "-" + badgeStartDateMonth + "-" + badgeStartDateDay;
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
    if (badgeExpiryDateDay == null || badgeExpiryDateMonth == null || badgeExpiryDateYear == null) {
      return false;
    }
    try {
      LocalDate expiryDate =
          LocalDate.of(badgeExpiryDateYear, badgeExpiryDateMonth, badgeExpiryDateDay);

      if (badgeStartDateDay == null || badgeStartDateMonth == null || badgeStartDateYear == null) {
        return true;
      }

      LocalDate startDate =
          LocalDate.of(badgeStartDateYear, badgeStartDateMonth, badgeStartDateDay);
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
}
