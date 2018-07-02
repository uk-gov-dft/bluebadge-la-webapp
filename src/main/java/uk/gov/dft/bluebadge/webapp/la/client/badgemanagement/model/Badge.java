package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.Address;

/** Badge */
@Validated
public class Badge {
  @JsonProperty("badgeNumber")
  private String badgeNumber = null;

  @JsonProperty("partyTypeCode")
  private String partyTypeCode = null;

  @JsonProperty("nationalInsurance")
  private String nationalInsurance = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("homeAddress")
  private Address homeAddress = null;

  @JsonProperty("contactNumber")
  private String contactNumber = null;

  @JsonProperty("emailAddress")
  private String emailAddress = null;

  @JsonProperty("dob")
  private LocalDate dob = null;

  @JsonProperty("localAuthorityId")
  private Integer localAuthorityId = null;

  @JsonProperty("localAuthorityRef")
  private String localAuthorityRef = null;

  @JsonProperty("applicationDate")
  private LocalDate applicationDate = null;

  @JsonProperty("applicationSourceCode")
  private String applicationSourceCode = null;

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

  @JsonProperty("statusCode")
  private String statusCode = null;

  public Badge badgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  /**
   * The unique badge number for this badge
   *
   * @return badgeNumber
   */
  @ApiModelProperty(example = "091215", value = "The unique badge number for this badge")
  public String getBadgeNumber() {
    return badgeNumber;
  }

  public void setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
  }

  public Badge partyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
    return this;
  }

  /**
   * Code for Person or Organisation
   *
   * @return partyTypeCode
   */
  @ApiModelProperty(value = "Code for Person or Organisation ")
  public String getPartyTypeCode() {
    return partyTypeCode;
  }

  public void setPartyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
  }

  public Badge nationalInsurance(String nationalInsurance) {
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

  public Badge name(String name) {
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

  public Badge homeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
    return this;
  }

  /**
   * Get homeAddress
   *
   * @return homeAddress
   */
  @ApiModelProperty(value = "")
  @Valid
  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

  public Badge contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * Get contactNumber
   *
   * @return contactNumber
   */
  @ApiModelProperty(value = "")
  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public Badge emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Get emailAddress
   *
   * @return emailAddress
   */
  @ApiModelProperty(value = "")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public Badge dob(LocalDate dob) {
    this.dob = dob;
    return this;
  }

  /**
   * The badgeholders date of birth
   *
   * @return dob
   */
  @ApiModelProperty(value = "The badgeholders date of birth")
  @Valid
  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public Badge localAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
    return this;
  }

  /**
   * Id of Local Authority
   *
   * @return localAuthorityId
   */
  @ApiModelProperty(value = "Id of Local Authority")
  public Integer getLocalAuthorityId() {
    return localAuthorityId;
  }

  public void setLocalAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
  }

  public Badge localAuthorityRef(String localAuthorityRef) {
    this.localAuthorityRef = localAuthorityRef;
    return this;
  }

  /**
   * Get localAuthorityRef
   *
   * @return localAuthorityRef
   */
  @ApiModelProperty(value = "")
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
   * Get applicationDate
   *
   * @return applicationDate
   */
  @ApiModelProperty(value = "")
  @Valid
  public LocalDate getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public Badge applicationSourceCode(String applicationSourceCode) {
    this.applicationSourceCode = applicationSourceCode;
    return this;
  }

  /**
   * Code for Online, Paper, Phone or In person
   *
   * @return applicationSourceCode
   */
  @ApiModelProperty(value = "Code for Online, Paper, Phone or In person ")
  public String getApplicationSourceCode() {
    return applicationSourceCode;
  }

  public void setApplicationSourceCode(String applicationSourceCode) {
    this.applicationSourceCode = applicationSourceCode;
  }

  public Badge orderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
    return this;
  }

  /**
   * Get orderDate
   *
   * @return orderDate
   */
  @ApiModelProperty(value = "")
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
   * Get startDate
   *
   * @return startDate
   */
  @ApiModelProperty(value = "")
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

  public Badge eligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
    return this;
  }

  /**
   * Reason for badge
   *
   * @return eligibilityCode
   */
  @ApiModelProperty(value = "Reason for badge")
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
   * Link to photo of badge holder if applicable
   *
   * @return imageLink
   */
  @ApiModelProperty(value = "Link to photo of badge holder if applicable")
  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Badge statusCode(String statusCode) {
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
        && Objects.equals(this.partyTypeCode, badge.partyTypeCode)
        && Objects.equals(this.nationalInsurance, badge.nationalInsurance)
        && Objects.equals(this.name, badge.name)
        && Objects.equals(this.homeAddress, badge.homeAddress)
        && Objects.equals(this.contactNumber, badge.contactNumber)
        && Objects.equals(this.emailAddress, badge.emailAddress)
        && Objects.equals(this.dob, badge.dob)
        && Objects.equals(this.localAuthorityId, badge.localAuthorityId)
        && Objects.equals(this.localAuthorityRef, badge.localAuthorityRef)
        && Objects.equals(this.applicationDate, badge.applicationDate)
        && Objects.equals(this.applicationSourceCode, badge.applicationSourceCode)
        && Objects.equals(this.orderDate, badge.orderDate)
        && Objects.equals(this.startDate, badge.startDate)
        && Objects.equals(this.expiryDate, badge.expiryDate)
        && Objects.equals(this.eligibilityCode, badge.eligibilityCode)
        && Objects.equals(this.imageLink, badge.imageLink)
        && Objects.equals(this.statusCode, badge.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        badgeNumber,
        partyTypeCode,
        nationalInsurance,
        name,
        homeAddress,
        contactNumber,
        emailAddress,
        dob,
        localAuthorityId,
        localAuthorityRef,
        applicationDate,
        applicationSourceCode,
        orderDate,
        startDate,
        expiryDate,
        eligibilityCode,
        imageLink,
        statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Badge {\n");

    sb.append("    badgeNumber: ").append(toIndentedString(badgeNumber)).append("\n");
    sb.append("    partyTypeCode: ").append(toIndentedString(partyTypeCode)).append("\n");
    sb.append("    nationalInsurance: ").append(toIndentedString(nationalInsurance)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    homeAddress: ").append(toIndentedString(homeAddress)).append("\n");
    sb.append("    contactNumber: ").append(toIndentedString(contactNumber)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
    sb.append("    localAuthorityId: ").append(toIndentedString(localAuthorityId)).append("\n");
    sb.append("    localAuthorityRef: ").append(toIndentedString(localAuthorityRef)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    applicationSourceCode: ")
        .append(toIndentedString(applicationSourceCode))
        .append("\n");
    sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    eligibilityCode: ").append(toIndentedString(eligibilityCode)).append("\n");
    sb.append("    imageLink: ").append(toIndentedString(imageLink)).append("\n");
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
}
