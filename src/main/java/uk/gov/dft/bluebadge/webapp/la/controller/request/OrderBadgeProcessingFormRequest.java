package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ConsistentDate;

@Data
public class OrderBadgeProcessingFormRequest {
  private Integer applicationDateDay;
  private Integer applicationDateMonth;
  private Integer applicationDataYear;

  @NotBlank(message = "{NotNull.badge.applicationDate}")
  @ConsistentDate(message = "{Pattern.badge.applicationDate}")
  public String getApplicationDate() {
    if (applicationDateDay == null && applicationDateMonth == null && applicationDataYear == null) {
      return null;
    }
    return LocalDate.of(applicationDateDay, applicationDateMonth, applicationDataYear)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  @NotBlank(message = "{NotNull.badge.applicationChannel}")
  private String applicationChannel;

  private String localAuthorityReferenceNumber;

  private Integer badgeStartDateDay;
  private Integer badgeStartDateMonth;
  private Integer badgeStartDateYear;

  @NotBlank(message = "{NotNull.badge.startDate}")
  @ConsistentDate(message = "{Pattern.badge.startDate}")
  public String getStartDate() {
    if (badgeStartDateDay == null && badgeStartDateMonth == null && badgeStartDateYear == null) {
      return null;
    }
    return LocalDate.of(badgeStartDateDay, badgeStartDateMonth, badgeStartDateYear)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  @NotBlank(message = "{NotNull.badge.expiryDate}")
  @ConsistentDate(message = "{Pattern.badge.expiryDate}")
  public String getExpiryDate() {
    if (badgeExpiryDateDay == null && badgeExpiryDateMonth == null && badgeExpiryDateYear == null) {
      return null;
    }
    return LocalDate.of(badgeExpiryDateDay, badgeExpiryDateMonth, badgeExpiryDateYear)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  private Integer badgeExpiryDateDay;
  private Integer badgeExpiryDateMonth;
  private Integer badgeExpiryDateYear;

  @NotBlank(message = "{NotNull.badge.deliverTo}")
  private String deliverTo;

  @NotBlank(message = "{NotNull.badge.deliveryOptions}")
  private String deliveryOptions;
}
