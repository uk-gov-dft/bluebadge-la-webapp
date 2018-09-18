package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** ChildUnder3 */
@Validated
public class ChildUnder3 {
  @JsonProperty("bulkyMedicalEquipmentTypeCode")
  private BulkyMedicalEquipmentTypeCodeField bulkyMedicalEquipmentTypeCode = null;

  public ChildUnder3 bulkyMedicalEquipmentTypeCode(
      BulkyMedicalEquipmentTypeCodeField bulkyMedicalEquipmentTypeCode) {
    this.bulkyMedicalEquipmentTypeCode = bulkyMedicalEquipmentTypeCode;
    return this;
  }

  /**
   * Get bulkyMedicalEquipmentTypeCode
   *
   * @return bulkyMedicalEquipmentTypeCode
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public BulkyMedicalEquipmentTypeCodeField getBulkyMedicalEquipmentTypeCode() {
    return bulkyMedicalEquipmentTypeCode;
  }

  public void setBulkyMedicalEquipmentTypeCode(
      BulkyMedicalEquipmentTypeCodeField bulkyMedicalEquipmentTypeCode) {
    this.bulkyMedicalEquipmentTypeCode = bulkyMedicalEquipmentTypeCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChildUnder3 childUnder3 = (ChildUnder3) o;
    return Objects.equals(
        this.bulkyMedicalEquipmentTypeCode, childUnder3.bulkyMedicalEquipmentTypeCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bulkyMedicalEquipmentTypeCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChildUnder3 {\n");

    sb.append("    bulkyMedicalEquipmentTypeCode: ")
        .append(toIndentedString(bulkyMedicalEquipmentTypeCode))
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
