package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * BadgeSummary
 */
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

  @JsonProperty("nino")
  private String nino = null;

  @JsonProperty("localAuthorityCode")
  private Integer localAuthorityCode = null;

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
   * The unique badge number for this badge.
   *
   * @return badgeNumber
   */
  @ApiModelProperty(example = "095215", value = "The unique badge number for this badge.")
  @Pattern(regexp = "^[0-9A-HJK]{6}$")
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
   * A short code from the PARTY group of reference data.
   *
   * @return partyTypeCode
   */
  @ApiModelProperty(
    example = "PERSON",
    value = "A short code from the PARTY group of reference data."
  )
  @Size(max = 10)
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
  @ApiModelProperty(example = "Person", value = "")
  @Size(max = 100)
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
  @ApiModelProperty(
    example = "Ms Mary Inglethorpe",
    value = "The name of the badge holder Organisation or Person"
  )
  @Size(max = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BadgeSummary nino(String nino) {
    this.nino = nino;
    return this;
  }

  /**
   * The badge holder's National Insurance number.
   *
   * @return nino
   */
  @ApiModelProperty(example = "NY186548E", value = "The badge holder's National Insurance number.")
  @Pattern(
    regexp =
      "^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])(?:\\s*\\d\\s*){6}([A-D]|\\s)$"
  )
  public String getNino() {
    return nino;
  }

  public void setNino(String nino) {
    this.nino = nino;
  }

  public BadgeSummary localAuthorityCode(Integer localAuthorityCode) {
    this.localAuthorityCode = localAuthorityCode;
    return this;
  }

  /**
   * The code for the local authority.
   *
   * @return localAuthorityCode
   */
  @ApiModelProperty(example = "211", value = "The code for the local authority.")
  public Integer getLocalAuthorityCode() {
    return localAuthorityCode;
  }

  public void setLocalAuthorityCode(Integer localAuthorityCode) {
    this.localAuthorityCode = localAuthorityCode;
  }

  public BadgeSummary localAuthorityName(String localAuthorityName) {
    this.localAuthorityName = localAuthorityName;
    return this;
  }

  /**
   * Display name of the local authority.
   *
   * @return localAuthorityName
   */
  @ApiModelProperty(
    example = "Shropshire County Council",
    value = "Display name of the local authority."
  )
  @Size(max = 100)
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
   * The date the Blue Badge expires.
   *
   * @return expiryDate
   */
  @ApiModelProperty(example = "2019-06-10", value = "The date the Blue Badge expires.")
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
   * A short code from the STATUS group of reference data.
   *
   * @return statusCode
   */
  @ApiModelProperty(
    example = "ISSUED",
    value = "A short code from the STATUS group of reference data."
  )
  @Size(max = 10)
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
   * Status full description.
   *
   * @return statusDescription
   */
  @ApiModelProperty(example = "Issued", value = "Status full description.")
  @Size(max = 100)
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
      && Objects.equals(this.nino, badgeSummary.nino)
      && Objects.equals(this.localAuthorityCode, badgeSummary.localAuthorityCode)
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
      nino,
      localAuthorityCode,
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
    sb.append("    nino: ").append(toIndentedString(nino)).append("\n");
    sb.append("    localAuthorityCode: ").append(toIndentedString(localAuthorityCode)).append("\n");
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
