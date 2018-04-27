package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Objects;

public class ApplicationViewModel {
  private Long id;
  private String fullname;

  public ApplicationViewModel() {
    super();
  }

  public ApplicationViewModel(Long id, String fullname) {
    super();
    this.id = id;
    this.fullname = fullname;
  }

  public ApplicationViewModel(String fullname) {
    super();
    this.fullname = fullname;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
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
    ApplicationViewModel that = (ApplicationViewModel) o;
    return Objects.equals(id, that.id) && Objects.equals(fullname, that.fullname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, fullname);
  }
}
