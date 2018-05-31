package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class UserDetailsFormRequest {

  private String emailAddress;

  private String name;

  public UserDetailsFormRequest(String emailAddress, String name) {
    this.emailAddress = emailAddress;
    this.name = name;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsFormRequest that = (UserDetailsFormRequest) o;
    return Objects.equals(emailAddress, that.emailAddress) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(emailAddress, name);
  }

  @Override
  public String toString() {
    return "UserDetailsFormRequest{"
        + "emailAddress='"
        + emailAddress
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
