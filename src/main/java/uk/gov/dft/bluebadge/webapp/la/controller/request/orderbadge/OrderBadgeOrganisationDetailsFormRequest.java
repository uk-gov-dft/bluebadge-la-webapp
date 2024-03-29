package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.common.util.ValidationPattern;

@Data
@Builder
public class OrderBadgeOrganisationDetailsFormRequest
    implements OrderBadgeBaseDetailsFormRequest, Serializable {
  private String flowId;

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
    regexp = ValidationPattern.POSTCODE_CASE_INSENSITIVE,
    message = "{Pattern.badge.postcode}"
  )
  private String postcode;

  @NotBlank(message = "{NotNull.user.name}")
  @Pattern(regexp = ValidationPattern.PERSON_NAME, message = "{Pattern.user.name}")
  private String contactDetailsName;

  @NotBlank(message = "{NotNull.badge.contactDetailsContactNumber}")
  @Pattern(
    regexp = ValidationPattern.PHONE_NUMBER,
    message = "{Pattern.badge.contactDetailsContactNumber}"
  )
  private String contactDetailsContactNumber;

  @Pattern(
    regexp = ValidationPattern.PHONE_NUMBER,
    message = "{Pattern.badge.contactDetailsSecondaryContactNumber}"
  )
  private String contactDetailsSecondaryContactNumber;

  @Pattern(regexp = ValidationPattern.EMAIL, message = "{NotNull.user.emailAddress}")
  private String contactDetailsEmailAddress;
}
