package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** DisabilityArms */
@Validated
public class DisabilityArms {
  @JsonProperty("drivingFrequency")
  private String drivingFrequency = null;

  @JsonProperty("isAdaptedVehicle")
  private Boolean isAdaptedVehicle = null;

  @JsonProperty("adaptedVehicleDescription")
  private String adaptedVehicleDescription = null;

  public DisabilityArms drivingFrequency(String drivingFrequency) {
    this.drivingFrequency = drivingFrequency;
    return this;
  }

  /**
   * Get drivingFrequency
   *
   * @return drivingFrequency
   */
  @ApiModelProperty(value = "")
  @Size(max = 100)
  public String getDrivingFrequency() {
    return drivingFrequency;
  }

  public void setDrivingFrequency(String drivingFrequency) {
    this.drivingFrequency = drivingFrequency;
  }

  public DisabilityArms isAdaptedVehicle(Boolean isAdaptedVehicle) {
    this.isAdaptedVehicle = isAdaptedVehicle;
    return this;
  }

  /**
   * Get isAdaptedVehicle
   *
   * @return isAdaptedVehicle
   */
  @ApiModelProperty(example = "true", required = true, value = "")
  @NotNull
  public Boolean isIsAdaptedVehicle() {
    return isAdaptedVehicle;
  }

  public void setIsAdaptedVehicle(Boolean isAdaptedVehicle) {
    this.isAdaptedVehicle = isAdaptedVehicle;
  }

  public DisabilityArms adaptedVehicleDescription(String adaptedVehicleDescription) {
    this.adaptedVehicleDescription = adaptedVehicleDescription;
    return this;
  }

  /**
   * Get adaptedVehicleDescription
   *
   * @return adaptedVehicleDescription
   */
  @ApiModelProperty(value = "")
  @Size(max = 100)
  public String getAdaptedVehicleDescription() {
    return adaptedVehicleDescription;
  }

  public void setAdaptedVehicleDescription(String adaptedVehicleDescription) {
    this.adaptedVehicleDescription = adaptedVehicleDescription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DisabilityArms disabilityArms = (DisabilityArms) o;
    return Objects.equals(this.drivingFrequency, disabilityArms.drivingFrequency)
        && Objects.equals(this.isAdaptedVehicle, disabilityArms.isAdaptedVehicle)
        && Objects.equals(this.adaptedVehicleDescription, disabilityArms.adaptedVehicleDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(drivingFrequency, isAdaptedVehicle, adaptedVehicleDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DisabilityArms {\n");

    sb.append("    drivingFrequency: ").append(toIndentedString(drivingFrequency)).append("\n");
    sb.append("    isAdaptedVehicle: ").append(toIndentedString(isAdaptedVehicle)).append("\n");
    sb.append("    adaptedVehicleDescription: ")
        .append(toIndentedString(adaptedVehicleDescription))
        .append("\n");
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
