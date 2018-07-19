package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

public class ScenarioContext {
  private static final Logger log = getLogger(ScenarioContext.class);

  private Map<String, Object> scenarioContext;

  public ScenarioContext() {
    scenarioContext = new HashMap<>();
  }

  public void setContext(String key, Object value) {
    log.debug("Saving [(),()] into ScenarioContext", key, value.toString());
    scenarioContext.put(key, value);
  }

  public Object getContext(String key) {
    log.debug("Retrieving [()] from ScenarioContext", key);
    return scenarioContext.get(key);
  }

  public Boolean isContains(String key) {
    boolean result = scenarioContext.containsKey(key);
    log.debug("Scenario contains [()] equals [()]", key, result);
    return result;
  }
}
