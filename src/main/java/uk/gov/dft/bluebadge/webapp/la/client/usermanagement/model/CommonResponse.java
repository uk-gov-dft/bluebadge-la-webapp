package uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/** CommonResponse */
@Validated
public class CommonResponse {
  @JsonProperty("apiVersion")
  private String apiVersion = null;

  @JsonProperty("context")
  private String context = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("method")
  private String method = null;

  @JsonProperty("error")
  private Error error = null;

  public CommonResponse apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  /**
   * Get apiVersion
   *
   * @return apiVersion
   */
  @ApiModelProperty(value = "")
  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public CommonResponse context(String context) {
    this.context = context;
    return this;
  }

  /**
   * Get context
   *
   * @return context
   */
  @ApiModelProperty(value = "")
  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public CommonResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CommonResponse method(String method) {
    this.method = method;
    return this;
  }

  /**
   * Get method
   *
   * @return method
   */
  @ApiModelProperty(value = "")
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public CommonResponse error(Error error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   *
   * @return error
   */
  @ApiModelProperty(value = "")
  @Valid
  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonResponse commonResponse = (CommonResponse) o;
    return Objects.equals(this.apiVersion, commonResponse.apiVersion)
        && Objects.equals(this.context, commonResponse.context)
        && Objects.equals(this.id, commonResponse.id)
        && Objects.equals(this.method, commonResponse.method)
        && Objects.equals(this.error, commonResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiVersion, context, id, method, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonResponse {\n");

    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
