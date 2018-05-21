package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class CreateANewUserFormRequest {

  //@NotEmpty @Email
  private String email;

  //@NotEmpty
  private String fullName;

  public CreateANewUserFormRequest(String email, String fullName) {
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
    CreateANewUserFormRequest that = (CreateANewUserFormRequest) o;
    return Objects.equals(email, that.email) && Objects.equals(fullName, that.fullName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(email, fullName);
  }

  @Override
  public String toString() {
    return "CreateANewUserFormRequest{"
        + "email='"
        + email
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + '}';
  }
}
