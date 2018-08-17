package uk.gov.dft.bluebadge.webapp.la.client.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;

/** ApplicationSummaryResponse */
@Validated
public class ApplicationSummaryResponse extends CommonResponse {
  @JsonProperty("data")
  @Valid
  private List<ApplicationSummary> data = null;

  public ApplicationSummaryResponse data(List<ApplicationSummary> data) {
    this.data = data;
    return this;
  }

  public ApplicationSummaryResponse addDataItem(ApplicationSummary dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   *
   * @return data
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<ApplicationSummary> getData() {
    return data;
  }

  public void setData(List<ApplicationSummary> data) {
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
    ApplicationSummaryResponse applicationSummaryResponse = (ApplicationSummaryResponse) o;
    return Objects.equals(this.data, applicationSummaryResponse.data) && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationSummaryResponse {\n");
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
