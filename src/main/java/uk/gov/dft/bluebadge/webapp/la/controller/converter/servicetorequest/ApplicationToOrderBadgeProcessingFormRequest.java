package uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetorequest;

import java.time.OffsetDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

@Component
public class ApplicationToOrderBadgeProcessingFormRequest
    implements Converter<Application, OrderBadgeProcessingFormRequest> {

  @Override
  public OrderBadgeProcessingFormRequest convert(Application source) {
    Assert.notNull(source, "Source cannot be null");

    OffsetDateTime submissionDate = source.getSubmissionDate();

    return OrderBadgeProcessingFormRequest.builder()
        .applicationDateDay(submissionDate.getDayOfMonth())
        .applicationDateMonth(submissionDate.getMonthValue())
        .applicationDateYear(submissionDate.getYear())
        .build();
  }
}
