package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

/** BadgeSummary */
@Validated
public class BadgeSummary {
  @JsonProperty("badgeNumber")
  private String badgeNumber = null;

  @JsonProperty("partyTypeCode")
  private String partyTypeCode = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("nino")
  private String nino = null;

  @JsonProperty("localAuthorityShortCode")
  private String localAuthorityShortCode = null;

  @JsonProperty("postCode")
  private String postCode = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  @JsonProperty("statusCode")
  private String statusCode = null;

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

  public BadgeSummary localAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
    return this;
  }

  /**
   * The short code for the local authority.
   *
   * @return localAuthorityShortCode
   */
  @Pattern(regexp = "^[A-Z]+$")
  @ApiModelProperty(example = "BIRM", value = "The short code for the local authority.")
  public String getLocalAuthorityShortCode() {
    return localAuthorityShortCode;
  }

  public void setLocalAuthorityShortCode(String localAuthorityShortCode) {
    this.localAuthorityShortCode = localAuthorityShortCode;
  }

  public BadgeSummary postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * Get postCode
   *
   * @return postCode
   */
  @ApiModelProperty(example = "SK6 8GH", value = "")
  @Pattern(regexp = "^[A-Za-z]{1,2}[0-9][0-9A-Za-z]?\\s?[0-9][A-Za-z]{2}$")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadgeSummary badgeSummary = (BadgeSummary) o;
    return Objects.equals(this.badgeNumber, badgeSummary.badgeNumber)
        && Objects.equals(this.partyTypeCode, badgeSummary.partyTypeCode)
        && Objects.equals(this.name, badgeSummary.name)
        && Objects.equals(this.nino, badgeSummary.nino)
        && Objects.equals(this.localAuthorityShortCode, badgeSummary.localAuthorityShortCode)
        && Objects.equals(this.postCode, badgeSummary.postCode)
        && Objects.equals(this.expiryDate, badgeSummary.expiryDate)
        && Objects.equals(this.statusCode, badgeSummary.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(badgeNumber, partyTypeCode, name, nino, postCode, expiryDate, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadgeSummary {\n");
    sb.append("    badgeNumber: ").append(toIndentedString(badgeNumber)).append("\n");
    sb.append("    partyTypeCode: ").append(toIndentedString(partyTypeCode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nino: ").append(toIndentedString(nino)).append("\n");
    sb.append("    localAuthorityShortCode: ")
        .append(toIndentedString(localAuthorityShortCode))
        .append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
