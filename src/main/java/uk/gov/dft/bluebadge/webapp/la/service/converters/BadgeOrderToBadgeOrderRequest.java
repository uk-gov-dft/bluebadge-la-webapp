package uk.gov.dft.bluebadge.webapp.la.service.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.service.model.badge.BadgeOrder;

@Component
public class BadgeOrderToBadgeOrderRequest implements Converter<BadgeOrder, BadgeOrderRequest> {

  @Override
  public BadgeOrderRequest convert(BadgeOrder source) {
    Assert.notNull(source, "Source cannot be null");

    return new BadgeOrderRequest()
        .applicationChannelCode(source.getApplicationChannelCode())
        .applicationDate(source.getApplicationDate());
  }
}
