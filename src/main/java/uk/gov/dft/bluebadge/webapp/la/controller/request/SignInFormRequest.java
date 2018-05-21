package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class SignInFormRequest {

  //  @NotEmpty(message = "{error.form.field.signin.email.notEmpty}")
  //  @Email(message = "{error.form.field.signin.email.wrongFormat}")
  private String email;

  //  @NotEmpty(message = "{error.form.field.signin.password.notEmpty}")
  private String password;

  public SignInFormRequest(String email, String password) {
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
    return String.format("SignInFormRequest [email=%s]", email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SignInFormRequest that = (SignInFormRequest) o;
    return Objects.equals(email, that.email) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {

    return Objects.hash(email, password);
  }

  public static final class SignInRequestBuilder {
    private String email;
    private String password;

    private SignInRequestBuilder() {}

    public static SignInRequestBuilder aLoginRequest() {
      return new SignInRequestBuilder();
    }

    public SignInRequestBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public SignInRequestBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public SignInFormRequest build() {
      return new SignInFormRequest(email, password);
    }
  }
}
