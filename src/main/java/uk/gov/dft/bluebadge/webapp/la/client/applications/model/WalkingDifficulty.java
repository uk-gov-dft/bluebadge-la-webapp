package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** WalkingDifficulty */
@Validated
public class WalkingDifficulty {
  @JsonProperty("typeCodes")
  @Valid
  private List<WalkingDifficultyTypeCodeField> typeCodes = null;

  @JsonProperty("otherDescription")
  private String otherDescription = null;

  @JsonProperty("walkingAids")
  @Valid
  private List<WalkingAid> walkingAids = null;

  @JsonProperty("walkingLengthOfTimeCode")
  private WalkingLengthOfTimeCodeField walkingLengthOfTimeCode = null;

  @JsonProperty("walkingSpeedCode")
  private WalkingSpeedCodeField walkingSpeedCode = null;

  @JsonProperty("treatments")
  @Valid
  private List<Treatment> treatments = null;

  @JsonProperty("medications")
  @Valid
  private List<Medication> medications = null;

  public WalkingDifficulty typeCodes(List<WalkingDifficultyTypeCodeField> typeCodes) {
    this.typeCodes = typeCodes;
    return this;
  }

  public WalkingDifficulty addTypeCodesItem(WalkingDifficultyTypeCodeField typeCodesItem) {
    if (this.typeCodes == null) {
      this.typeCodes = new ArrayList<>();
    }
    this.typeCodes.add(typeCodesItem);
    return this;
  }

  /**
   * 'Short codes from the WALKDIFF group of data. At least 1 required.'
   *
   * @return typeCodes
   */
  @ApiModelProperty(value = "'Short codes from the WALKDIFF group of data.  At least 1 required.' ")
  @Valid
  public List<WalkingDifficultyTypeCodeField> getTypeCodes() {
    return typeCodes;
  }

  public void setTypeCodes(List<WalkingDifficultyTypeCodeField> typeCodes) {
    this.typeCodes = typeCodes;
  }

  public WalkingDifficulty otherDescription(String otherDescription) {
    this.otherDescription = otherDescription;
    return this;
  }

  /**
   * Only entered if something else type selected and even then not required.
   *
   * @return otherDescription
   */
  @ApiModelProperty(
    value = "Only entered if something else type selected and even then not required."
  )
  @Size(max = 100)
  public String getOtherDescription() {
    return otherDescription;
  }

  public void setOtherDescription(String otherDescription) {
    this.otherDescription = otherDescription;
  }

  public WalkingDifficulty walkingAids(List<WalkingAid> walkingAids) {
    this.walkingAids = walkingAids;
    return this;
  }

  public WalkingDifficulty addWalkingAidsItem(WalkingAid walkingAidsItem) {
    if (this.walkingAids == null) {
      this.walkingAids = new ArrayList<>();
    }
    this.walkingAids.add(walkingAidsItem);
    return this;
  }

  /**
   * Get walkingAids
   *
   * @return walkingAids
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<WalkingAid> getWalkingAids() {
    return walkingAids;
  }

  public void setWalkingAids(List<WalkingAid> walkingAids) {
    this.walkingAids = walkingAids;
  }

  public WalkingDifficulty walkingLengthOfTimeCode(
      WalkingLengthOfTimeCodeField walkingLengthOfTimeCode) {
    this.walkingLengthOfTimeCode = walkingLengthOfTimeCode;
    return this;
  }

  /**
   * Get walkingLengthOfTimeCode
   *
   * @return walkingLengthOfTimeCode
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public WalkingLengthOfTimeCodeField getWalkingLengthOfTimeCode() {
    return walkingLengthOfTimeCode;
  }

  public void setWalkingLengthOfTimeCode(WalkingLengthOfTimeCodeField walkingLengthOfTimeCode) {
    this.walkingLengthOfTimeCode = walkingLengthOfTimeCode;
  }

  public WalkingDifficulty walkingSpeedCode(WalkingSpeedCodeField walkingSpeedCode) {
    this.walkingSpeedCode = walkingSpeedCode;
    return this;
  }

  /**
   * Get walkingSpeedCode
   *
   * @return walkingSpeedCode
   */
  @ApiModelProperty(value = "")
  @Valid
  public WalkingSpeedCodeField getWalkingSpeedCode() {
    return walkingSpeedCode;
  }

  public void setWalkingSpeedCode(WalkingSpeedCodeField walkingSpeedCode) {
    this.walkingSpeedCode = walkingSpeedCode;
  }

  public WalkingDifficulty treatments(List<Treatment> treatments) {
    this.treatments = treatments;
    return this;
  }

  public WalkingDifficulty addTreatmentsItem(Treatment treatmentsItem) {
    if (this.treatments == null) {
      this.treatments = new ArrayList<>();
    }
    this.treatments.add(treatmentsItem);
    return this;
  }

  /**
   * Get treatments
   *
   * @return treatments
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<Treatment> getTreatments() {
    return treatments;
  }

  public void setTreatments(List<Treatment> treatments) {
    this.treatments = treatments;
  }

  public WalkingDifficulty medications(List<Medication> medications) {
    this.medications = medications;
    return this;
  }

  public WalkingDifficulty addMedicationsItem(Medication medicationsItem) {
    if (this.medications == null) {
      this.medications = new ArrayList<>();
    }
    this.medications.add(medicationsItem);
    return this;
  }

  /**
   * Get medications
   *
   * @return medications
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<Medication> getMedications() {
    return medications;
  }

  public void setMedications(List<Medication> medications) {
    this.medications = medications;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WalkingDifficulty walkingDifficulty = (WalkingDifficulty) o;
    return Objects.equals(this.typeCodes, walkingDifficulty.typeCodes)
        && Objects.equals(this.otherDescription, walkingDifficulty.otherDescription)
        && Objects.equals(this.walkingAids, walkingDifficulty.walkingAids)
        && Objects.equals(this.walkingLengthOfTimeCode, walkingDifficulty.walkingLengthOfTimeCode)
        && Objects.equals(this.walkingSpeedCode, walkingDifficulty.walkingSpeedCode)
        && Objects.equals(this.treatments, walkingDifficulty.treatments)
        && Objects.equals(this.medications, walkingDifficulty.medications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        typeCodes,
        otherDescription,
        walkingAids,
        walkingLengthOfTimeCode,
        walkingSpeedCode,
        treatments,
        medications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WalkingDifficulty {\n");

    sb.append("    typeCodes: ").append(toIndentedString(typeCodes)).append("\n");
    sb.append("    otherDescription: ").append(toIndentedString(otherDescription)).append("\n");
    sb.append("    walkingAids: ").append(toIndentedString(walkingAids)).append("\n");
    sb.append("    walkingLengthOfTimeCode: ")
        .append(toIndentedString(walkingLengthOfTimeCode))
        .append("\n");
    sb.append("    walkingSpeedCode: ").append(toIndentedString(walkingSpeedCode)).append("\n");
    sb.append("    treatments: ").append(toIndentedString(treatments)).append("\n");
    sb.append("    medications: ").append(toIndentedString(medications)).append("\n");
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
