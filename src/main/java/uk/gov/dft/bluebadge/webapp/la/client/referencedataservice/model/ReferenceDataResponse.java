package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;

/** ReferenceDataResponse */
@Validated
public class ReferenceDataResponse extends CommonResponse {
  @JsonProperty("data")
  @Valid
  private List<ReferenceData> data = null;

  public ReferenceDataResponse data(List<ReferenceData> data) {
    this.data = data;
    return this;
  }

  public ReferenceDataResponse addDataItem(ReferenceData dataItem) {
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
  public List<ReferenceData> getData() {
    return data;
  }

  public void setData(List<ReferenceData> data) {
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
    ReferenceDataResponse referenceDataResponse = (ReferenceDataResponse) o;
    return Objects.equals(this.data, referenceDataResponse.data) && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenceDataResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}