package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class ApplicationUpdateRequest {
  private Long id;
  private String fullname;

  public ApplicationUpdateRequest(Long id, String fullname) {
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
    return String.format("ApplicationUpdateRequest [id=%s, fullname=%s", id, fullname);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationUpdateRequest that = (ApplicationUpdateRequest) o;
    return Objects.equals(id, that.id) && Objects.equals(fullname, that.fullname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, fullname);
  }

  public static final class ApplicationUpdateRequestBuilder {
    private Long id;
    private String fullname;

    private ApplicationUpdateRequestBuilder() {}

    public static ApplicationUpdateRequestBuilder anApplicationUpdateRequest() {
      return new ApplicationUpdateRequestBuilder();
    }

    public ApplicationUpdateRequestBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ApplicationUpdateRequestBuilder withFullname(String fullname) {
      this.fullname = fullname;
      return this;
    }

    public ApplicationUpdateRequest build() {
      return new ApplicationUpdateRequest(id, fullname);
    }
  }
}
