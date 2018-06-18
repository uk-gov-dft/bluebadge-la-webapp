package uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/** Data */
@Validated
public class Data {
  @JsonProperty("updated")
  private Integer updated = null;

  @JsonProperty("deleted")
  private Integer deleted = null;

  @JsonProperty("totalItems")
  private Integer totalItems = null;

  public Data updated(Integer updated) {
    this.updated = updated;
    return this;
  }

  /**
   * Get updated
   *
   * @return updated
   */
  @ApiModelProperty(value = "")
  public Integer getUpdated() {
    return updated;
  }

  public void setUpdated(Integer updated) {
    this.updated = updated;
  }

  public Data deleted(Integer deleted) {
    this.deleted = deleted;
    return this;
  }

  /**
   * Get deleted
   *
   * @return deleted
   */
  @ApiModelProperty(value = "")
  public Integer getDeleted() {
    return deleted;
  }

  public void setDeleted(Integer deleted) {
    this.deleted = deleted;
  }

  public Data totalItems(Integer totalItems) {
    this.totalItems = totalItems;
    return this;
  }

  /**
   * Get totalItems
   *
   * @return totalItems
   */
  @ApiModelProperty(value = "")
  public Integer getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(Integer totalItems) {
    this.totalItems = totalItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return Objects.equals(this.updated, data.updated)
        && Objects.equals(this.deleted, data.deleted)
        && Objects.equals(this.totalItems, data.totalItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(updated, deleted, totalItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Data {\n");

    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    deleted: ").append(toIndentedString(deleted)).append("\n");
    sb.append("    totalItems: ").append(toIndentedString(totalItems)).append("\n");
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
