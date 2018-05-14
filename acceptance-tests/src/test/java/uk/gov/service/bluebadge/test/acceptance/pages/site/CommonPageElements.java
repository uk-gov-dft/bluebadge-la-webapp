package uk.gov.service.bluebadge.test.acceptance.pages.site;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;

public class CommonPageElements implements PageElements {

  private static final Map<String, By> pageElements =
      new HashMap<String, By>() {
        {
          put("Search Box", By.xpath("//input[@name='query']"));
          put(
              "Pagination page",
              By.xpath(
                  "//li[contains(@class, 'pagination-list__item pagination-list__item--current')]/span"));
        }
      };

  @Override
  public boolean contains(String elementName) {
    return pageElements.containsKey(elementName);
  }

  @Override
  public WebElement getElementByName(String elementName, PageHelper helper) {
    return getElementByName(elementName, 0, helper);
  }

  @Override
  public WebElement getElementByName(String elementName, int nth, PageHelper helper) {
    return helper.findElements(pageElements.get(elementName)).get(nth);
  }
}
