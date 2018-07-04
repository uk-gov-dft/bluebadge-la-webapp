package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

import java.util.Objects;
import org.springframework.util.Assert;

public class ErrorViewModel {

  private static final String DEFAULT_TITLE = "error.form.summary.title";
  private static final String DEFAULT_DESCRIPTION = "empty";

  private static final String ASSERTION_TITLE_CANNOT_BE_NULL = "title cannot be null";
  private static final String ASSERTION_DESCRIPTION_CANNOT_BE_NULL = "description cannot be null";
  private static final String ASSERTION_TITLE_SHOULD_HAVE_TEXT = "title should have text";
  private static final String ASSERTION_DESCRIPTION_SHOULD_HAVE_TEXT =
      "description should have text";

  private String title = DEFAULT_TITLE;
  private String description = DEFAULT_DESCRIPTION;

  public ErrorViewModel() {
    title = DEFAULT_TITLE;
    description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title) {
    Assert.notNull(title, ASSERTION_TITLE_CANNOT_BE_NULL);
    Assert.hasText(title, ASSERTION_TITLE_SHOULD_HAVE_TEXT);
    this.title = title;
    description = DEFAULT_DESCRIPTION;
  }

  public ErrorViewModel(String title, String description) {
    Assert.notNull(title, ASSERTION_TITLE_CANNOT_BE_NULL);
    Assert.hasText(title, ASSERTION_TITLE_SHOULD_HAVE_TEXT);
    Assert.notNull(description, ASSERTION_DESCRIPTION_CANNOT_BE_NULL);
    Assert.hasText(description, ASSERTION_DESCRIPTION_SHOULD_HAVE_TEXT);
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    Assert.notNull(title, ASSERTION_TITLE_CANNOT_BE_NULL);
    Assert.hasText(title, ASSERTION_TITLE_SHOULD_HAVE_TEXT);
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    Assert.notNull(description, ASSERTION_DESCRIPTION_CANNOT_BE_NULL);
    Assert.hasText(description, ASSERTION_DESCRIPTION_SHOULD_HAVE_TEXT);
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
