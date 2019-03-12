package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Breathlessness */
@Validated
public class Breathlessness {
  @JsonProperty("typeCodes")
  @Valid
  private List<BreathlessnessCodeField> typeCodes = null;

  @JsonProperty("otherDescription")
  private String otherDescription = null;

  public Breathlessness typeCodes(List<BreathlessnessCodeField> typeCodes) {
    this.typeCodes = typeCodes;
    return this;
  }

  public Breathlessness addTypeCodesItem(BreathlessnessCodeField typeCodesItem) {
    if (this.typeCodes == null) {
      this.typeCodes = new ArrayList<>();
    }
    this.typeCodes.add(typeCodesItem);
    return this;
  }

  /**
   * 'Short codes from the BREATHLESS group of data. At least 1 required.'
   *
   * @return typeCodes
   */
  @ApiModelProperty(
    value = "'Short codes from the BREATHLESS group of data.  At least 1 required.' "
  )
  @Valid
  public List<BreathlessnessCodeField> getTypeCodes() {
    return typeCodes;
  }

  public void setTypeCodes(List<BreathlessnessCodeField> typeCodes) {
    this.typeCodes = typeCodes;
  }

  public Breathlessness otherDescription(String otherDescription) {
    this.otherDescription = otherDescription;
    return this;
  }

  /**
   * Only entered if something else type selected and even then not required.
   *
   * @return otherDescription
   */
  @ApiModelProperty(value = "Only entered if OTHER type selected and even then not required.")
  @Size(max = 255)
  public String getOtherDescription() {
    return otherDescription;
  }

  public void setOtherDescription(String otherDescription) {
    this.otherDescription = otherDescription;
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeCodes, otherDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Breathlessness {\n");

    sb.append("    typeCodes: ").append(toIndentedString(typeCodes)).append("\n");
    sb.append("    otherDescription: ").append(toIndentedString(otherDescription)).append("\n");
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
