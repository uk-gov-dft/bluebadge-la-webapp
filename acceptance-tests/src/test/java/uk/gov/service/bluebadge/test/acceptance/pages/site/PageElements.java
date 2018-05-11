package uk.gov.service.bluebadge.test.acceptance.pages.site;

import org.openqa.selenium.WebElement;
import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;

public interface PageElements {

    boolean contains(String elementName);

    WebElement getElementByName(String elementName, PageHelper webDriver);

    WebElement getElementByName(String elementName, int nth, PageHelper webDriver);
}
