package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/** ReferenceData */
@Validated
public class ReferenceData implements Serializable {
  @JsonProperty("shortCode")
  private String shortCode = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("groupShortCode")
  private String groupShortCode = null;

  @JsonProperty("groupDescription")
  private String groupDescription = null;

  @JsonProperty("subgroupShortCode")
  private String subgroupShortCode = null;

  @JsonProperty("subgroupDescription")
  private String subgroupDescription = null;

  @JsonProperty("displayOrder")
  private Integer displayOrder = null;

  public ReferenceData shortCode(String shortCode) {
    this.shortCode = shortCode;
    return this;
  }

  /**
   * Identifier of data item
   *
   * @return shortCode
   */
  @ApiModelProperty(example = "AFRFCS", value = "Identifier of data item")
  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode(String shortCode) {
    this.shortCode = shortCode;
  }

  public ReferenceData description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Display value of data item
   *
   * @return description
   */
  @ApiModelProperty(
    example = "Armed Forces Compensation scheme",
    value = "Display value of data item"
  )
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ReferenceData groupShortCode(String groupShortCode) {
    this.groupShortCode = groupShortCode;
    return this;
  }

  /**
   * Group of data.
   *
   * @return groupShortCode
   */
  @ApiModelProperty(example = "ELIGIBILIT", value = "Group of data.")
  public String getGroupShortCode() {
    return groupShortCode;
  }

  public void setGroupShortCode(String groupShortCode) {
    this.groupShortCode = groupShortCode;
  }

  public ReferenceData groupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
    return this;
  }

  /**
   * Description of group
   *
   * @return groupDescription
   */
  @ApiModelProperty(example = "Eligibility", value = "Description of group")
  public String getGroupDescription() {
    return groupDescription;
  }

  public void setGroupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
  }

  public ReferenceData subgroupShortCode(String subgroupShortCode) {
    this.subgroupShortCode = subgroupShortCode;
    return this;
  }

  /**
   * Id of a subgroup
   *
   * @return subgroupShortCode
   */
  @ApiModelProperty(example = "ELIG_AUTO", value = "Id of a subgroup")
  public String getSubgroupShortCode() {
    return subgroupShortCode;
  }

  public void setSubgroupShortCode(String subgroupShortCode) {
    this.subgroupShortCode = subgroupShortCode;
  }

  public ReferenceData subgroupDescription(String subgroupDescription) {
    this.subgroupDescription = subgroupDescription;
    return this;
  }

  /**
   * Display value of a sub group
   *
   * @return subgroupDescription
   */
  @ApiModelProperty(example = "Automatic", value = "Display value of a sub group")
  public String getSubgroupDescription() {
    return subgroupDescription;
  }

  public void setSubgroupDescription(String subgroupDescription) {
    this.subgroupDescription = subgroupDescription;
  }

  public ReferenceData displayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
    return this;
  }

  /**
   * Order to display
   *
   * @return displayOrder
   */
  @ApiModelProperty(value = "Order to display")
  public Integer getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenceData referenceData = (ReferenceData) o;
    return Objects.equals(this.shortCode, referenceData.shortCode)
        && Objects.equals(this.description, referenceData.description)
        && Objects.equals(this.groupShortCode, referenceData.groupShortCode)
        && Objects.equals(this.groupDescription, referenceData.groupDescription)
        && Objects.equals(this.subgroupShortCode, referenceData.subgroupShortCode)
        && Objects.equals(this.subgroupDescription, referenceData.subgroupDescription)
        && Objects.equals(this.displayOrder, referenceData.displayOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        shortCode,
        description,
        groupShortCode,
        groupDescription,
        subgroupShortCode,
        subgroupDescription,
        displayOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenceData {\n");

    sb.append("    shortCode: ").append(toIndentedString(shortCode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    groupShortCode: ").append(toIndentedString(groupShortCode)).append("\n");
    sb.append("    groupDescription: ").append(toIndentedString(groupDescription)).append("\n");
    sb.append("    subgroupShortCode: ").append(toIndentedString(subgroupShortCode)).append("\n");
    sb.append("    subgroupDescription: ")
        .append(toIndentedString(subgroupDescription))
        .append("\n");
    sb.append("    displayOrder: ").append(toIndentedString(displayOrder)).append("\n");
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
