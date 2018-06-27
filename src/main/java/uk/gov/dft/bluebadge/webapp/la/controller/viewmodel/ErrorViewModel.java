package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.util.Objects;
import org.springframework.util.Assert;

public class ErrorViewModel {

  private static final String DEFAULT_TITLE = "error.form.summary.title";
  private static final String DEFAULT_DESCRIPTION = "empty";

  private String title = DEFAULT_TITLE;
  private String description = DEFAULT_DESCRIPTION;

  public ErrorViewModel() {
    this.title = DEFAULT_TITLE;
    this.description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title) {
    Assert.notNull(title, "title cannot be null");
    Assert.hasText(title, "title should have text");
    this.title = title;
    this.description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title, String description) {
    Assert.notNull(title, "title cannot be null");
    Assert.hasText(title, "title should have text");
    Assert.notNull(description, "description cannot be null");
    Assert.hasText(description, "description should have text");
    this.title = title;
    this.description = description;
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
