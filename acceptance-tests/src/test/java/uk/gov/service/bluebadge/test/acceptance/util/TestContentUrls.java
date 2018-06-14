package uk.gov.service.bluebadge.test.acceptance.util;

import java.util.HashMap;
import java.util.Map;
import uk.gov.service.bluebadge.test.acceptance.pages.site.AbstractSitePage;

public class TestContentUrls {

  private final Map<String, String> urlLookup = new HashMap();

  public TestContentUrls() {
    setup();
  }

  public String lookupUrl(String site, String pageName) {
    String url = urlLookup.get(pageName.toLowerCase());
    if (url == null) {
      throw new RuntimeException("Unknown pageName: " + pageName);
    } else if (site.equals("main")) {
      return AbstractSitePage.URL + url;
    } else {
      return AbstractSitePage.actuator_URL + url;
    }
  }

  private void setup() {

    add("home", "/");
    add("news", "/news");
    add("sign-in", "/sign-in");
    add("manage-users", "/manage-users");
    add("info", "/info");
    add("health", "/health");
    add("loggers", "/loggers");
  }

  private void add(String pageName, String url) {
    urlLookup.put(pageName.toLowerCase(), url);
  }
}
