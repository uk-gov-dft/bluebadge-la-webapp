package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class ApplicationCreateRequest {
  private Long id;
  private String fullname;

  public ApplicationCreateRequest(Long id, String fullname) {
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
    return String.format("ApplicationCreateRequest [id=%s, fullname=%s", id, fullname);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationCreateRequest that = (ApplicationCreateRequest) o;
    return Objects.equals(id, that.id) && Objects.equals(fullname, that.fullname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, fullname);
  }

  public static final class ApplicationCreateRequestBuilder {
    private Long id;
    private String fullname;

    private ApplicationCreateRequestBuilder() {}

    public static ApplicationCreateRequestBuilder anApplicationCreateRequest() {
      return new ApplicationCreateRequestBuilder();
    }

    public ApplicationCreateRequestBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ApplicationCreateRequestBuilder withFullname(String fullname) {
      this.fullname = fullname;
      return this;
    }

    public ApplicationCreateRequest build() {
      return new ApplicationCreateRequest(id, fullname);
    }
  }
}
