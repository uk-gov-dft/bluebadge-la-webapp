package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.common.util.ValidationPattern;
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

  @JsonProperty("issuedDate")
  private LocalDateTime issuedDate = null;

  @JsonProperty("printDate")
  private LocalDateTime printDate = null;

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

  @JsonProperty("replaceReasonCode")
  private String replaceReasonCode = null;

  @JsonProperty("rejectedReason")
  private String rejectedReason;

  @JsonProperty("statusCode")
  private String statusCode = null;

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
   * Short code of local authority
   *
   * @return localAuthorityShortCode
   */
  @ApiModelProperty(example = "BLACK", value = "Short code of local authority")
  @Pattern(regexp = ValidationPattern.LA_SHORT_CODE)
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
    value = "The date that the badge was issued by the issuing local authority."
  )
  @Valid
  public LocalDateTime getIssuedDate() {
    return issuedDate;
  }

  public void setIssuedDate(LocalDateTime issuedDate) {
    this.issuedDate = issuedDate;
  }

  public Badge issuedDate(LocalDateTime issuedDate) {
    this.issuedDate = issuedDate;
    return this;
  }

  /**
   * The date that the batch corresponding to this badge was sent to printer.
   *
   * @return printDate
   */
  @ApiModelProperty(
    example = "2018-07-07",
    value = "The date that the batch that contains the badge was sent to printer."
  )
  @Valid
  public LocalDateTime getPrintDate() {
    return printDate;
  }

  public void setPrintDate(LocalDateTime printDate) {
    this.printDate = printDate;
  }

  public Badge printDate(LocalDateTime printDate) {
    this.printDate = printDate;
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
    value = "A short code from the CANCEL group of reference data."
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

  /**
   * A short code from the REPLACE group of reference data.
   *
   * @return replaceReasonCode
   */
  @ApiModelProperty(
    example = "STOLE",
    value = "A short code from the REPLACE group of reference data."
  )
  @Size(max = 10)
  public String getReplaceReasonCode() {
    return replaceReasonCode;
  }

  public void setReplaceReasonCode(String replaceReasonCode) {
    this.replaceReasonCode = replaceReasonCode;
  }

  public Badge rejectedReason(String rejectedReason) {
    this.rejectedReason = rejectedReason;
    return this;
  }

  /**
   * A short code from the REPLACE group of reference data.
   *
   * @return replaceReasonCode
   */
  @ApiModelProperty(
    example = "TODO: STOLE",
    value = "TODO: A short code from the REPLACE group of reference data."
  )
  @Size(max = 10)
  public String getRejectedReason() {
    return rejectedReason;
  }

  public void setRejectedReason(String rejectedReason) {
    this.rejectedReason = rejectedReason;
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
    return Objects.equals(badgeNumber, badge.badgeNumber)
        && Objects.equals(party, badge.party)
        && Objects.equals(localAuthorityShortCode, badge.localAuthorityShortCode)
        && Objects.equals(localAuthorityRef, badge.localAuthorityRef)
        && Objects.equals(applicationDate, badge.applicationDate)
        && Objects.equals(applicationChannelCode, badge.applicationChannelCode)
        && Objects.equals(orderDate, badge.orderDate)
        && Objects.equals(issuedDate, badge.issuedDate)
        && Objects.equals(printDate, badge.printDate)
        && Objects.equals(startDate, badge.startDate)
        && Objects.equals(expiryDate, badge.expiryDate)
        && Objects.equals(eligibilityCode, badge.eligibilityCode)
        && Objects.equals(imageLink, badge.imageLink)
        && Objects.equals(cancelReasonCode, badge.cancelReasonCode)
        && Objects.equals(replaceReasonCode, badge.replaceReasonCode)
        && Objects.equals(rejectedReason, badge.rejectedReason)
        && Objects.equals(statusCode, badge.statusCode);
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
        issuedDate,
        printDate,
        startDate,
        expiryDate,
        eligibilityCode,
        imageLink,
        cancelReasonCode,
        replaceReasonCode,
        rejectedReason,
        statusCode);
  }

  @Override
  public String toString() {
    return "Badge{"
        + "badgeNumber='"
        + badgeNumber
        + '\''
        + ", party="
        + party
        + ", localAuthorityShortCode='"
        + localAuthorityShortCode
        + '\''
        + ", localAuthorityRef='"
        + localAuthorityRef
        + '\''
        + ", applicationDate="
        + applicationDate
        + ", applicationChannelCode='"
        + applicationChannelCode
        + '\''
        + ", orderDate="
        + orderDate
        + ", issuedDate="
        + issuedDate
        + ", printDate="
        + printDate
        + ", startDate="
        + startDate
        + ", expiryDate="
        + expiryDate
        + ", eligibilityCode='"
        + eligibilityCode
        + '\''
        + ", imageLink='"
        + imageLink
        + '\''
        + ", cancelReasonCode='"
        + cancelReasonCode
        + '\''
        + ", replaceReasonCode='"
        + replaceReasonCode
        + '\''
        + ", rejectedReason='"
        + rejectedReason
        + '\''
        + ", statusCode='"
        + statusCode
        + '\''
        + '}';
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
