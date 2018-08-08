package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** BadgeOrderRequest */
@Validated
public class BadgeOrderRequest {
  @JsonProperty("party")
  private Party party = null;

  @JsonProperty("localAuthorityShortCode")
  private String localAuthorityShortCode = null;

  @JsonProperty("localAuthorityRef")
  private String localAuthorityRef = null;

  @JsonProperty("applicationDate")
  private LocalDate applicationDate = null;

  @JsonProperty("applicationChannelCode")
  private String applicationChannelCode = null;

  @JsonProperty("startDate")
  private LocalDate startDate = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  @JsonProperty("eligibilityCode")
  private String eligibilityCode = null;

  @JsonProperty("imageFile")
  private String imageFile = null;

  @JsonProperty("deliverToCode")
  private String deliverToCode = null;

  @JsonProperty("deliveryOptionCode")
  private String deliveryOptionCode = null;

  @JsonProperty("numberOfBadges")
  private Integer numberOfBadges = null;

  public BadgeOrderRequest party(Party party) {
    this.party = party;
    return this;
  }

  /**
   * Get party
   *
   * @return party
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }

  public BadgeOrderRequest localAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
    return this;
  }

  /**
   * Short code of local authority.
   *
   * @return localAuthorityShortCode
   */
  @Pattern(regexp = "^[A-Z]+$")
  @ApiModelProperty(example = "ABERD", required = true, value = "Short code of local authority.")
  public String getLocalAuthorityShortCode() {
    return localAuthorityShortCode;
  }

