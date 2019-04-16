package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** ApplicationSummary */
@Validated
public class ApplicationSummary {
  @JsonProperty("applicationId")
  private String applicationId = null;

  @JsonProperty("partyTypeCode")
  private PartyTypeCodeField partyTypeCode = null;

  @JsonProperty("applicationTypeCode")
  private ApplicationTypeCodeField applicationTypeCode = null;

  @JsonProperty("nino")
  private String nino = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("dob")
  private LocalDate dob = null;

  @JsonProperty("submissionDate")
  private OffsetDateTime submissionDate = null;

  @JsonProperty("eligibilityCode")
  private EligibilityCodeField eligibilityCode = null;

  @JsonProperty("applicationStatus")
  private ApplicationStatusField applicationStatus;

  public ApplicationSummary applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  /**
   * The unique badge number for this application - a UUID
   *
   * @return applicationId
   */
  @ApiModelProperty(
    example = "12345678-1234-1234-1234-123412341234",
    value = "The unique badge number for this application - a UUID"
  )
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public ApplicationSummary partyTypeCode(PartyTypeCodeField partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
    return this;
  }

  /**
   * Get partyTypeCode
   *
   * @return partyTypeCode
   */
  @ApiModelProperty(value = "")
  @Valid
  public PartyTypeCodeField getPartyTypeCode() {
    return partyTypeCode;
  }

  public void setPartyTypeCode(PartyTypeCodeField partyTypeCode) {
    this.partyTypeCode = partyTypeCode;
  }

  public ApplicationSummary applicationTypeCode(ApplicationTypeCodeField applicationTypeCode) {
    this.applicationTypeCode = applicationTypeCode;
    return this;
  }

  /**
   * Get applicationTypeCode
   *
   * @return applicationTypeCode
   */
  @ApiModelProperty(value = "")
  @Valid
  public ApplicationTypeCodeField getApplicationTypeCode() {
    return applicationTypeCode;
  }

  public void setApplicationTypeCode(ApplicationTypeCodeField applicationTypeCode) {
    this.applicationTypeCode = applicationTypeCode;
  }

  public ApplicationSummary nino(String nino) {
    this.nino = nino;
    return this;
  }

  /**
   * The badgeholders national insurance number
   *
   * @return nino
   */
  @ApiModelProperty(example = "NS123458S", value = "The badgeholders national insurance number")
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

  public ApplicationSummary name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the badge holder Organisation or Person
   *
   * @return name
   */
  @ApiModelProperty(
    example = "John Smith",
    value = "The name of the badge holder Organisation or Person"
  )
  @Size(max = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ApplicationSummary dob(LocalDate dob) {
    this.dob = dob;
    return this;
  }

  /**
   * Date of birth YYYY-MM-DD
   *
   * @return dob
   */
  @ApiModelProperty(example = "1970-05-29", required = true, value = "Date of birth YYYY-MM-DD")
  @Valid
  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public ApplicationSummary submissionDate(OffsetDateTime submissionDate) {
    this.submissionDate = submissionDate;
    return this;
  }

  /**
   * Submitted date and time
   *
   * @return submissionDate
   */
  @ApiModelProperty(example = "2018-12-25T12:30:45Z", value = "Submitted date and time")
  @Valid
  public OffsetDateTime getSubmissionDate() {
    return submissionDate;
  }

  public void setSubmissionDate(OffsetDateTime submissionDate) {
    this.submissionDate = submissionDate;
  }

  public ApplicationSummary eligibilityCode(EligibilityCodeField eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
    return this;
  }

  /**
   * Get eligibilityCode
   *
   * @return eligibilityCode
   */
  @ApiModelProperty(value = "")
  @Valid
  public EligibilityCodeField getEligibilityCode() {
    return eligibilityCode;
  }

  public void setEligibilityCode(EligibilityCodeField eligibilityCode) {
    this.eligibilityCode = eligibilityCode;
  }

  public ApplicationSummary applicationStatus(ApplicationStatusField applicationStatus) {
    this.applicationStatus = applicationStatus;
    return this;
  }

  public ApplicationStatusField getApplicationStatus() {
    return applicationStatus;
  }

  public void setApplicationStatus(ApplicationStatusField applicationStatus) {
    this.applicationStatus = applicationStatus;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationSummary applicationSummary = (ApplicationSummary) o;
    return Objects.equals(this.applicationId, applicationSummary.applicationId)
        && Objects.equals(this.partyTypeCode, applicationSummary.partyTypeCode)
        && Objects.equals(this.applicationTypeCode, applicationSummary.applicationTypeCode)
        && Objects.equals(this.nino, applicationSummary.nino)
        && Objects.equals(this.name, applicationSummary.name)
        && Objects.equals(this.dob, applicationSummary.dob)
        && Objects.equals(this.submissionDate, applicationSummary.submissionDate)
        && Objects.equals(this.eligibilityCode, applicationSummary.eligibilityCode)
        && Objects.equals(this.applicationStatus, applicationSummary.applicationStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        applicationId,
        partyTypeCode,
        applicationTypeCode,
        nino,
        name,
        dob,
        submissionDate,
        eligibilityCode,
        applicationStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationSummary {\n");

    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    partyTypeCode: ").append(toIndentedString(partyTypeCode)).append("\n");
    sb.append("    applicationTypeCode: ")
        .append(toIndentedString(applicationTypeCode))
        .append("\n");
    sb.append("    nino: ").append(toIndentedString(nino)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
    sb.append("    submissionDate: ").append(toIndentedString(submissionDate)).append("\n");
    sb.append("    eligibilityCode: ").append(toIndentedString(eligibilityCode)).append("\n");
    sb.append("    applicationStatus: ").append(toIndentedString(applicationStatus)).append("\n");
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
