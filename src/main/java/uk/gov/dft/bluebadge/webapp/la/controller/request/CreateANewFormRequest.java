package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CreateANewFormRequest {

  @NotEmpty(message = "{error.form.field.createUser.email.notEmpty}")
  @Email(message = "{error.form.field.createUser.email.wrongFormat}")
  private String email;

  @NotEmpty(message = "{error.form.field.createUser.fullname.notEmpty}")
  private String fullName;

  public CreateANewFormRequest(
      @NotEmpty(message = "{error.form.field.createUser.email.notEmpty}")
          @Email(message = "{error.form.field.createUser.email.wrongFormat}")
          String email,
      @NotEmpty(message = "{error.form.field.createUser.fullname.notEmpty}") String fullName) {
    this.email = email;
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateANewFormRequest that = (CreateANewFormRequest) o;
    return Objects.equals(email, that.email) && Objects.equals(fullName, that.fullName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(email, fullName);
  }

  @Override
  public String toString() {
    return "CreateANewFormRequest{"
        + "email='"
        + email
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + '}';
  }
}
