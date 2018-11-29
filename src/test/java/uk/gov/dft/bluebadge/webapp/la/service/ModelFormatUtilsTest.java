package uk.gov.dft.bluebadge.webapp.la.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.Assert;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppOrganisation;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;

public class ModelFormatUtilsTest {

  class TestDateTimeService extends DateTimeService {
    @Override
    public ZoneId clientZoneId() {
      return ZoneId.of("Europe/London");
    }
  }

  private ModelFormatUtils utils = new ModelFormatUtils(new TestDateTimeService());

  @Test
  public void toCommaSeparatedString() {
    String string1 = "1";
    String stringNull = null;
    String stringEmpty = "";
    String string2 = "2";

    Assert.assertEquals("1, 2", utils.toCommaSeparatedString(string1, string2));
    Assert.assertEquals("1, 2", utils.toCommaSeparatedString(string1, stringNull, string2));
    Assert.assertEquals(
        "1, 2", utils.toCommaSeparatedString(string1, stringNull, stringEmpty, string2));
    Assert.assertEquals("", utils.toCommaSeparatedString(stringNull));
  }

  @Test
  public void prescribedToMessageKey() {
    Assert.assertEquals(
        "application.details.medication.isPrescribed", utils.prescribedToMessageKey(Boolean.TRUE));
    Assert.assertEquals(
        "application.details.medication.notPrescribed",
        utils.prescribedToMessageKey(Boolean.FALSE));
    Assert.assertNull(utils.prescribedToMessageKey(null));
  }

  @Test
  public void offsetDateTimeToFieldDisplay() {
    OffsetDateTime testDate = OffsetDateTime.parse("1980-01-01T02:00:30+01:00");
    Assert.assertEquals("01 January 1980 at 01:00", utils.offsetDateTimeToFieldDisplay(testDate));
    Assert.assertEquals("", utils.offsetDateTimeToFieldDisplay(null));
  }

  @Test
  public void localDateToDisplay() {
    LocalDate testDate = LocalDate.of(1980, 1, 31);
    Assert.assertEquals("31 January 1980", utils.localDateToDisplay(testDate));
    Assert.assertEquals("", utils.localDateToDisplay(null));
  }

  @Test
  public void extractBadgeHolderName() {
    Application application = Application.builder().party(new AppParty()).build();

    // Org
    application.getParty().setOrganisation(new AppOrganisation().badgeHolderName("Org"));
    Assert.assertEquals("Org", utils.extractBadgeHolderName(application));

    application.getParty().setOrganisation(null);
    application.getParty().setPerson(new AppPerson().badgeHolderName("Person"));
    Assert.assertEquals("Person", utils.extractBadgeHolderName(application));
  }

  @Test
  public void parseNewLinesToArray() {
    String noNewLines = "Stuff";
    String newLines = "Stuff1\nStuff2\nStuff3";

    Assert.assertEquals(1, utils.parseNewLinesToArray(noNewLines).length);
    Assert.assertEquals(noNewLines, utils.parseNewLinesToArray(noNewLines)[0]);

    Assert.assertEquals(3, utils.parseNewLinesToArray(newLines).length);
    Assert.assertEquals("Stuff3", utils.parseNewLinesToArray(newLines)[2]);

    Assert.assertNull(utils.parseNewLinesToArray(null));
  }
}
