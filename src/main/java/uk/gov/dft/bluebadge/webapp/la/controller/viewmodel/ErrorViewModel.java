package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.io.Serializable;
import java.util.Objects;
import org.springframework.util.Assert;

public class ErrorViewModel implements Serializable {

  private static final String DEFAULT_TITLE = "error.form.summary.title";
  private static final String DEFAULT_DESCRIPTION = "empty";

  private String title = DEFAULT_TITLE;
  private String description = DEFAULT_DESCRIPTION;

  public ErrorViewModel() {
    title = DEFAULT_TITLE;
    description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title) {
    setTitle(title);
    description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title, String description) {
    setTitle(title);
    setDescription(description);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    Assert.notNull(title, "title cannot be null");
    Assert.hasText(title, "title should have text");
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    Assert.notNull(description, "description cannot be null");
    Assert.hasText(description, "description should have text");
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorViewModel that = (ErrorViewModel) o;
    return Objects.equals(title, that.title) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {

    return Objects.hash(title, description);
  }

  @Override
  public String toString() {
    return "ErrorViewModel{"
        + "title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
