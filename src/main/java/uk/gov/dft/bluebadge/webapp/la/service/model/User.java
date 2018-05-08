package uk.gov.dft.bluebadge.webapp.la.service.model;

import java.util.Objects;

public class User {
  private Long id;
  private String fullname;
  private String email;
  private String password;

  public User(Long id, String fullname, String email, String password) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getFullname() {
    return fullname;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return String.format("Application [id=%s, fullname=%s, username=%s", id, fullname, email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(fullname, user.fullname)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fullname, email, password);
  }

  public static final class UserBuilder {
    private Long id;
    private String fullname;
    private String email;
    private String password;

    private UserBuilder() {}

    public static UserBuilder anUser() {
      return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public UserBuilder withFullname(String fullname) {
      this.fullname = fullname;
      return this;
    }

    public UserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      return new User(id, fullname, email, password);
    }
  }
}
