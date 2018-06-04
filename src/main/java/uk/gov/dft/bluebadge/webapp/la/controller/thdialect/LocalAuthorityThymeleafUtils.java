package uk.gov.dft.bluebadge.webapp.la.controller.thdialect;

public final class LocalAuthorityThymeleafUtils {

  public String getURLFirstSegment(String url) {
    String[] segments = url.split("/");
    if (segments.length >= 2) {
      return segments[1];
    } else {
      return "";
    }
  }
}
