package uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public enum Nation {
  ENG,
  WLS,
  SCO,
  NIR;

  private static Map<String, Nation> nationMap =
      ImmutableMap.of("ENG", ENG, "SCO", SCO, "WLS", WLS, "NIR", NIR);

  @JsonCreator
  public static Nation forValue(String value) {
    // Expect a valid nation if a value given
    if (StringUtils.isNotEmpty(value)) {
      if (null == nationMap.get(value)) {
        throw new IllegalArgumentException("No Nation mapping for value " + value);
      }
      return nationMap.get(value);
    }
    return null;
  }
}
