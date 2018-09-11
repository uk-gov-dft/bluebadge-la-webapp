package uk.gov.dft.bluebadge.webapp.la.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ModelViewFormats;

// Accessed by Thymeleaf templates.
@SuppressWarnings("WeakerAccess")
@Service
public class ModelFormatUtils {

  private DateTimeService dateTimeService;

  @Autowired
  public ModelFormatUtils(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

  /**
   * Return a single line string with not null inputs comma separated.
   *
   * @param args List of Strings to conacatnate with a comma separator.
   * @return Concatenated alues
   */
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

  /**
   * Message key based upon medication.isPrescribed.
   *
   * @param isPrescribed medication.isPrescribed
   * @return The property key
   */
  public String prescribedToMessageKey(Boolean isPrescribed) {
    if (null == isPrescribed) {
      return null;
    }

    return isPrescribed ? "application.details.medication.isPrescribed" : "application.details.medication.notPrescribed";
  }

  /**
   * Get formatted OffsetDateTime as string for display in details screens.
   *
   * @param time Time to format.
   * @return Formatted time.
   */
  public String offsetDateTimeToFieldDisplay(OffsetDateTime time) {
    if (null == time) {
      return "";
    }
    return time.atZoneSameInstant(dateTimeService.clientZoneId())
        .format(ModelViewFormats.viewModelFieldDateTimeFormatter);
  }

  /**
   * Get formatted LocalDate as string for display in details screens.
   *
   * @param date Date to format.
   * @return Formatted date.
   */
  public String localDateToDisplay(LocalDate date) {
    if (null == date) {
      return "";
    }
    return date.format(ModelViewFormats.viewModelFieldDateFormatter);
  }

  /**
   * Get badge holder name whether Peron or Org application.
   *
   * @param application The application.
   * @return Badge holders name.
   */
  public String extractBadgeHolderName(Application application) {
    Assert.notNull(application.getParty(), "Must have party.");
    if (null != application.getParty().getPerson()) {
      return application.getParty().getPerson().getBadgeHolderName();
    }
    if (null != application.getParty().getOrganisation()) {
      return application.getParty().getOrganisation().getBadgeHolderName();
    }
    return "";
  }

  public String[] parseNewLinesToArray(String text) {
    return StringUtils.split(text, "\n");
  }
}
