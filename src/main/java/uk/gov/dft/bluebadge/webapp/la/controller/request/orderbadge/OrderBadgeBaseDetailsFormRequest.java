package uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge;

import org.thymeleaf.util.StringUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FlowForm;

public interface OrderBadgeBaseDetailsFormRequest extends FlowForm {
  String getName();

  String getPostcode();

  String getTownOrCity();

  String getOptionalAddressField();

  String getBuildingAndStreet();

  default String getAddress() {
    StringBuilder address = new StringBuilder(getBuildingAndStreet());
    String line2 = getOptionalAddressField();
    if (!StringUtils.isEmpty(line2)) {
      address.append(", ").append(line2);
    }
    address.append(", ").append(getTownOrCity());
    address.append(", ").append(getPostcode());
    return address.toString();
  }
}
