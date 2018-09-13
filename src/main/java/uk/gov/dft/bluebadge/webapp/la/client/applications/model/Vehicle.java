package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Vehicle */
@Validated
public class Vehicle {
  @JsonProperty("registrationNumber")
  private String registrationNumber = null;

  @JsonProperty("typeCode")
  private VehicleTypeCodeField typeCode = null;

  @JsonProperty("usageFrequency")
  private String usageFrequency = null;

  public Vehicle registrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
    return this;
  }

  /**
   * Get registrationNumber
   *
   * @return registrationNumber
   */
  @ApiModelProperty(example = "VK61VZZ", value = "")
  @Size(max = 7)
  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public Vehicle typeCode(VehicleTypeCodeField typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  /**
   * Get typeCode
   *
   * @return typeCode
   */
  @ApiModelProperty(value = "")
  @Valid
  public VehicleTypeCodeField getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(VehicleTypeCodeField typeCode) {
    this.typeCode = typeCode;
  }

  public Vehicle usageFrequency(String usageFrequency) {
    this.usageFrequency = usageFrequency;
    return this;
  }

  /**
   * Get usageFrequency
   *
   * @return usageFrequency
   */
  @ApiModelProperty(example = "Daily", value = "")
  @Size(max = 100)
  public String getUsageFrequency() {
    return usageFrequency;
  }

  public void setUsageFrequency(String usageFrequency) {
    this.usageFrequency = usageFrequency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vehicle vehicle = (Vehicle) o;
    return Objects.equals(this.registrationNumber, vehicle.registrationNumber)
        && Objects.equals(this.typeCode, vehicle.typeCode)
        && Objects.equals(this.usageFrequency, vehicle.usageFrequency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registrationNumber, typeCode, usageFrequency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vehicle {\n");

    sb.append("    registrationNumber: ").append(toIndentedString(registrationNumber)).append("\n");
    sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
    sb.append("    usageFrequency: ").append(toIndentedString(usageFrequency)).append("\n");
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
