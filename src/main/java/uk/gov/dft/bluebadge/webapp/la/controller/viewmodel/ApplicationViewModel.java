package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.util.Objects;

public class ApplicationViewModel {
  private Long id;
  private String fullname;

  public ApplicationViewModel(Long id, String fullname) {
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
    return String.format("ApplicationViewModel [id=%s, fullname=%s", id, fullname);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationViewModel that = (ApplicationViewModel) o;
    return Objects.equals(id, that.id) && Objects.equals(fullname, that.fullname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, fullname);
  }

  public static final class ApplicationViewModelBuilder {
    private Long id;
    private String fullname;

    private ApplicationViewModelBuilder() {}

    public static ApplicationViewModelBuilder anApplicationViewModel() {
      return new ApplicationViewModelBuilder();
    }

    public ApplicationViewModelBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ApplicationViewModelBuilder withFullname(String fullname) {
      this.fullname = fullname;
      return this;
    }

    public ApplicationViewModel build() {
      return new ApplicationViewModel(id, fullname);
    }
  }
}
