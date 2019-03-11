package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.webapp.la.service.enums.Status;

/** Badge */
@Validated
public class Badge {
  @JsonProperty("badgeNumber")
  private String badgeNumber = null;

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

  @JsonProperty("orderDate")
  private LocalDate orderDate = null;

  @JsonProperty("startDate")
  private LocalDate startDate = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  @JsonProperty("eligibilityCode")
  private String eligibilityCode = null;

  @JsonProperty("imageLink")
  private String imageLink = null;

  @JsonProperty("cancelReasonCode")
  private String cancelReasonCode = null;

  @JsonProperty("statusCode")
  private String statusCode = null;

  @JsonProperty("replaceReasonCode")
  private String replaceReasonCode;

  public Badge badgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  /**
   * The unique badge number for this badge.
   *
   * @return badgeNumber
   */
  @ApiModelProperty(example = "091215", value = "The unique badge number for this badge.")
  @Pattern(regexp = "^[0-9A-HK]{6}$")
  public String getBadgeNumber() {
    return badgeNumber;
  }

  public void setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
  }

  public Badge party(Party party) {
    this.party = party;
    return this;
  }

  /**
   * Get party
   *
   * @return party
   */
  @ApiModelProperty(value = "")
  @Valid
  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }

  public Badge localAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
    return this;
  }

  /**
   * Short code of local authority.
   *
   * @return localAuthorityShortCode
   */
  @ApiModelProperty(example = "BIRM", value = "Short code of local authority.")
  @Pattern(regexp = "^[A-Z]+$")
  public String getLocalAuthorityShortCode() {
    return localAuthorityShortCode;
  }

  public void setLocalAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
  }

  public Badge localAuthorityRef(String localAuthorityRef) {
    this.localAuthorityRef = localAuthorityRef;
    return this;
  }

  /**
   * A reference to enable local authorities to link this badge to records in other systems
   *
   * @return localAuthorityRef
   */
  @ApiModelProperty(
    example = "YOURREF",
    value = "A reference to enable local authorities to link this badge to records in other systems"
  )
  public String getLocalAuthorityRef() {
    return localAuthorityRef;
  }

  public void setLocalAuthorityRef(String localAuthorityRef) {
    this.localAuthorityRef = localAuthorityRef;
  }

  public Badge applicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
    return this;
  }

  /**
   * The date that the initial application was received by the issuing local authority.
   *
   * @return applicationDate
   */
  @ApiModelProperty(
    example = "2017-07-21",
    value = "The date that the initial application was received by the issuing local authority."
  )
  @Valid
  public LocalDate getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public Badge applicationChannelCode(String applicationChannelCode) {
    this.applicationChannelCode = applicationChannelCode;
    return this;
  }

  /**
   * A short code from the APPSOURCE group of reference data e.g. ONLINE, PAPER, PHONE or INPERSON.
   *
   * @return applicationChannelCode
   */
  @ApiModelProperty(
    example = "INPERSON",
    value =
        "A short code from the APPSOURCE group of reference data e.g. ONLINE, PAPER, PHONE or INPERSON."
  )
  public String getApplicationChannelCode() {
    return applicationChannelCode;
  }

  public void setApplicationChannelCode(String applicationChannelCode) {
    this.applicationChannelCode = applicationChannelCode;
  }

  public Badge orderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
    return this;
  }

  /**
   * The date that the badge was ordered by the issuing local authority.
   *
   * @return orderDate
   */
  @ApiModelProperty(
    example = "2018-07-07",
    value = "The date that the badge was ordered by the issuing local authority."
  )
  @Valid
  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public Badge startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * The date that the badge comes into effect.
   *
   * @return startDate
   */
  @ApiModelProperty(example = "2018-07-07", value = "The date that the badge comes into effect.")
  @Valid
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public Badge expiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * The date the badge expires.
   *
   * @return expiryDate
   */
  @ApiModelProperty(example = "2019-06-31", value = "The date the badge expires.")
  @Valid
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Badge eligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
    return this;
  }

  /**
   * A short code from the ELIGIBILIT group of reference data.
   *
   * @return eligibilityCode
   */
  @ApiModelProperty(
    example = "WALKD",
    value = "A short code from the ELIGIBILIT group of reference data."
  )
  @Size(max = 10)
  public String getEligibilityCode() {
    return eligibilityCode;
  }

  public void setEligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
  }

  public Badge imageLink(String imageLink) {
    this.imageLink = imageLink;
    return this;
  }

  /**
   * A URL for the badge photo.
   *
   * @return imageLink
   */
  @ApiModelProperty(example = "http://tiny.url?q=ab63fg", value = "A URL for the badge photo.")
  @Size(max = 255)
  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Badge cancelReasonCode(String cancelReasonCode) {
    this.cancelReasonCode = cancelReasonCode;
    return this;
  }

  /**
   * A short code from the CANCEL group of reference data.
   *
   * @return cancelReasonCode
   */
  @ApiModelProperty(
    example = "NOLONG",
    value = "A short code from the CANCEL group of reference data. "
  )
  @Size(max = 10)
  public String getCancelReasonCode() {
    return cancelReasonCode;
  }

  public void setCancelReasonCode(String cancelReasonCode) {
    this.cancelReasonCode = cancelReasonCode;
  }

  public Badge replaceReasonCode(String replaceReasonCode) {
    this.replaceReasonCode = replaceReasonCode;
    return this;
  }

  @Size(max = 10)
  public String getReplaceReasonCode() {
    return replaceReasonCode;
  }

  public void setReplaceReasonCode(String replaceReasonCode) {
    this.replaceReasonCode = replaceReasonCode;
  }

  public Badge statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * A short code from the STATUS group of reference data. The current status of the badge e.g.
   * ACTIVE, EXPIRED, CANCELLED.
   *
   * @return statusCode
   */
  @ApiModelProperty(
    example = "CANCELLED",
    value =
        "A short code from the STATUS group of reference data. The current status of the badge e.g. ACTIVE, EXPIRED, CANCELLED."
  )
  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Badge badge = (Badge) o;
    return Objects.equals(this.badgeNumber, badge.badgeNumber)
        && Objects.equals(this.party, badge.party)
        && Objects.equals(this.localAuthorityShortCode, badge.localAuthorityShortCode)
        && Objects.equals(this.localAuthorityRef, badge.localAuthorityRef)
        && Objects.equals(this.applicationDate, badge.applicationDate)
        && Objects.equals(this.applicationChannelCode, badge.applicationChannelCode)
        && Objects.equals(this.orderDate, badge.orderDate)
        && Objects.equals(this.startDate, badge.startDate)
        && Objects.equals(this.expiryDate, badge.expiryDate)
        && Objects.equals(this.eligibilityCode, badge.eligibilityCode)
        && Objects.equals(this.imageLink, badge.imageLink)
        && Objects.equals(this.cancelReasonCode, badge.cancelReasonCode)
        && Objects.equals(this.statusCode, badge.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        badgeNumber,
        party,
        localAuthorityShortCode,
        localAuthorityRef,
        applicationDate,
        applicationChannelCode,
        orderDate,
        startDate,
        expiryDate,
        eligibilityCode,
        imageLink,
        cancelReasonCode,
        statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Badge {\n");

    sb.append("    badgeNumber: ").append(toIndentedString(badgeNumber)).append("\n");
    sb.append("    party: ").append(toIndentedString(party)).append("\n");
    sb.append("    localAuthorityShortCode: ")
        .append(toIndentedString(localAuthorityShortCode))
        .append("\n");
    sb.append("    localAuthorityRef: ").append(toIndentedString(localAuthorityRef)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    applicationChannelCode: ")
        .append(toIndentedString(applicationChannelCode))
        .append("\n");
    sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    eligibilityCode: ").append(toIndentedString(eligibilityCode)).append("\n");
    sb.append("    imageLink: ").append(toIndentedString(imageLink)).append("\n");
    sb.append("    cancelReasonCode: ").append(toIndentedString(cancelReasonCode)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
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

  public boolean canBeReplaced() {
    return this.statusCode.equals(Status.ISSUED.name()) && this.expiryDate.isAfter(LocalDate.now());
  }

  public boolean canBeCancelled() {
    return (this.statusCode.equals(Status.ISSUED.name())
            || this.statusCode.equals(Status.ORDERED.name()))
        && this.expiryDate.isAfter(LocalDate.now());
  }
}
