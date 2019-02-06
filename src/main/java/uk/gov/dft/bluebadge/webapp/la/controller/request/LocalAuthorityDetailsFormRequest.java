package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class LocalAuthorityDetailsFormRequest implements Serializable {
  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.description}")
  private String description;

  private String welshDescription;

  private String nameLine2;

  private String addressLine1;

  private String addressLine2;

  private String addressLine3;

  private String addressLine4;

  private String town;

  private String county;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.postcode}")
  private String postcode;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.country}")
  private String country;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.nation}")
  @Pattern(
    regexp = "^(\\s*|ENG|SCO|WLS|NIR)$",
    message = "{Pattern.localAuthorityDetailPage.nation}"
  )
  private String nation;

  private String contactNumber;

  @NotBlank(message = "{NotBlank.localAuthorityDetailPage.websiteUrl}")
  @URL(message = "{URL.localAuthorityDetailPage.websiteUrl}")
  private String websiteUrl;

  private String emailAddress;

  private String badgePackType;

  @DecimalMin(value = "0.0", message = "{Range.localAuthorityDetailPage.badgeCost}")
  @DecimalMax(value = "999.99", message = "{Range.localAuthorityDetailPage.badgeCost}")
  private BigDecimal badgeCost;

  private Boolean paymentsEnabled;

  @URL(message = "{URL.localAuthority.differentServiceSignpostUrl}")
  private String differentServiceSignpostUrl;
}
