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

/** Eligibility */
@Validated
public class Eligibility {
  @JsonProperty("typeCode")
  private EligibilityCodeField typeCode = null;

  @JsonProperty("descriptionOfConditions")
  private String descriptionOfConditions = null;

  @JsonProperty("benefit")
  private Benefit benefit = null;

  @JsonProperty("walkingDifficulty")
  private WalkingDifficulty walkingDifficulty = null;

  @JsonProperty("disabilityArms")
  private DisabilityArms disabilityArms = null;

  @JsonProperty("healthcareProfessionals")
  @Valid
  private List<HealthcareProfessional> healthcareProfessionals = null;

  @JsonProperty("blind")
  private Blind blind = null;

  @JsonProperty("childUnder3")
  private ChildUnder3 childUnder3 = null;

  public Eligibility typeCode(EligibilityCodeField typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  /**
   * Get typeCode
   *
   * @return typeCode
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public EligibilityCodeField getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(EligibilityCodeField typeCode) {
    this.typeCode = typeCode;
  }

  public Eligibility descriptionOfConditions(String descriptionOfConditions) {
    this.descriptionOfConditions = descriptionOfConditions;
    return this;
  }

  /**
   * Get descriptionOfConditions
   *
   * @return descriptionOfConditions
   */
  @ApiModelProperty(example = "Freetext", value = "")
  @Size(max = 10000)
  public String getDescriptionOfConditions() {
    return descriptionOfConditions;
  }

  public void setDescriptionOfConditions(String descriptionOfConditions) {
    this.descriptionOfConditions = descriptionOfConditions;
  }

  public Eligibility benefit(Benefit benefit) {
    this.benefit = benefit;
    return this;
  }

  /**
   * Get benefit
   *
   * @return benefit
   */
  @ApiModelProperty(value = "")
  @Valid
  public Benefit getBenefit() {
    return benefit;
  }

  public void setBenefit(Benefit benefit) {
    this.benefit = benefit;
  }

  public Eligibility walkingDifficulty(WalkingDifficulty walkingDifficulty) {
    this.walkingDifficulty = walkingDifficulty;
    return this;
  }

  /**
   * Get walkingDifficulty
   *
   * @return walkingDifficulty
   */
  @ApiModelProperty(value = "")
  @Valid
  public WalkingDifficulty getWalkingDifficulty() {
    return walkingDifficulty;
  }

  public void setWalkingDifficulty(WalkingDifficulty walkingDifficulty) {
    this.walkingDifficulty = walkingDifficulty;
  }

  public Eligibility disabilityArms(DisabilityArms disabilityArms) {
    this.disabilityArms = disabilityArms;
    return this;
  }

  /**
   * Get disabilityArms
   *
   * @return disabilityArms
   */
  @ApiModelProperty(value = "")
  @Valid
  public DisabilityArms getDisabilityArms() {
    return disabilityArms;
  }

  public void setDisabilityArms(DisabilityArms disabilityArms) {
    this.disabilityArms = disabilityArms;
  }

  public Eligibility healthcareProfessionals(List<HealthcareProfessional> healthcareProfessionals) {
    this.healthcareProfessionals = healthcareProfessionals;
    return this;
  }

  public Eligibility addHealthcareProfessionalsItem(
      HealthcareProfessional healthcareProfessionalsItem) {
    if (this.healthcareProfessionals == null) {
      this.healthcareProfessionals = new ArrayList<>();
    }
    this.healthcareProfessionals.add(healthcareProfessionalsItem);
    return this;
  }

  /**
   * Get healthcareProfessionals
   *
   * @return healthcareProfessionals
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<HealthcareProfessional> getHealthcareProfessionals() {
    return healthcareProfessionals;
  }

  public void setHealthcareProfessionals(List<HealthcareProfessional> healthcareProfessionals) {
    this.healthcareProfessionals = healthcareProfessionals;
  }

  public Eligibility blind(Blind blind) {
    this.blind = blind;
    return this;
  }

  /**
   * Get blind
   *
   * @return blind
   */
  @ApiModelProperty(value = "")
  @Valid
  public Blind getBlind() {
    return blind;
  }

  public void setBlind(Blind blind) {
    this.blind = blind;
  }

  public Eligibility childUnder3(ChildUnder3 childUnder3) {
    this.childUnder3 = childUnder3;
    return this;
  }

  /**
   * Get childUnder3
   *
   * @return childUnder3
   */
  @ApiModelProperty(value = "")
  @Valid
  public ChildUnder3 getChildUnder3() {
    return childUnder3;
  }

  public void setChildUnder3(ChildUnder3 childUnder3) {
    this.childUnder3 = childUnder3;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Eligibility eligibility = (Eligibility) o;
    return Objects.equals(this.typeCode, eligibility.typeCode)
        && Objects.equals(this.descriptionOfConditions, eligibility.descriptionOfConditions)
        && Objects.equals(this.benefit, eligibility.benefit)
        && Objects.equals(this.walkingDifficulty, eligibility.walkingDifficulty)
        && Objects.equals(this.disabilityArms, eligibility.disabilityArms)
        && Objects.equals(this.healthcareProfessionals, eligibility.healthcareProfessionals)
        && Objects.equals(this.blind, eligibility.blind)
        && Objects.equals(this.childUnder3, eligibility.childUnder3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        typeCode,
        descriptionOfConditions,
        benefit,
        walkingDifficulty,
        disabilityArms,
        healthcareProfessionals,
        blind,
        childUnder3);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Eligibility {\n");

    sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
    sb.append("    descriptionOfConditions: ")
        .append(toIndentedString(descriptionOfConditions))
        .append("\n");
    sb.append("    benefit: ").append(toIndentedString(benefit)).append("\n");
    sb.append("    walkingDifficulty: ").append(toIndentedString(walkingDifficulty)).append("\n");
    sb.append("    disabilityArms: ").append(toIndentedString(disabilityArms)).append("\n");
    sb.append("    healthcareProfessionals: ")
        .append(toIndentedString(healthcareProfessionals))
        .append("\n");
    sb.append("    blind: ").append(toIndentedString(blind)).append("\n");
    sb.append("    childUnder3: ").append(toIndentedString(childUnder3)).append("\n");
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
