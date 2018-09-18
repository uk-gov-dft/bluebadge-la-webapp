package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Medication */
@Validated
public class Medication {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("isPrescribed")
  private Boolean isPrescribed = null;

  @JsonProperty("frequency")
  private String frequency = null;

  @JsonProperty("quantity")
  private String quantity = null;

  public Medication name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "Paracetamol", value = "")
  @Size(max = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Medication isPrescribed(Boolean isPrescribed) {
    this.isPrescribed = isPrescribed;
    return this;
  }

  /**
   * Get isPrescribed
   *
   * @return isPrescribed
   */
  @ApiModelProperty(example = "true", value = "")
  public Boolean isIsPrescribed() {
    return isPrescribed;
  }

  public void setIsPrescribed(Boolean isPrescribed) {
    this.isPrescribed = isPrescribed;
  }

  public Medication frequency(String frequency) {
    this.frequency = frequency;
    return this;
  }

  /**
   * Get frequency
   *
   * @return frequency
   */
  @ApiModelProperty(example = "Twice daily.", value = "")
  @Size(max = 100)
  public String getFrequency() {
    return frequency;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public Medication quantity(String quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   *
   * @return quantity
   */
  @ApiModelProperty(example = "1 tablet, 20mg", value = "")
  @Size(max = 100)
  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Medication medication = (Medication) o;
    return Objects.equals(this.name, medication.name)
        && Objects.equals(this.isPrescribed, medication.isPrescribed)
        && Objects.equals(this.frequency, medication.frequency)
        && Objects.equals(this.quantity, medication.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isPrescribed, frequency, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Medication {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isPrescribed: ").append(toIndentedString(isPrescribed)).append("\n");
    sb.append("    frequency: ").append(toIndentedString(frequency)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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
