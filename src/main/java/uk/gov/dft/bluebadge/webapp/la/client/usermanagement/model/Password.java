package uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

/** Password */
@Validated
public class Password {
  @JsonProperty("password")
  private String password = null;

  @JsonProperty("passwordConfirm")
  private String passwordConfirm = null;

  public Password password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   *
   * @return password
   */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Pattern(regexp = "^[^\\s-]{8,}$")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Password passwordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
    return this;
  }

  /**
   * Get passwordConfirm
   *
   * @return passwordConfirm
   */
  @ApiModelProperty(value = "")
  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Password password = (Password) o;
    return Objects.equals(this.password, password.password)
        && Objects.equals(this.passwordConfirm, password.passwordConfirm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(password, passwordConfirm);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Password {\n");

    sb.append("     ***REMOVED***);
    sb.append("     ***REMOVED***);
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
