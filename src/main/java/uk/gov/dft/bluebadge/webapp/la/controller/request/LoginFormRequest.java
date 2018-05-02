package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class LoginRequest {
  private String email;
  private String password;

  public LoginRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  @Override
  public String toString() {
    return String.format("LoginRequest [email=%s]", email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginRequest that = (LoginRequest) o;
    return Objects.equals(email, that.email) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {

    return Objects.hash(email, password);
  }

  public static final class LoginRequestBuilder {
    private String email;
    private String password;

    private LoginRequestBuilder() {
    }

    public static LoginRequestBuilder aLoginRequest() {
      return new LoginRequestBuilder();
    }

    public LoginRequestBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public LoginRequestBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public LoginRequest build() {
      return new LoginRequest(email, password);
    }
  }
}
