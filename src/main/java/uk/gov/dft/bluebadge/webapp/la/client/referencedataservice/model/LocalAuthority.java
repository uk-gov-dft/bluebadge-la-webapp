package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

/** LocalAuthority */
@Validated
public class LocalAuthority {
  @JsonProperty("differentServiceSignpostUrl")
  private String differentServiceSignpostUrl = null;

  public LocalAuthority differentServiceSignpostUrl(String differentServiceSignpostUrl) {
    this.differentServiceSignpostUrl = differentServiceSignpostUrl;
    return this;
  }

  /**
   * Service signpost url.
   *
   * @return differentServiceSignpostUrl
   */
  @ApiModelProperty(example = "http : //tiny.url?q=ab63fg", value = "Service signpost url.")
  @Size(max = 255)
  @URL
  public String getDifferentServiceSignpostUrl() {
    return differentServiceSignpostUrl;
  }

  public void setDifferentServiceSignpostUrl(String differentServiceSignpostUrl) {
    this.differentServiceSignpostUrl = differentServiceSignpostUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalAuthority localAuthority = (LocalAuthority) o;
    return Objects.equals(
        this.differentServiceSignpostUrl, localAuthority.differentServiceSignpostUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(differentServiceSignpostUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalAuthority {\n");

    sb.append("    differentServiceSignpostUrl: ")
        .append(toIndentedString(differentServiceSignpostUrl))
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
