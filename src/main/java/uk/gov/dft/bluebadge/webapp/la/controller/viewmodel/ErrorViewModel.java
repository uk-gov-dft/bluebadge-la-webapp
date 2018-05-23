package uk.gov.dft.bluebadge.webapp.la.controller.viewmodel;

public class ErrorViewModel {

  public String title;
  public String description;

  public ErrorViewModel(String title) {
    this.title = title;
  }

  public ErrorViewModel(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
