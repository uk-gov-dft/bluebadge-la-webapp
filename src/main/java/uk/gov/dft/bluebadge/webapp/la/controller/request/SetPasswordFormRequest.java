package uk.gov.dft.bluebadge.webapp.la.controller.request;

import uk.gov.dft.bluebadge.webapp.la.controller.validator.PasswordsMatch;

@PasswordsMatch(message = "error.form.setPassword.passwords.dont.match")
public class SetPasswordFormRequest {

  private String password;

  private String passwordConfirm;

  public SetPasswordFormRequest(String password, String passwordConfirm) {
    this.password = password;
    this.passwordConfirm = passwordConfirm;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }
}
