package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Organisation */
@Validated
public class AppOrganisation {
  @JsonProperty("badgeHolderName")
  private String badgeHolderName = null;

  @JsonProperty("isCharity")
  private Boolean isCharity = null;

  @JsonProperty("charityNumber")
  private String charityNumber = null;

  @JsonProperty("vehicles")
  @Valid
  private List<Vehicle> vehicles = null;

  @JsonProperty("numberOfBadges")
  private Integer numberOfBadges = null;

  public AppOrganisation badgeHolderName(String badgeHolderName) {
    this.badgeHolderName = badgeHolderName;
    return this;
  }

  /**
   * Get badgeHolderName
   *
   * @return badgeHolderName
   */
  @ApiModelProperty(example = "Trotters Independant Traders", required = true, value = "")
  @NotNull
  @Size(max = 100)
  public String getBadgeHolderName() {
    return badgeHolderName;
  }

  public void setBadgeHolderName(String badgeHolderName) {
    this.badgeHolderName = badgeHolderName;
  }

  public AppOrganisation isCharity(Boolean isCharity) {
    this.isCharity = isCharity;
    return this;
  }

  /**
   * Get isCharity
   *
   * @return isCharity
   */
  @ApiModelProperty(example = "false", required = true, value = "")
  @NotNull
  public Boolean isIsCharity() {
    return isCharity;
  }

  public void setIsCharity(Boolean isCharity) {
    this.isCharity = isCharity;
  }

  public AppOrganisation charityNumber(String charityNumber) {
    this.charityNumber = charityNumber;
    return this;
  }

  /**
   * Get charityNumber
   *
   * @return charityNumber
   */
  @ApiModelProperty(example = "12345", value = "")
  @Size(max = 100)
  public String getCharityNumber() {
    return charityNumber;
  }

  public void setCharityNumber(String charityNumber) {
    this.charityNumber = charityNumber;
  }

  public AppOrganisation vehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
    return this;
  }

  public AppOrganisation addVehiclesItem(Vehicle vehiclesItem) {
    if (this.vehicles == null) {
      this.vehicles = new ArrayList<>();
    }
    this.vehicles.add(vehiclesItem);
    return this;
  }

  /**
   * Get vehicles
   *
   * @return vehicles
   */
  @ApiModelProperty(value = "")
  @Valid
  public List<Vehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }

  public AppOrganisation numberOfBadges(Integer numberOfBadges) {
    this.numberOfBadges = numberOfBadges;
    return this;
  }

  /**
   * Must be 1 for a person, can be multiple for an organisation. minimum: 1
   *
   * @return numberOfBadges
   */
  @ApiModelProperty(
    example = "1",
    required = true,
    value = "Must be 1 for a person, can be multiple for an organisation."
  )
  @NotNull
  @Min(1)
  public Integer getNumberOfBadges() {
    return numberOfBadges;
  }

  public void setNumberOfBadges(Integer numberOfBadges) {
    this.numberOfBadges = numberOfBadges;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppOrganisation organisation = (AppOrganisation) o;
    return Objects.equals(this.badgeHolderName, organisation.badgeHolderName)
        && Objects.equals(this.isCharity, organisation.isCharity)
        && Objects.equals(this.charityNumber, organisation.charityNumber)
        && Objects.equals(this.vehicles, organisation.vehicles)
        && Objects.equals(this.numberOfBadges, organisation.numberOfBadges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(badgeHolderName, isCharity, charityNumber, vehicles, numberOfBadges);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organisation {\n");

    sb.append("    badgeHolderName: ").append(toIndentedString(badgeHolderName)).append("\n");
    sb.append("    isCharity: ").append(toIndentedString(isCharity)).append("\n");
    sb.append("    charityNumber: ").append(toIndentedString(charityNumber)).append("\n");
    sb.append("    vehicles: ").append(toIndentedString(vehicles)).append("\n");
    sb.append("    numberOfBadges: ").append(toIndentedString(numberOfBadges)).append("\n");
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
