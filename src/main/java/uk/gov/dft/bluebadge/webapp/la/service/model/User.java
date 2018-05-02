package uk.gov.dft.bluebadge.webapp.la.service.model;

import java.util.Objects;

public class Application {
  private Long id;
  private String fullname;

  public Application(Long id, String fullname) {
    this.id = id;
    this.fullname = fullname;
  }

  public Long getId() {
    return id;
  }

  public String getFullname() {
    return fullname;
  }

  @Override
  public String toString() {
    return String.format("Application [id=%s, fullname=%s", id, fullname);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Application that = (Application) o;
    return Objects.equals(id, that.id) && Objects.equals(fullname, that.fullname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, fullname);
  }

  public static final class ApplicationBuilder {
    private Long id;
    private String fullname;

    private ApplicationBuilder() {}

    public static ApplicationBuilder anApplication() {
      return new ApplicationBuilder();
    }

    public ApplicationBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ApplicationBuilder withFullname(String fullname) {
      this.fullname = fullname;
      return this;
    }

    public Application build() {
      return new Application(id, fullname);
    }
  }
}
