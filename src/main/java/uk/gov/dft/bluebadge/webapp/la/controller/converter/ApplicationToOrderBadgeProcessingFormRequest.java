package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeProcessingFormRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class ApplicationToOrderBadgeProcessingFormRequest implements Converter<Application, OrderBadgeProcessingFormRequest> {

  @Override
  public OrderBadgeProcessingFormRequest convert(Application source) {
    Assert.notNull(source, "Source cannot be null");

    AppPerson appPerson = source.getParty().getPerson();
    AppContact appContact = source.getParty().getContact();
    OffsetDateTime submissionDate = source.getSubmissionDate();

    return OrderBadgeProcessingFormRequest.builder()
            .applicationDateDay(submissionDate.getDayOfMonth())
            .applicationDateMonth(submissionDate.getMonthValue())
            .applicationDateYear(submissionDate.getYear())
          .build();
  }
}