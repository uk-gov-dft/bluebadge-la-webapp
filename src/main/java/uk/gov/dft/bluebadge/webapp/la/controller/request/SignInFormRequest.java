package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SignInFormRequest {

  @NotEmpty(message = "{error.form.field.signin.email.notEmpty}")
  @Email(message = "{error.form.field.signin.email.wrongFormat}")
  private String emailAddress;

  @NotEmpty(message = "{error.form.field.signin.password.notEmpty}")
  private String password;

  public SignInFormRequest(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public String getPassword() {
    return this.password;
  }

  @Override
  public String toString() {
    return String.format("SignInFormRequest [emailAddress=%s]", emailAddress);
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
    return Objects.equals(emailAddress, that.emailAddress)
        && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {

    return Objects.hash(emailAddress, password);
  }

  public static final class SignInRequestBuilder {
    private String emailAddress;
    private String password;

    private SignInRequestBuilder() {}

    public static SignInRequestBuilder aLoginRequest() {
      return new SignInRequestBuilder();
    }

    public SignInRequestBuilder withEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    public SignInRequestBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public SignInFormRequest build() {
      return new SignInFormRequest(emailAddress, password);
    }
  }
}
