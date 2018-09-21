package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** WalkingAid */
@Validated
public class WalkingAid {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("usage")
  private String usage = null;

  @JsonProperty("howProvidedCode")
  private HowProvidedCodeField howProvidedCode = null;

  public WalkingAid description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description
   */
  @ApiModelProperty(value = "")
  @Size(max = 100)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public WalkingAid usage(String usage) {
    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   *
   * @return usage
   */
  @ApiModelProperty(value = "")
  @Size(max = 100)
  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public WalkingAid howProvidedCode(HowProvidedCodeField howProvidedCode) {
    this.howProvidedCode = howProvidedCode;
    return this;
  }

  /**
   * Get howProvidedCode
   *
   * @return howProvidedCode
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public HowProvidedCodeField getHowProvidedCode() {
    return howProvidedCode;
  }

  public void setHowProvidedCode(HowProvidedCodeField howProvidedCode) {
    this.howProvidedCode = howProvidedCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WalkingAid walkingAid = (WalkingAid) o;
    return Objects.equals(this.description, walkingAid.description)
        && Objects.equals(this.usage, walkingAid.usage)
        && Objects.equals(this.howProvidedCode, walkingAid.howProvidedCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, usage, howProvidedCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WalkingAid {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("    howProvidedCode: ").append(toIndentedString(howProvidedCode)).append("\n");
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
