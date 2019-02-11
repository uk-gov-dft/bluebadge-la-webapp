package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.la.controller.validation.ValidationPatterns;

@Data
@Builder
public class OrderBadgeOrganisationDetailsFormRequest
    implements OrderBadgeBaseDetailsFormRequest, Serializable {
  @NotBlank(message = "{NotNull.user.name}")
  @Size(max = 100)
  private String name;

  @NotBlank(message = "{NotNull.badge.buildingAndStreet}")
  @Size(min = 1, max = 50, message = "{Size.badge.buildingAndStreet}")
  private String buildingAndStreet;

  @Size(max = 40, message = "{Size.badge.optionalAddressField}")
  private String optionalAddressField;

  @Size(min = 1, max = 40, message = "{Size.badge.townOrCity}")
  @NotBlank(message = "{NotNull.badge.townOrCity}")
  private String townOrCity;

  @NotBlank(message = "{NotNull.badge.postcode}")
  @Pattern(
    regexp = ValidationPatterns.POSTCODE_CASE_INSENSITIVE,
    message = "{Pattern.badge.postcode}"
  )
  private String postcode;

  @NotBlank(message = "{NotNull.user.name}")
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
}
