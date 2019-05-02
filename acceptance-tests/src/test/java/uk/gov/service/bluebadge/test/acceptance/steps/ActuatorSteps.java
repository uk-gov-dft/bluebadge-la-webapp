package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.service.bluebadge.test.acceptance.pages.site.ActuatorPage;

public class ActuatorSteps extends AbstractSpringSteps {

  private ActuatorPage actuatorPage;

  @Autowired
  public ActuatorSteps(ActuatorPage actuatorPage) {
    this.actuatorPage = actuatorPage;
  }

  @Given("^Call actuator \'(/info|/health|/prometheus|/loggers)\'$")
  public void pathActuatorInfo(String actuatorPath) {
    actuatorPage.openActuator(actuatorPath);
  }

  @And("^Actuator data present '([a-zA-Z\\s\\.]*)'$")
  public void actuatorDataContainsField(String field) {
    actuatorPage.checkForField(field);
  }

  @And("^Actuator data '([a-zA-Z\\s\\.]*)' equals '([a-zA-Z\\s\\.]*)'$")
  public void actuatorDataFieldEquals(String field, String expectValue) {
    String value = actuatorPage.getField(field);
    assertEquals(expectValue, value);
  }
}
