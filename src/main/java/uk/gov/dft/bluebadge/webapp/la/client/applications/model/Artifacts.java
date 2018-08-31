package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Provided files/evidence */
@ApiModel(description = "Provided files/evidence")
@Validated
public class Artifacts {
  @JsonProperty("proofOfEligibilityUrl")
  private String proofOfEligibilityUrl = null;

  @JsonProperty("proofOfAddressUrl")
  private String proofOfAddressUrl = null;

  @JsonProperty("proofOfIdentityUrl")
  private String proofOfIdentityUrl = null;

  @JsonProperty("badgePhotoUrl")
  private String badgePhotoUrl = null;

  @JsonProperty("proofOfEligibility")
  private String proofOfEligibility = null;

  @JsonProperty("proofOfAddress")
  private String proofOfAddress = null;

  @JsonProperty("proofOfIdentity")
  private String proofOfIdentity = null;

  @JsonProperty("badgePhoto")
  private String badgePhoto = null;

  public Artifacts proofOfEligibilityUrl(String proofOfEligibilityUrl) {
    this.proofOfEligibilityUrl = proofOfEligibilityUrl;
    return this;
  }

  /**
   * Get proofOfEligibilityUrl
   *
   * @return proofOfEligibilityUrl
   */
  @ApiModelProperty(value = "")
  @Size(max = 255)
  public String getProofOfEligibilityUrl() {
    return proofOfEligibilityUrl;
  }

  public void setProofOfEligibilityUrl(String proofOfEligibilityUrl) {
    this.proofOfEligibilityUrl = proofOfEligibilityUrl;
  }

  public Artifacts proofOfAddressUrl(String proofOfAddressUrl) {
    this.proofOfAddressUrl = proofOfAddressUrl;
    return this;
  }

  /**
   * Get proofOfAddressUrl
   *
   * @return proofOfAddressUrl
   */
  @ApiModelProperty(value = "")
  @Size(max = 255)
  public String getProofOfAddressUrl() {
    return proofOfAddressUrl;
  }

  public void setProofOfAddressUrl(String proofOfAddressUrl) {
    this.proofOfAddressUrl = proofOfAddressUrl;
  }

  public Artifacts proofOfIdentityUrl(String proofOfIdentityUrl) {
    this.proofOfIdentityUrl = proofOfIdentityUrl;
    return this;
  }

  /**
   * Get proofOfIdentityUrl
   *
   * @return proofOfIdentityUrl
   */
  @ApiModelProperty(value = "")
  @Size(max = 255)
  public String getProofOfIdentityUrl() {
    return proofOfIdentityUrl;
  }

  public void setProofOfIdentityUrl(String proofOfIdentityUrl) {
    this.proofOfIdentityUrl = proofOfIdentityUrl;
  }

  public Artifacts badgePhotoUrl(String badgePhotoUrl) {
    this.badgePhotoUrl = badgePhotoUrl;
    return this;
  }

  /**
   * A URL for the badge photo.
   *
   * @return badgePhotoUrl
   */
  @ApiModelProperty(value = "A URL for the badge photo.")
  @Size(max = 255)
  public String getBadgePhotoUrl() {
    return badgePhotoUrl;
  }

  public void setBadgePhotoUrl(String badgePhotoUrl) {
    this.badgePhotoUrl = badgePhotoUrl;
  }

  public Artifacts proofOfEligibility(String proofOfEligibility) {
    this.proofOfEligibility = proofOfEligibility;
    return this;
  }

  /**
   * Base64 encoded.
   *
   * @return proofOfEligibility
   */
  @ApiModelProperty(value = "Base64 encoded.")
  public String getProofOfEligibility() {
    return proofOfEligibility;
  }

  public void setProofOfEligibility(String proofOfEligibility) {
    this.proofOfEligibility = proofOfEligibility;
  }

  public Artifacts proofOfAddress(String proofOfAddress) {
    this.proofOfAddress = proofOfAddress;
    return this;
  }

  /**
   * Base64 encoded.
   *
   * @return proofOfAddress
   */
  @ApiModelProperty(value = "Base64 encoded.")
  public String getProofOfAddress() {
    return proofOfAddress;
  }

  public void setProofOfAddress(String proofOfAddress) {
    this.proofOfAddress = proofOfAddress;
  }

  public Artifacts proofOfIdentity(String proofOfIdentity) {
    this.proofOfIdentity = proofOfIdentity;
    return this;
  }

  /**
   * Base64 encoded.
   *
   * @return proofOfIdentity
   */
  @ApiModelProperty(value = "Base64 encoded.")
  public String getProofOfIdentity() {
    return proofOfIdentity;
  }

  public void setProofOfIdentity(String proofOfIdentity) {
    this.proofOfIdentity = proofOfIdentity;
  }

  public Artifacts badgePhoto(String badgePhoto) {
    this.badgePhoto = badgePhoto;
    return this;
  }

  /**
   * Base64 encoded. Must be between 50KB and 10MB, and of format JPG, PNG, or GIF.
   *
   * @return badgePhoto
   */
  @ApiModelProperty(
    value = "Base64 encoded. Must be between 50KB and 10MB, and of format JPG, PNG, or GIF."
  )
  public String getBadgePhoto() {
    return badgePhoto;
  }

  public void setBadgePhoto(String badgePhoto) {
    this.badgePhoto = badgePhoto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Artifacts artifacts = (Artifacts) o;
    return Objects.equals(this.proofOfEligibilityUrl, artifacts.proofOfEligibilityUrl)
        && Objects.equals(this.proofOfAddressUrl, artifacts.proofOfAddressUrl)
        && Objects.equals(this.proofOfIdentityUrl, artifacts.proofOfIdentityUrl)
        && Objects.equals(this.badgePhotoUrl, artifacts.badgePhotoUrl)
        && Objects.equals(this.proofOfEligibility, artifacts.proofOfEligibility)
        && Objects.equals(this.proofOfAddress, artifacts.proofOfAddress)
        && Objects.equals(this.proofOfIdentity, artifacts.proofOfIdentity)
        && Objects.equals(this.badgePhoto, artifacts.badgePhoto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        proofOfEligibilityUrl,
        proofOfAddressUrl,
        proofOfIdentityUrl,
        badgePhotoUrl,
        proofOfEligibility,
        proofOfAddress,
        proofOfIdentity,
        badgePhoto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Artifacts {\n");

    sb.append("    proofOfEligibilityUrl: ")
        .append(toIndentedString(proofOfEligibilityUrl))
        .append("\n");
    sb.append("    proofOfAddressUrl: ").append(toIndentedString(proofOfAddressUrl)).append("\n");
    sb.append("    proofOfIdentityUrl: ").append(toIndentedString(proofOfIdentityUrl)).append("\n");
    sb.append("    badgePhotoUrl: ").append(toIndentedString(badgePhotoUrl)).append("\n");
    sb.append("    proofOfEligibility: ").append(toIndentedString(proofOfEligibility)).append("\n");
    sb.append("    proofOfAddress: ").append(toIndentedString(proofOfAddress)).append("\n");
    sb.append("    proofOfIdentity: ").append(toIndentedString(proofOfIdentity)).append("\n");
    sb.append("    badgePhoto: ").append(toIndentedString(badgePhoto)).append("\n");
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
