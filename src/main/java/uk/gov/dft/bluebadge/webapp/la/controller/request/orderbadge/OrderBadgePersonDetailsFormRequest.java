package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import java.io.Serializable;
import java.util.Arrays;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.CannotBeInTheFutureDate;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.DateValidationUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ValidationPatterns;

@Data
@Builder
public class OrderBadgePersonDetailsFormRequest
    implements OrderBadgeBaseDetailsFormRequest, Serializable {
  private Integer numberOfBadges;

  @NotBlank(message = "{NotNull.user.name}")
  @Pattern(regexp = ValidationPatterns.PERSON_NAME, message = "{Pattern.user.name}")
  @Size(max = 100)
  private String name;

  @NotBlank(message = "{NotNull.badge.gender}")
  private String gender;

  private Integer dobDay;

  private Integer dobMonth;

  private Integer dobYear;

  private String dob;

  private transient MultipartFile photo;

  private String thumbBase64;

  private byte[] byteImage;

  private static final String[] ALLOWED_FILE_TYPES =
      new String[] {"image/jpg", "image/jpeg", "image/png", "image/gif"};

  @NotBlank(message = "{NotNull.badge.dob}")
  @CannotBeInTheFutureDate(message = "{Pattern.badge.dob}")
  public String getDob() {
    return DateValidationUtils.buildDateStringIfValidNullIfInvalid(dobDay, dobMonth, dobYear);
  }

  @Pattern(regexp = ValidationPatterns.NINO_CASE_INSENSITIVE, message = "{Pattern.badge.nino}")
  private String nino;

  @NotBlank(message = "{NotNull.badge.buildingAndStreet}")
  @Size(max = 100, message = "{Size.badge.buildingAndStreet}")
  private String buildingAndStreet;

  @Size(max = 100, message = "{Size.badge.optionalAddressField}")
  private String optionalAddressField;

  @Size(max = 100, message = "{Size.badge.townOrCity}")
  @NotBlank(message = "{NotNull.badge.townOrCity}")
  private String townOrCity;

  @NotBlank(message = "{NotNull.badge.postcode}")
  @Pattern(
    regexp = ValidationPatterns.POSTCODE_CASE_INSENSITIVE,
    message = "{Pattern.badge.postcode}"
  )
  private String postcode;

  @Pattern(regexp = ValidationPatterns.PERSON_NAME, message = "{Pattern.user.name}")
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

  public boolean hasPhoto() {
    return null != getPhoto() && getPhoto().getSize() > 0;
  }

  public boolean isPhotoValid() {
    return hasPhoto()
        && Arrays.asList(ALLOWED_FILE_TYPES).contains(getPhoto().getContentType().toLowerCase());
  }

  public String getNino() {
    return null != nino ? nino.replaceAll("\\s+", "").toUpperCase() : null;
  }
}
