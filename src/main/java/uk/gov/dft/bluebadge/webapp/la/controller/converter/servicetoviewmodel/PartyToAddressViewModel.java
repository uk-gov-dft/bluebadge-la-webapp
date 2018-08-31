package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;

@Component
public class PartyToAddressViewModel implements Converter<Contact, String> {

  @Override
  public String convert(Contact source) {
    Assert.notNull(source, "Source cannot be null");

    if (source == null) {
      return "";
    }
    StringBuilder address = new StringBuilder(source.getBuildingStreet());
    String line2 = source.getLine2();
    if (!StringUtils.isEmpty(line2)) {
      address.append(", ").append(line2);
    }
    address.append(", ").append(source.getTownCity());
    address.append(", ").append(source.getPostCode());
    return address.toString();
  }
}
