package uk.gov.dft.bluebadge.webapp.la.controller.request;

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
