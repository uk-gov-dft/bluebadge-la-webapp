package uk.gov.dft.bluebadge.webapp.la.client.applications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** Benefit */
@Validated
public class Benefit {
  @JsonProperty("isIndefinite")
  private Boolean isIndefinite = null;

  @JsonProperty("expiryDate")
  private LocalDate expiryDate = null;

  public Benefit isIndefinite(Boolean isIndefinite) {
    this.isIndefinite = isIndefinite;
    return this;
  }

  /**
   * Get isIndefinite
   *
   * @return isIndefinite
   */
  @ApiModelProperty(example = "true", required = true, value = "")
  @NotNull
  public Boolean isIsIndefinite() {
    return isIndefinite;
  }

  public void setIsIndefinite(Boolean isIndefinite) {
    this.isIndefinite = isIndefinite;
  }

  public Benefit expiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * Get expiryDate
   *
   * @return expiryDate
   */
  @ApiModelProperty(example = "2022-03-30", value = "")
  @Valid
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Benefit benefit = (Benefit) o;
    return Objects.equals(this.isIndefinite, benefit.isIndefinite)
        && Objects.equals(this.expiryDate, benefit.expiryDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isIndefinite, expiryDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Benefit {\n");

    sb.append("    isIndefinite: ").append(toIndentedString(isIndefinite)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
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
