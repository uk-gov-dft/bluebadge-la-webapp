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
  private Integer applicationDateYear;

  @NotBlank(message = "{NotNull.badge.applicationDate}")
  @ConsistentDate(message = "{Pattern.badge.applicationDate}")
  public String getApplicationDate() {
    if (applicationDateDay == null && applicationDateMonth == null && applicationDateYear == null) {
      return null;
    }
    return LocalDate.of(applicationDateYear, applicationDateMonth, applicationDateDay)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  @NotBlank(message = "{NotNull.badge.applicationChannel}")
  private String applicationChannel;

  private String localAuthorityReferenceNumber;

  private Integer badgeStartDateDay;
  private Integer badgeStartDateMonth;
  private Integer badgeStartDateYear;
  private String badgeStartDate;

  @NotBlank(message = "{NotNull.badge.startDate}")
  @ConsistentDate(message = "{Pattern.badge.startDate}")
  public String getStartDate() {
    if (badgeStartDateDay == null && badgeStartDateMonth == null && badgeStartDateYear == null) {
      return null;
    }
    return LocalDate.of(badgeStartDateYear, badgeStartDateMonth, badgeStartDateDay)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  @NotBlank(message = "{NotNull.badge.expiryDate}")
  @ConsistentDate(message = "{Pattern.badge.expiryDate}")
  public String getExpiryDate() {
    if (badgeExpiryDateDay == null && badgeExpiryDateMonth == null && badgeExpiryDateYear == null) {
      return null;
    }
    return LocalDate.of(badgeExpiryDateYear, badgeExpiryDateMonth, badgeExpiryDateDay)
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  private Integer badgeExpiryDateDay;
  private Integer badgeExpiryDateMonth;
  private Integer badgeExpiryDateYear;
  private String badgeExpiryDate;

  @NotBlank(message = "{NotNull.badge.deliverTo}")
  private String deliverTo;

  @NotBlank(message = "{NotNull.badge.deliveryOptions}")
  private String deliveryOptions;
}
