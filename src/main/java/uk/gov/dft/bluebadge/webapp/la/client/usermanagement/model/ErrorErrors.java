package uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/** ErrorErrors */
@Validated
public class ErrorErrors {
  @JsonProperty("field")
  private String field = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("location")
  private String location = null;

  @JsonProperty("locationType")
  private String locationType = null;

  public ErrorErrors field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   *
   * @return field
   */
  @ApiModelProperty(value = "")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public ErrorErrors reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   *
   * @return reason
   */
  @ApiModelProperty(value = "")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public ErrorErrors message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message
   */
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorErrors location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @ApiModelProperty(value = "")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public ErrorErrors locationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

  /**
   * Get locationType
   *
   * @return locationType
   */
  @ApiModelProperty(value = "")
  public String getLocationType() {
    return locationType;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorErrors errorErrors = (ErrorErrors) o;
    return Objects.equals(this.field, errorErrors.field)
        && Objects.equals(this.reason, errorErrors.reason)
        && Objects.equals(this.message, errorErrors.message)
        && Objects.equals(this.location, errorErrors.location)
        && Objects.equals(this.locationType, errorErrors.locationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, reason, message, location, locationType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorErrors {\n");

    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    locationType: ").append(toIndentedString(locationType)).append("\n");
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
