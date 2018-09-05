package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats;

@Service
public class ModelFormatUtils {

  private DateTimeService dateTimeService;

  @Autowired
  public ModelFormatUtils(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

  public String toCommaSeparatedString(String... args) {
    StringBuilder result = new StringBuilder();
    boolean firstItem = true;
    for (String item : args) {
      if (StringUtils.isNotEmpty(item)) {
        if (firstItem) {
          result = new StringBuilder(item);
          firstItem = false;
        } else {
          result.append(", ").append(item);
        }
      }
    }
    return result.toString();
  }

  public String prescribedToMessageKey(Boolean isPrescribed) {
    if (null == isPrescribed) {
      return null;
    }

    return isPrescribed ? "isPrescribed" : "notPrescribed";
  }

  public String offsetDateTimeToFieldDisplay(OffsetDateTime time) {
    if (null == time) {
      return "";
    }
    return time.atZoneSameInstant(dateTimeService.clientZoneId())
        .format(ModelViewFormats.viewModelFieldDateTimeFormatter);
  }

  public String localDateToDisplay(LocalDate date) {
    if (null == date) {
      return "";
    }
    return date.format(ModelViewFormats.viewModelFieldDateFormatter);
  }

  public String extractBadgeHolderName(Application application) {
    if (null != application.getParty().getPerson()) {
      return application.getParty().getPerson().getBadgeHolderName();
    }
    if (null != application.getParty().getOrganisation()) {
      return application.getParty().getOrganisation().getBadgeHolderName();
    }
    return "";
  }
}
