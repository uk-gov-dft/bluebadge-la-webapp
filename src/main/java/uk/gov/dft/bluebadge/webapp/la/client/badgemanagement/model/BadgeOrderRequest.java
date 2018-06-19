package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** BadgeOrderRequest */
@Validated
public class BadgeOrderRequest {
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

  @JsonProperty("startDate")
  private LocalDate startDate = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  @JsonProperty("eligibilityCode")
  private String eligibilityCode = null;

  @JsonProperty("imageFile")
  private byte[] imageFile = null;

  @JsonProperty("deliverToCode")
  private String deliverToCode = null;

  @JsonProperty("deliveryOptionCode")
  private String deliveryOptionCode = null;

  @JsonProperty("numberOfBadges")
  private Integer numberOfBadges = null;

  public BadgeOrderRequest partyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
    return this;
  }

  /**
   * Code for Person or Organisation
   *
   * @return partyTypeCode
   */
  @ApiModelProperty(required = true, value = "Code for Person or Organisation ")
  @NotNull
  @Size(max = 10)
  public String getPartyTypeCode() {
    return partyTypeCode;
  }

  public void setPartyTypeCode(String partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
  }

  public BadgeOrderRequest nationalInsurance(String nationalInsurance) {
    this.nationalInsurance = nationalInsurance;
    return this;
  }

  /**
   * The badgeholders national insurance number
   *
   * @return nationalInsurance
   */
  @ApiModelProperty(value = "The badgeholders national insurance number")
  @Pattern(
    regexp =
        "^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])(?:\\s*\\d\\s*){6}([A-D]|\\s)$"
  )
  public String getNationalInsurance() {
    return nationalInsurance;
  }

  public void setNationalInsurance(String nationalInsurance) {
    this.nationalInsurance = nationalInsurance;
  }

  public BadgeOrderRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the badge holder Organisation or Person
   *
   * @return name
   */
  @ApiModelProperty(required = true, value = "The name of the badge holder Organisation or Person")
  @NotNull
  @Size(max = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BadgeOrderRequest homeAddress(Address homeAddress) {
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

  public BadgeOrderRequest contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * Get contactNumber
   *
   * @return contactNumber
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Pattern(
    regexp =
        "^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$"
  )
  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public BadgeOrderRequest emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Get emailAddress
   *
   * @return emailAddress
   */
  @ApiModelProperty(value = "")
  @Pattern(
    regexp =
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
  )
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public BadgeOrderRequest dob(LocalDate dob) {
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

  public BadgeOrderRequest localAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
    return this;
  }

  /**
   * Id of Local Authority
   *
   * @return localAuthorityId
   */
  @ApiModelProperty(required = true, value = "Id of Local Authority")
  @NotNull
  public Integer getLocalAuthorityId() {
    return localAuthorityId;
  }

  public void setLocalAuthorityId(Integer localAuthorityId) {
    this.localAuthorityId = localAuthorityId;
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
  @ApiModelProperty(value = "")
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
   * Get applicationDate
   *
   * @return applicationDate
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public LocalDate getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public BadgeOrderRequest applicationSourceCode(String applicationSourceCode) {
    this.applicationSourceCode = applicationSourceCode;
    return this;
  }

  /**
   * Code for Online, Paper, Phone or In person
   *
   * @return applicationSourceCode
   */
  @ApiModelProperty(required = true, value = "Code for Online, Paper, Phone or In person ")
  @NotNull
  @Size(max = 10)
  public String getApplicationSourceCode() {
    return applicationSourceCode;
  }

  public void setApplicationSourceCode(String applicationSourceCode) {
    this.applicationSourceCode = applicationSourceCode;
  }

  public BadgeOrderRequest startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   *
   * @return startDate
   */
  @ApiModelProperty(required = true, value = "")
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
   * The date the blue bagde expires
   *
   * @return expiryDate
   */
  @ApiModelProperty(required = true, value = "The date the blue bagde expires")
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
   * Reason for badge
   *
   * @return eligibilityCode
   */
  @ApiModelProperty(value = "Reason for badge")
  @Size(max = 10)
  public String getEligibilityCode() {
    return eligibilityCode;
  }

  public void setEligibilityCode(String eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
  }

  public BadgeOrderRequest imageFile(byte[] imageFile) {
    this.imageFile = imageFile;
    return this;
  }

  /**
   * Base64 encoded image
   *
   * @return imageFile
   */
  @ApiModelProperty(value = "Base64 encoded image")
  @Pattern(regexp = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$")
  public byte[] getImageFile() {
    return imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }

  public BadgeOrderRequest deliverToCode(String deliverToCode) {
    this.deliverToCode = deliverToCode;
    return this;
  }

  /**
   * Code for either Home address or LA address
   *
   * @return deliverToCode
   */
  @ApiModelProperty(required = true, value = "Code for either Home address or LA address")
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
   * Code for either Standard or Fast track
   *
   * @return deliveryOptionCode
   */
  @ApiModelProperty(required = true, value = "Code for either Standard or Fast track")
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
   * Get numberOfBadges minimum: 1
   *
   * @return numberOfBadges
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Min(1)
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
    return Objects.equals(this.partyTypeCode, badgeOrderRequest.partyTypeCode)
        && Objects.equals(this.nationalInsurance, badgeOrderRequest.nationalInsurance)
        && Objects.equals(this.name, badgeOrderRequest.name)
        && Objects.equals(this.homeAddress, badgeOrderRequest.homeAddress)
        && Objects.equals(this.contactNumber, badgeOrderRequest.contactNumber)
        && Objects.equals(this.emailAddress, badgeOrderRequest.emailAddress)
        && Objects.equals(this.dob, badgeOrderRequest.dob)
        && Objects.equals(this.localAuthorityId, badgeOrderRequest.localAuthorityId)
        && Objects.equals(this.localAuthorityRef, badgeOrderRequest.localAuthorityRef)
        && Objects.equals(this.applicationDate, badgeOrderRequest.applicationDate)
        && Objects.equals(this.applicationSourceCode, badgeOrderRequest.applicationSourceCode)
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