  public void setLocalAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
  }

  public BadgeOrderRequest localAuthorityRef(String localAuthorityRef) {
    this.localAuthorityRef = localAuthorityRef;
    return this;
  }

  /**
   * Get localAuthorityRef
   *
   * @return localAuthorityRef
   */
  @ApiModelProperty(example = "YOURCODE", value = "")
  @Size(max = 100)
  public String getLocalAuthorityRef() {
    return localAuthorityRef;
  }

  public void setLocalAuthorityRef(String localAuthorityRef) {
    this.localAuthorityRef = localAuthorityRef;
  }

  public BadgeOrderRequest applicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
    return this;
  }

  /**
   * The date that the initial application was received by the issuing local authority.
   *
   * @return applicationDate
   */
  @ApiModelProperty(
    example = "2018-04-23",
    required = true,
    value = "The date that the initial application was received by the issuing local authority."
  )
  @NotNull
  @Valid
  public LocalDate getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public BadgeOrderRequest applicationChannelCode(String applicationChannelCode) {
    this.applicationChannelCode = applicationChannelCode;
    return this;
  }

  /**
   * A short code from the APPSOURCE group of reference data. e.g. ONLINE, PAPER, PHONE or INPERSON.
   *
   * @return applicationChannelCode
   */
  @ApiModelProperty(
    example = "ONLINE",
    required = true,
    value =
        "A short code from the APPSOURCE group of reference data. e.g.  ONLINE, PAPER, PHONE or INPERSON."
  )
  @NotNull
  @Size(max = 10)
  public String getApplicationChannelCode() {
    return applicationChannelCode;
  }

  public void setApplicationChannelCode(String applicationChannelCode) {
    this.applicationChannelCode = applicationChannelCode;
  }

  public BadgeOrderRequest startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Cannot be in the past.
   *
   * @return startDate
   */
  @ApiModelProperty(example = "2019-06-30", required = true, value = "Cannot be in the past.")
  @NotNull
  @Valid
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public BadgeOrderRequest expiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * The date the Blue Badge expires. Must be within 3 years of start date.
   *
   * @return expiryDate
   */
  @ApiModelProperty(
    example = "2019-07-01",
    required = true,
    value = "The date the Blue Badge expires.  Must be within 3 years of start date. "
  )
  @NotNull
  @Valid
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  public BadgeOrderRequest eligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
    return this;
  }

  /**
   * A short code from the ELIGIBILITY group of reference data. Reason for badge.
   *
   * @return eligibilityCode
   */
  @ApiModelProperty(
    example = "CHILDBULK",
    value = "A short code from the ELIGIBILITY group of reference data. Reason for badge."
  )
  @Size(max = 10)
  public String getEligibilityCode() {
    return eligibilityCode;
  }

  public void setEligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
  }

  public BadgeOrderRequest imageFile(String imageFile) {
    this.imageFile = imageFile;
    return this;
  }

  /**
   * Base64 encoded. Must be between 50KB and 10MB, and of format JPG, PNG, or GIF.
   *
   * @return imageFile
   */
  @ApiModelProperty(
    value = "Base64 encoded.  Must be between 50KB and 10MB, and of format JPG, PNG, or GIF."
  )
  public String getImageFile() {
    return imageFile;
  }

  public void setImageFile(String imageFile) {
    this.imageFile = imageFile;
  }

  public BadgeOrderRequest deliverToCode(String deliverToCode) {
    this.deliverToCode = deliverToCode;
    return this;
  }

  /**
   * A short code from the DELIVER group of reference data.
   *
   * @return deliverToCode
   */
  @ApiModelProperty(
    example = "HOME",
    required = true,
    value = "A short code from the DELIVER group of reference data."
  )
  @NotNull
  @Size(max = 10)
  public String getDeliverToCode() {
    return deliverToCode;
  }

  public void setDeliverToCode(String deliverToCode) {
    this.deliverToCode = deliverToCode;
  }

  public BadgeOrderRequest deliveryOptionCode(String deliveryOptionCode) {
    this.deliveryOptionCode = deliveryOptionCode;
    return this;
  }

  /**
   * A short code from the DELOPT group of reference data. e.g. STANDARD or FAST
   *
   * @return deliveryOptionCode
   */
  @ApiModelProperty(
    example = "STANDARD",
    required = true,
    value = "A short code from the DELOPT group of reference data. e.g. STANDARD or FAST"
  )
  @NotNull
  @Size(max = 10)
  public String getDeliveryOptionCode() {
    return deliveryOptionCode;
  }

  public void setDeliveryOptionCode(String deliveryOptionCode) {
    this.deliveryOptionCode = deliveryOptionCode;
  }

  public BadgeOrderRequest numberOfBadges(Integer numberOfBadges) {
    this.numberOfBadges = numberOfBadges;
    return this;
  }

  /**
   * Must be 1 for a person, can be multiple for an organisation. minimum: 1 maximum: 999
   *
   * @return numberOfBadges
   */
  @ApiModelProperty(
    example = "1",
    value = "Must be 1 for a person, can be multiple for an organisation."
  )
  @Min(1)
  @Max(999)
  public Integer getNumberOfBadges() {
    return numberOfBadges;
  }

  public void setNumberOfBadges(Integer numberOfBadges) {
    this.numberOfBadges = numberOfBadges;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadgeOrderRequest badgeOrderRequest = (BadgeOrderRequest) o;
    return Objects.equals(this.party, badgeOrderRequest.party)
        && Objects.equals(this.localAuthorityShortCode, badgeOrderRequest.localAuthorityShortCode)
        && Objects.equals(this.localAuthorityRef, badgeOrderRequest.localAuthorityRef)
        && Objects.equals(this.applicationDate, badgeOrderRequest.applicationDate)
        && Objects.equals(this.applicationChannelCode, badgeOrderRequest.applicationChannelCode)
        && Objects.equals(this.startDate, badgeOrderRequest.startDate)
        && Objects.equals(this.expiryDate, badgeOrderRequest.expiryDate)
        && Objects.equals(this.eligibilityCode, badgeOrderRequest.eligibilityCode)
        && Objects.equals(this.imageFile, badgeOrderRequest.imageFile)
        && Objects.equals(this.deliverToCode, badgeOrderRequest.deliverToCode)
        && Objects.equals(this.deliveryOptionCode, badgeOrderRequest.deliveryOptionCode)
        && Objects.equals(this.numberOfBadges, badgeOrderRequest.numberOfBadges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        party,
        localAuthorityShortCode,
        localAuthorityRef,
        applicationDate,
        applicationChannelCode,
        startDate,
        expiryDate,
        eligibilityCode,
        imageFile,
        deliverToCode,
        deliveryOptionCode,
        numberOfBadges);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadgeOrderRequest {\n");

    sb.append("    party: ").append(toIndentedString(party)).append("\n");
    sb.append("    localAuthorityShortCode: ")
        .append(toIndentedString(localAuthorityShortCode))
        .append("\n");
    sb.append("    localAuthorityRef: ").append(toIndentedString(localAuthorityRef)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    applicationChannelCode: ")
        .append(toIndentedString(applicationChannelCode))
        .append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    eligibilityCode: ").append(toIndentedString(eligibilityCode)).append("\n");
    sb.append("    imageFile: ").append(toIndentedString(imageFile)).append("\n");
    sb.append("    deliverToCode: ").append(toIndentedString(deliverToCode)).append("\n");
    sb.append("    deliveryOptionCode: ").append(toIndentedString(deliveryOptionCode)).append("\n");
    sb.append("    numberOfBadges: ").append(toIndentedString(numberOfBadges)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
