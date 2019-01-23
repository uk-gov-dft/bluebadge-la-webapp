package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

@Component
public class ApplicationToOrderBadgeIndexFormRequest implements Converter<Application, OrderBadgeIndexFormRequest> {

  @Override
  public OrderBadgeIndexFormRequest convert(Application source) {
    Assert.notNull(source, "Source cannot be null");

    PartyTypeCodeField partyTypeCode = source.getParty().getTypeCode();

    String applicantType = partyTypeCode.equals(PartyTypeCodeField.ORG) ? "organisation" : "person";

    return OrderBadgeIndexFormRequest.builder()
        .applicantType(applicantType)
        .build();
  }
}
