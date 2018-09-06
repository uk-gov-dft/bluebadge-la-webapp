package uk.gov.service.bluebadge.test.acceptance.util;

import java.util.HashMap;
import java.util.Map;
import uk.gov.service.bluebadge.test.acceptance.pages.site.AbstractSitePage;

public class TestContentUrls {

  private final Map<String, String> urlLookup = new HashMap<>();

  public TestContentUrls() {
    setup();
  }

  public String lookupUrl(String pageName) {
    String url = urlLookup.get(pageName.toLowerCase());
    if (url == null) {
      throw new RuntimeException("Unknown pageName: " + pageName);
    }
    return AbstractSitePage.URL + url;
  }

  public static String lookupUrlUnmapped(String pageName) {
    if (pageName.startsWith("/")) {
      return AbstractSitePage.URL + pageName;
    } else {
      return AbstractSitePage.URL + "/" + pageName;
    }
  }

  private void setup() {

    add("home", "/");
    add("manage-users", "/manage-users");
    add("order-a-badge", "/order-a-badge/");
    add("order-a-badge/person/details", "/order-a-badge/person/details");
    add("order-a-badge/person/processing", "/order-a-badge/person/processing");
    add("order-a-badge/organisation/details", "/order-a-badge/organisation/details");
    add("order-a-badge/organisation/processing", "/order-a-badge/organisation/processing");
  }

  private void add(String pageName, String url) {
    urlLookup.put(pageName.toLowerCase(), url);
  }
}
