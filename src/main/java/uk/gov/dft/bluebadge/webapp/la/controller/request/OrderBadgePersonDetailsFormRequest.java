package uk.gov.dft.bluebadge.webapp.la.controller.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInTheFutureDate;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ValidationPatterns;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
public class OrderBadgePersonDetailsFormRequest implements Serializable {

  @NotBlank(message = "{NotNull.user.name}")
  @Pattern(regexp = ValidationPatterns.NAME, message = "{Pattern.user.name}")
  @Size(max = 100)
  private String name;

  @NotBlank(message = "{NotNull.badge.gender}")
  private String gender;

  private Integer dobDay;

  private Integer dobMonth;

  private Integer dobYear;

  private String dob;

  @NotBlank(message = "{NotNull.badge.dob}")
  @CannotBeInTheFutureDate(message = "{Pattern.badge.dob}")
  public String getDob() {
    return DateValidationUtils.buildDateStringIfValidNullIfInvalid(dobDay, dobMonth, dobYear);
  }

  private MultipartFile photo;

  @Pattern(regexp = ValidationPatterns.NINO_CASE_INSENSITIVE, message = "{Pattern.badge.nino}")
  private String nino;

  @NotBlank(message = "{NotNull.badge.buildingAndStreet}")
  private String buildingAndStreet;

  private String optionalAddressField;

  @NotBlank(message = "{NotNull.badge.townOrCity}")
  private String townOrCity;

  @NotBlank(message = "{NotNull.badge.postcode}")
  @Pattern(
    regexp = ValidationPatterns.POSTCODE_CASE_INSENSITIVE,
    message = "{Pattern.badge.postcode}"
  )
  private String postcode;

  @Pattern(regexp = ValidationPatterns.NAME, message = "{Pattern.user.name}")
  private String contactDetailsName;

  @NotBlank(message = "{NotNull.badge.contactDetailsContactNumber}")
  @Pattern(
    regexp = ValidationPatterns.PHONE_NUMBER,
    message = "{Pattern.badge.contactDetailsContactNumber}"
  )
  private String contactDetailsContactNumber;

  @Pattern(
    regexp = ValidationPatterns.PHONE_NUMBER,
    message = "{Pattern.badge.contactDetailsSecondaryContactNumber}"
  )
  private String contactDetailsSecondaryContactNumber;

  @Pattern(regexp = ValidationPatterns.EMAIL, message = "{NotNull.user.emailAddress}")
  private String contactDetailsEmailAddress;

  @NotBlank(message = "{NotNull.badge.eligibility}")
  private String eligibility;
}
