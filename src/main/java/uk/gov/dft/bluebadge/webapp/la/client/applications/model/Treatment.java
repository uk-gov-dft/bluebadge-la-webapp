package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Treatment */
@Validated
public class Treatment {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("time")
  private String time = null;

  public Treatment description(String description) {
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

  public Treatment time(String time) {
    this.time = time;
    return this;
  }

  /**
   * Get time
   *
   * @return time
   */
  @ApiModelProperty(example = "6 months", value = "")
  @Size(max = 100)
  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Treatment treatment = (Treatment) o;
    return Objects.equals(this.description, treatment.description)
        && Objects.equals(this.time, treatment.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Treatment {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
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
