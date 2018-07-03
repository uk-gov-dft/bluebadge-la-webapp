package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/** BadgeSummary */
@Validated
public class BadgeSummary {
  @JsonProperty("badgeNumber")
  private String badgeNumber = null;

  @JsonProperty("partyTypeCode")
  private String partyTypeCode = null;

  @JsonProperty("partyTypeDescription")
  private String partyTypeDescription = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("nationalInsurance")
  private String nationalInsurance = null;

  @JsonProperty("localAuthorityId")
  private Integer localAuthorityId = null;

  @JsonProperty("localAuthorityName")
  private String localAuthorityName = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  @JsonProperty("statusCode")
  private String statusCode = null;

  @JsonProperty("statusDescription")
  private String statusDescription = null;

  public BadgeSummary badgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  /**
   * The unique badge number for this badge
   *
   * @return badgeNumber
   */
  @ApiModelProperty(value = "The unique badge number for this badge")
  public String getBadgeNumber() {
    return badgeNumber;
  }

  public void setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
  }

  public BadgeSummary partyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
    return this;
  }

  /**
   * Get partyTypeCode
   *
   * @return partyTypeCode
   */
  @ApiModelProperty(value = "")
  public String getPartyTypeCode() {
    return partyTypeCode;
  }

  public void setPartyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
  }

  public BadgeSummary partyTypeDescription(String partyTypeDescription) {
    this.partyTypeDescription = partyTypeDescription;
    return this;
  }

  /**
   * Get partyTypeDescription
   *
   * @return partyTypeDescription
   */
  @ApiModelProperty(value = "")
  public String getPartyTypeDescription() {
    return partyTypeDescription;
  }

  public void setPartyTypeDescription(String partyTypeDescription) {
    this.partyTypeDescription = partyTypeDescription;
  }

  public BadgeSummary name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the badge holder Organisation or Person
   *
   * @return name
   */
  @ApiModelProperty(value = "The name of the badge holder Organisation or Person")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BadgeSummary nationalInsurance(String nationalInsurance) {
    this.nationalInsurance = nationalInsurance;
    return this;
  }

  /**
   * The badgeholders national insurance number
   *
   * @return nationalInsurance
   */
  @ApiModelProperty(value = "The badgeholders national insurance number")
  public String getNationalInsurance() {
    return nationalInsurance;
  }

  public void setNationalInsurance(String nationalInsurance) {
    this.nationalInsurance = nationalInsurance;
  }

  public BadgeSummary localAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
    return this;
  }

  /**
   * Get localAuthorityId
   *
   * @return localAuthorityId
   */
  @ApiModelProperty(value = "")
  public Integer getLocalAuthorityId() {
    return localAuthorityId;
  }

  public void setLocalAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
  }

  public BadgeSummary localAuthorityName(String localAuthorityName) {
    this.localAuthorityName = localAuthorityName;
    return this;
  }

  /**
   * Display name of the Local Authority
   *
   * @return localAuthorityName
   */
  @ApiModelProperty(value = "Display name of the Local Authority")
  public String getLocalAuthorityName() {
    return localAuthorityName;
  }

  public void setLocalAuthorityName(String localAuthorityName) {
    this.localAuthorityName = localAuthorityName;
  }

  public BadgeSummary expiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * The date the blue bagde expires
   *
   * @return expiryDate
   */
  @ApiModelProperty(value = "The date the blue bagde expires")
  @Valid
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  public BadgeSummary statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * The current status of the blue bage
   *
   * @return statusCode
   */
  @ApiModelProperty(value = "The current status of the blue bage")
  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public BadgeSummary statusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
    return this;
  }

  /**
   * Status full description
   *
   * @return statusDescription
   */
  @ApiModelProperty(value = "Status full description")
  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadgeSummary badgeSummary = (BadgeSummary) o;
    return Objects.equals(this.badgeNumber, badgeSummary.badgeNumber)
        && Objects.equals(this.partyTypeCode, badgeSummary.partyTypeCode)
        && Objects.equals(this.partyTypeDescription, badgeSummary.partyTypeDescription)
        && Objects.equals(this.name, badgeSummary.name)
        && Objects.equals(this.nationalInsurance, badgeSummary.nationalInsurance)
        && Objects.equals(this.localAuthorityId, badgeSummary.localAuthorityId)
        && Objects.equals(this.localAuthorityName, badgeSummary.localAuthorityName)
        && Objects.equals(this.expiryDate, badgeSummary.expiryDate)
        && Objects.equals(this.statusCode, badgeSummary.statusCode)
        && Objects.equals(this.statusDescription, badgeSummary.statusDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        badgeNumber,
        partyTypeCode,
        partyTypeDescription,
        name,
        nationalInsurance,
        localAuthorityId,
        localAuthorityName,
        expiryDate,
        statusCode,
        statusDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadgeSummary {\n");

    sb.append("    badgeNumber: ").append(toIndentedString(badgeNumber)).append("\n");
    sb.append("    partyTypeCode: ").append(toIndentedString(partyTypeCode)).append("\n");
    sb.append("    partyTypeDescription: ")
        .append(toIndentedString(partyTypeDescription))
        .append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nationalInsurance: ").append(toIndentedString(nationalInsurance)).append("\n");
    sb.append("    localAuthorityId: ").append(toIndentedString(localAuthorityId)).append("\n");
    sb.append("    localAuthorityName: ").append(toIndentedString(localAuthorityName)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusDescription: ").append(toIndentedString(statusDescription)).append("\n");
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
