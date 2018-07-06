package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.model.badge.BadgeOrder;

@Component
public class OrderBadgeFormsToBadgeOrder {

  public BadgeOrder convert(
      OrderBadgePersonDetailsFormRequest details, OrderBadgeProcessingFormRequest processing) {
    Assert.notNull(details, "details cannot be null");
    Assert.notNull(processing, "processing cannot be null");
    LocalDate applicationDate =
        LocalDate.of(
            processing.getApplicationDateYear(),
            processing.getApplicationDateMonth(),
            processing.getApplicationDateDay());
    return BadgeOrder.builder()
        .applicationChannelCode(processing.getApplicationChannel())
        .applicationDate(applicationDate)
        .build();
  }
}
