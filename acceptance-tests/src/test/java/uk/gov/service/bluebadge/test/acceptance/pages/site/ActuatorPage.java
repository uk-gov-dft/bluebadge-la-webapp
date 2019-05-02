package uk.gov.service.bluebadge.test.acceptance.pages.site;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.service.bluebadge.test.acceptance.pages.PageHelper;

@Component
public class ActuatorPage {

  private static final String MANAGEMENT_URL = System.getProperty("managementUrl");
  private final PageHelper pageHelper;
  private final String actuatorBaseUrl;
  private ObjectMapper objectMapper;

  @Autowired
  public ActuatorPage(PageHelper pageHelper) {
    this.pageHelper = pageHelper;
    Assert.hasText(MANAGEMENT_URL, "Management URL is not set.");
    this.actuatorBaseUrl = MANAGEMENT_URL + "/manage/actuator";
    objectMapper = new ObjectMapper();
  }

  public void openActuator(String actuatorUrl) {
    assertNotNull(MANAGEMENT_URL);
    try {
      pageHelper.getWebDriver().get(actuatorBaseUrl + actuatorUrl);
      pageHelper.getWebDriver().manage().window().maximize();
      checkForError();
    } catch (Exception ex) {
      fail("Failed to load actuator " + actuatorUrl + ". Exception:" + ex);
    }
  }

  /**
   * Not definitive, as relies on a json error response. Have to allow none json as valid, as
   * Prometheus is just a text response.
   */
  private void checkForError() {
    JsonNode jsonNode = parseResponse(false);
    if (null != jsonNode) {
      JsonNode error = getField(jsonNode, "error");
      assertNull("Actuator responded with error: " + error, error);
    }
  }

  private JsonNode parseResponse(boolean failOnError) {
    WebElement pre = pageHelper.findOptionalElement(By.cssSelector("pre"));
    assertNotNull("Failed to find 'pre' element of the actuator response", pre);
    String responseText = pre.getText();
    try {
      JsonNode jsonNode = objectMapper.readTree(responseText);
      assertNotNull(jsonNode);
      return jsonNode;
    } catch (IOException e) {
      if (failOnError) {
        fail("Failed to parse Actuator response. May not be JSON. response:" + responseText);
      }
    }
    return null;
  }

  private static JsonNode getFieldInner(JsonNode jsonNode, String[] field, int depth) {
    JsonNode nextNode = jsonNode.get(field[depth]);
    if (++depth == field.length) {
      return nextNode;
    }
    if (nextNode.isNull()) {
      return nextNode;
    }
    return getFieldInner(nextNode, field, depth);
  }

  private JsonNode getField(JsonNode root, String field) {
    return getFieldInner(root, field.split("\\."), 0);
  }

  public String getField(String field) {
    JsonNode jsonNode = parseResponse(true);
    JsonNode fieldNode = getField(jsonNode, field);
    assertNotNull("No field found for: " + field, fieldNode);
    return fieldNode.textValue();
  }

  public void checkForField(String field) {
    getField(field);
  }
}
