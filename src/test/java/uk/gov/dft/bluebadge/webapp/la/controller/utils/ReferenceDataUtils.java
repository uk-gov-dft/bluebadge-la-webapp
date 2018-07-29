package uk.gov.dft.bluebadge.webapp.la.controller.utils;

import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;

public class ReferenceDataUtils {
  public static final ReferenceData buildReferenceData(String groupShortCode, int i) {
    return new ReferenceData()
        .description("description" + 1)
        .displayOrder(i)
        .groupDescription("groupDescription" + i)
        .groupShortCode(groupShortCode)
        .shortCode("shortCode" + i)
        .subgroupDescription("subGroupDescription" + i)
        .subgroupShortCode("subGroupShortCode" + i);
  }
}
