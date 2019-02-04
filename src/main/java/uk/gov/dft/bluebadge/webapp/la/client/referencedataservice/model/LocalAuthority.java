package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

/** LocalAuthority */
@Validated
public class LocalAuthority implements Serializable {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("welshDescription")
  private String welshDescription = null;

  @JsonProperty("nameLine2")
  private String nameLine2 = null;

  @JsonProperty("addressLine1")
  private String addressLine1 = null;

  @JsonProperty("addressLine2")
  private String addressLine2 = null;

  @JsonProperty("addressLine3")
  private String addressLine3 = null;

  @JsonProperty("addressLine4")
  private String addressLine4 = null;

  @JsonProperty("town")
  private String town = null;

  @JsonProperty("county")
  private String county = null;

  @JsonProperty("postcode")
  private String postcode = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("nation")
  private String nation = null;

  @JsonProperty("contactNumber")
  private String contactNumber = null;

  @JsonProperty("contactUrl")
  private String contactUrl = null;

  @JsonProperty("emailAddress")
  private String emailAddress = null;

  @JsonProperty("badgePackType")
  private String badgePackType = null;

  @JsonProperty("badgeCost")
  private BigDecimal badgeCost = null;

  @JsonProperty("paymentsEnabled")
  private Boolean paymentsEnabled = null;

  @JsonProperty("differentServiceSignpostUrl")
  private String differentServiceSignpostUrl = null;

  public LocalAuthority nameLine2(String nameLine2) {
    this.nameLine2 = nameLine2;
    return this;
  }

  /**
   * description
   *
   * @return description
   */
  @ApiModelProperty(value = "description")
  @NotBlank
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalAuthority description(String description) {
    this.description = description;
    return this;
  }

  /**
   * welsh description
   *
   * @return welshDescription
   */
  @ApiModelProperty(value = "welsh description")
  public String getWelshDescription() {
    return welshDescription;
  }

  public void setWelshDescription(String welshDescription) {
    this.welshDescription = welshDescription;
  }

  public LocalAuthority welshDescription(String welshDescription) {
    this.welshDescription = welshDescription;
    return this;
  }

  /**
   * name line 2
   *
   * @return nameLine2
   */
  @ApiModelProperty(value = "name line 2")
  public String getNameLine2() {
    return nameLine2;
  }

  public void setNameLine2(String nameLine2) {
    this.nameLine2 = nameLine2;
  }

  public LocalAuthority addressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

  /**
   * address line 1
   *
   * @return addressLine1
   */
  @ApiModelProperty(example = "high street 1", value = "address line 1")
  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public LocalAuthority addressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

