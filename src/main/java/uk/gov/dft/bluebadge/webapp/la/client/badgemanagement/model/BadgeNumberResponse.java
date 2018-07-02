package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

/** Returns a badge number. */
@ApiModel(description = "Returns a badge number.")
@Validated
public class BadgeNumberResponse extends CommonResponse {
  @JsonProperty("data")
  private String data = null;

  public BadgeNumberResponse data(String data) {
    this.data = data;
    return this;
  }

  /**
   * The unique badge number for this badge
   *
   * @return data
   */
  @ApiModelProperty(example = "095715", value = "The unique badge number for this badge")
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadgeNumberResponse badgeNumberResponse = (BadgeNumberResponse) o;
    return Objects.equals(this.data, badgeNumberResponse.data) && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadgeNumberResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
