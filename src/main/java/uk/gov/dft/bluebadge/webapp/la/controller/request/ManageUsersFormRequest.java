package uk.gov.dft.bluebadge.webapp.la.controller.request;

import java.util.Objects;

public class ManageUsersFormRequest {

  private String search;

  public ManageUsersFormRequest(String search) {
    this.search = search;
  }

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ManageUsersFormRequest that = (ManageUsersFormRequest) o;
    return Objects.equals(search, that.search);
  }

  @Override
  public int hashCode() {

    return Objects.hash(search);
  }

  @Override
  public String toString() {
    return "ManageUsersFormRequest{" + "search='" + search + '\'' + '}';
  }
}
