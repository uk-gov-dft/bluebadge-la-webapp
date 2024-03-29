package uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/** Person */
@Validated
public class Person {
  @JsonProperty("badgeHolderName")
  private String badgeHolderName = null;

  @JsonProperty("nino")
  private String nino = null;

  @JsonProperty("dob")
  private LocalDate dob = null;

  @JsonProperty("genderCode")
  private String genderCode = null;

  public Person badgeHolderName(String badgeHolderName) {
    this.badgeHolderName = badgeHolderName;
    return this;
  }

  /**
   * Get badgeHolderName
   *
   * @return badgeHolderName
   */
  @ApiModelProperty(example = "Dwight Appleman", value = "")
  @Size(max = 100)
  public String getBadgeHolderName() {
    return badgeHolderName;
  }

  public void setBadgeHolderName(String badgeHolderName) {
    this.badgeHolderName = badgeHolderName;
  }

  public Person nino(String nino) {
    this.nino = nino;
    return this;
  }

  /**
   * The badge holder's National Insurance number.
   *
   * @return nino
   */
  @ApiModelProperty(example = "NY188796B", value = "The badge holder's National Insurance number.")
  @Pattern(
    regexp =
        "^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])(?:\\s*\\d\\s*){6}([A-D]|\\s)$"
  )
  public String getNino() {
    return nino;
  }

  public void setNino(String nino) {
    this.nino = nino;
  }

  public Person dob(LocalDate dob) {
    this.dob = dob;
    return this;
  }

  /**
   * Date of birth YYYY-MM-DD
   *
   * @return dob
   */
  @ApiModelProperty(example = "1972-09-13", value = "Date of birth YYYY-MM-DD")
  @Valid
  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public Person genderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  /**
   * A short code from the GENDER group of reference data. e.g. MALE, FEMALE or UNSPECIFIED.
   *
   * @return genderCode
   */
  @ApiModelProperty(
    example = "MALE",
    value =
        "A short code from the GENDER group of reference data. e.g. MALE, FEMALE or UNSPECIFIED."
  )
  public String getGenderCode() {
    return genderCode;
  }

  public void setGenderCode(String genderCode) {
    this.genderCode = genderCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.badgeHolderName, person.badgeHolderName)
        && Objects.equals(this.nino, person.nino)
        && Objects.equals(this.dob, person.dob)
        && Objects.equals(this.genderCode, person.genderCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(badgeHolderName, nino, dob, genderCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");

    sb.append("    badgeHolderName: ").append(toIndentedString(badgeHolderName)).append("\n");
    sb.append("    nino: ").append(toIndentedString(nino)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
    sb.append("    genderCode: ").append(toIndentedString(genderCode)).append("\n");
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
