package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import uk.gov.dft.bluebadge.common.util.ValidationPattern;

@Data
@Builder
public class LocalAuthorityDetailsFormRequest implements Serializable {
  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.description}")
  private String description;

  private String welshDescription;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.nameLine2}")
  private String nameLine2;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.addressLine1}")
  private String addressLine1;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.addressLine2}")
  private String addressLine2;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.addressLine3}")
  private String addressLine3;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.addressLine4}")
  private String addressLine4;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.town}")
  private String town;

  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.county}")
  private String county;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.postcode}")
  @Pattern(
          regexp = ValidationPattern.POSTCODE_CASE_INSENSITIVE,
          message = "{Pattern.localAuthorityDetailPage.postcode}"
  )
  private String postcode;
  
  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.country}")
  @Size(min = 1, max = 40, message = "{Size.localAuthorityDetailPage.country}")
  private String country;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.nation}")
  private String nation;

  @Pattern(
          regexp = ValidationPattern.PHONE_NUMBER,
          message = "{Pattern.localAuthorityDetailPage.contactNumber}"
  )
  private String contactNumber;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.websiteUrl}")
  @URL(message = "{URL.localAuthorityDetailPage.websiteUrl}")
  private String websiteUrl;

  @Pattern(
          regexp = ValidationPattern.EMAIL,
          message = "{Pattern.localAuthorityDetailPage.emailAddress}"
  )
  private String emailAddress;

  private String badgePackType;

  private Boolean paymentsEnabled;

  public Boolean arePaymentsEnabled() {
    return (Boolean.TRUE.equals(paymentsEnabled));
  }

  private String badgeCost;

  @URL(message = "{URL.localAuthority.differentServiceSignpostUrl}")
  private String differentServiceSignpostUrl;
}
