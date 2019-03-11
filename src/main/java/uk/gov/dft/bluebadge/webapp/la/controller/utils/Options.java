package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import java.util.ArrayList;
import java.util.List;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;

public class Options {
  private Options() {}

  public static List<ReferenceData> optionsFromEnum(
      String groupShortCode, Class<? extends Enum<?>> enumClass) {
    List<ReferenceData> items = new ArrayList<>();
    for (Enum<?> item : enumClass.getEnumConstants()) {
      items.add(
          ReferenceData.builder().shortCode(item.name()).groupShortCode(groupShortCode).build());
    }
    return items;
  }
}
