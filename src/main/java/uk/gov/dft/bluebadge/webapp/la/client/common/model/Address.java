package uk.gov.dft.bluebadge.webapp.la.client.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Address */
@Validated
public class Address {
  @JsonProperty("buildingStreet")
  private String buildingStreet = null;

  @JsonProperty("line2")
  private String line2 = null;

  @JsonProperty("townCity")
  private String townCity = null;

  @JsonProperty("postCode")
  private String postCode = null;

  public Address buildingStreet(String buildingStreet) {
    this.buildingStreet = buildingStreet;
    return this;
  }

  /**
   * Get buildingStreet
   *
   * @return buildingStreet
   */
  @ApiModelProperty(example = "65 Basil Chambers", required = true, value = "")
  @NotNull
  @Size(max = 50)
  public String getBuildingStreet() {
    return buildingStreet;
  }

  public void setBuildingStreet(String buildingStreet) {
    this.buildingStreet = buildingStreet;
  }

  public Address line2(String line2) {
    this.line2 = line2;
    return this;
  }

  /**
   * Get line2
   *
   * @return line2
   */
  @ApiModelProperty(example = "Northern Quarter", value = "")
  @Size(max = 40)
  public String getLine2() {
    return line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public Address townCity(String townCity) {
    this.townCity = townCity;
    return this;
  }

  /**
   * Get townCity
   *
   * @return townCity
   */
  @ApiModelProperty(example = "Manchester", required = true, value = "")
  @NotNull
  @Size(max = 40)
  public String getTownCity() {
    return townCity;
  }

  public void setTownCity(String townCity) {
    this.townCity = townCity;
  }

  public Address postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * Get postCode
   *
   * @return postCode
   */
  @ApiModelProperty(example = "SK6 8GH", required = true, value = "")
  @NotNull
  @Pattern(
    regexp =
        "^((GIR &0AA)|((([A-PR-UWYZ][A-HK-Y]?[0-9][0-9]?)|(([A-PR-UWYZ][0-9][A-HJKSTUW])|([A-PR-UWYZ][A-HK-Y][0-9][ABEHMNPRV-Y]))) &[0-9][ABD-HJLNP-UW-Z]{2}))$"
  )
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(this.buildingStreet, address.buildingStreet)
        && Objects.equals(this.line2, address.line2)
        && Objects.equals(this.townCity, address.townCity)
        && Objects.equals(this.postCode, address.postCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingStreet, line2, townCity, postCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");

    sb.append("    buildingStreet: ").append(toIndentedString(buildingStreet)).append("\n");
    sb.append("    line2: ").append(toIndentedString(line2)).append("\n");
    sb.append("    townCity: ").append(toIndentedString(townCity)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
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