  /**
   * address line 2
   *
   * @return addressLine2
   */
  @ApiModelProperty(example = "borough", value = "address line 2")
  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public LocalAuthority addressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
    return this;
  }

  /**
   * address line 3
   *
   * @return addressLine3
   */
  @ApiModelProperty(example = "city", value = "address line 3")
  public String getAddressLine3() {
    return addressLine3;
  }

  public void setAddressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  public LocalAuthority addressLine4(String addressLine4) {
    this.addressLine4 = addressLine4;
    return this;
  }

  /**
   * address line 4
   *
   * @return addressLine4
   */
  @ApiModelProperty(example = "other", value = "address line 4")
  public String getAddressLine4() {
    return addressLine4;
  }

  public void setAddressLine4(String addressLine4) {
    this.addressLine4 = addressLine4;
  }

  public LocalAuthority town(String town) {
    this.town = town;
    return this;
  }

  /**
   * town
   *
   * @return town
   */
  @ApiModelProperty(example = "Manchester", value = "town")
  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public LocalAuthority county(String county) {
    this.county = county;
    return this;
  }

  /**
   * county
   *
   * @return county
   */
  @ApiModelProperty(example = "Lancashire", value = "county")
  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public LocalAuthority postcode(String postcode) {
    this.postcode = postcode;
    return this;
  }

  /**
   * postcode
   *
   * @return postcode
   */
  @ApiModelProperty(example = "SW1P 4DR", value = "postcode")
  @NotBlank
  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public LocalAuthority country(String country) {
    this.country = country;
    return this;
  }

  /**
   * country
   *
   * @return country
   */
  @ApiModelProperty(example = "United Kingdom", required = true, value = "country")
  @NotBlank
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalAuthority nation(String nation) {
    this.nation = nation;
    return this;
  }

  /**
   * ENG, WLS, SCO or NIR
   *
   * @return nation
   */
  @ApiModelProperty(example = "ENG", required = true, value = "ENG, WLS, SCO or NIR")
  @NotBlank
  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public LocalAuthority contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * contact phone number
   *
   * @return contactNumber
   */
  @ApiModelProperty(example = "01632 960493", value = "contact phone number")
  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public LocalAuthority contactUrl(String contactUrl) {
    this.contactUrl = contactUrl;
    return this;
  }

  /**
   * web site url
   *
   * @return contactUrl
   */
  @ApiModelProperty(example = "https://www.gov.uk/", required = true, value = "web site url")
  @NotBlank
  @URL
  public String getContactUrl() {
    return contactUrl;
  }

  public void setContactUrl(String contactUrl) {
    this.contactUrl = contactUrl;
  }

  public LocalAuthority emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * email address
   *
   * @return emailAddress
   */
  @ApiModelProperty(example = "abc@abcdefghijk.co.uk", value = "email address")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public LocalAuthority badgePackType(String badgePackType) {
    this.badgePackType = badgePackType;
    return this;
  }

  /**
   * badge pack type STANDARD, WALLET
   *
   * @return badgePackType
   */
  @ApiModelProperty(example = "STANDARD", value = "badge pack type STANDARD, WALLET")
  public String getBadgePackType() {
    return badgePackType;
  }

  public void setBadgePackType(String badgePackType) {
    this.badgePackType = badgePackType;
  }

  public LocalAuthority badgeCost(BigDecimal badgeCost) {
    this.badgeCost = badgeCost;
    return this;
  }

  /**
   * badge cost minimum: 0.0 maximum: 999.99
   *
   * @return badgeCost
   */
  @ApiModelProperty(example = "100.0", value = "badge cost")
  @Valid
  @DecimalMin("0.0")
  @DecimalMax("999.99")
  public BigDecimal getBadgeCost() {
    return badgeCost;
  }

  public void setBadgeCost(BigDecimal badgeCost) {
    this.badgeCost = badgeCost;
  }

  public LocalAuthority differentServiceSignpostUrl(String differentServiceSignpostUrl) {
    this.differentServiceSignpostUrl = differentServiceSignpostUrl;
    return this;
  }

  /**
   * Service signpost url
   *
   * @return differentServiceSignpostUrl
   */
  @URL
  @ApiModelProperty(example = "http://tiny.url?q=ab63fg", value = "Service signpost url")
  public String getDifferentServiceSignpostUrl() {
    return differentServiceSignpostUrl;
  }

  public void setDifferentServiceSignpostUrl(String differentServiceSignpostUrl) {
    this.differentServiceSignpostUrl = differentServiceSignpostUrl;
  }

  /**
   * payments enabled
   *
   * @return paymentsEnabled
   */
  @ApiModelProperty(value = "payments enabled")
  public Boolean getPaymentsEnabled() {
    return paymentsEnabled;
  }

  public void setPaymentsEnabled(Boolean paymentsEnabled) {
    this.paymentsEnabled = paymentsEnabled;
  }

  public LocalAuthority paymentsEnabled(Boolean paymentsEnabled) {
    this.paymentsEnabled = paymentsEnabled;
    return this;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalAuthority localAuthority = (LocalAuthority) o;
    return Objects.equals(this.description, localAuthority.description)
        && Objects.equals(this.welshDescription, localAuthority.welshDescription)
        && Objects.equals(this.nameLine2, localAuthority.nameLine2)
        && Objects.equals(this.addressLine1, localAuthority.addressLine1)
        && Objects.equals(this.addressLine2, localAuthority.addressLine2)
        && Objects.equals(this.addressLine3, localAuthority.addressLine3)
        && Objects.equals(this.addressLine4, localAuthority.addressLine4)
        && Objects.equals(this.town, localAuthority.town)
        && Objects.equals(this.county, localAuthority.county)
        && Objects.equals(this.postcode, localAuthority.postcode)
        && Objects.equals(this.country, localAuthority.country)
        && Objects.equals(this.nation, localAuthority.nation)
        && Objects.equals(this.contactNumber, localAuthority.contactNumber)
        && Objects.equals(this.contactUrl, localAuthority.contactUrl)
        && Objects.equals(this.emailAddress, localAuthority.emailAddress)
        && Objects.equals(this.badgePackType, localAuthority.badgePackType)
        && Objects.equals(this.paymentsEnabled, localAuthority.paymentsEnabled)
        && Objects.equals(this.badgeCost, localAuthority.badgeCost)
        && Objects.equals(
            this.differentServiceSignpostUrl, localAuthority.differentServiceSignpostUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        description,
        welshDescription,
        nameLine2,
        addressLine1,
        addressLine2,
        addressLine3,
        addressLine4,
        town,
        county,
        postcode,
        country,
        nation,
        contactNumber,
        contactUrl,
        emailAddress,
        badgePackType,
        paymentsEnabled,
        badgeCost,
        differentServiceSignpostUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalAuthority {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    welshDescription: ").append(toIndentedString(welshDescription)).append("\n");
    sb.append("    nameLine2: ").append(toIndentedString(nameLine2)).append("\n");
    sb.append("    addressLine1: ").append(toIndentedString(addressLine1)).append("\n");
    sb.append("    addressLine2: ").append(toIndentedString(addressLine2)).append("\n");
    sb.append("    addressLine3: ").append(toIndentedString(addressLine3)).append("\n");
    sb.append("    addressLine4: ").append(toIndentedString(addressLine4)).append("\n");
    sb.append("    town: ").append(toIndentedString(town)).append("\n");
    sb.append("    county: ").append(toIndentedString(county)).append("\n");
    sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    nation: ").append(toIndentedString(nation)).append("\n");
    sb.append("    contactNumber: ").append(toIndentedString(contactNumber)).append("\n");
    sb.append("    contactUrl: ").append(toIndentedString(contactUrl)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    badgePackType: ").append(toIndentedString(badgePackType)).append("\n");
    sb.append("    paymentsEnabled: ").append(toIndentedString(paymentsEnabled)).append("\n");
    sb.append("    badgeCost: ").append(toIndentedString(badgeCost)).append("\n");
    sb.append("    differentServiceSignpostUrl: ")
        .append(toIndentedString(differentServiceSignpostUrl))
        .append("\n");
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
