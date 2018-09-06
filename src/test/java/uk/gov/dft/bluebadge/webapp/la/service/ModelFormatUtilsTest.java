package uk.gov.dft.bluebadge.webapp.la.service;

import org.junit.Assert;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppOrganisation;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class ModelFormatUtilsTest {

  class TestDateTimeService extends DateTimeService{
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
    Assert.assertEquals("1, 2", utils.toCommaSeparatedString(string1, stringNull, stringEmpty, string2));
    Assert.assertEquals("", utils.toCommaSeparatedString(stringNull));
  }

  @Test
  public void prescribedToMessageKey() {
    Assert.assertEquals("medication.isPrescribed", utils.prescribedToMessageKey(Boolean.TRUE));
    Assert.assertEquals("medication.notPrescribed", utils.prescribedToMessageKey(Boolean.FALSE));
    Assert.assertEquals(null, utils.prescribedToMessageKey(null));
  }

  @Test
  public void offsetDateTimeToFieldDisplay() {
    OffsetDateTime testDate = OffsetDateTime.parse("1980-01-01T02:00:30+01:00");
    Assert.assertEquals("01 January 1980 at 01:00", utils.offsetDateTimeToFieldDisplay(testDate));
    Assert.assertEquals("", utils.offsetDateTimeToFieldDisplay(null));
  }

  @Test
  public void localDateToDisplay() {
    LocalDate testDate = LocalDate.of(1980, 01, 31);
    Assert.assertEquals("31 January 1980", utils.localDateToDisplay(testDate));
    Assert.assertEquals("", utils.localDateToDisplay(null));
  }

  @Test
  public void extractBadgeHolderName() {
    Application application = new Application();
    application.setParty(new AppParty());

    // Org
    application.getParty().setOrganisation(new AppOrganisation().badgeHolderName("Org"));
    Assert.assertEquals("Org", utils.extractBadgeHolderName(application));

    application.getParty().setOrganisation(null);
    application.getParty().setPerson(new AppPerson().badgeHolderName("Person"));
    Assert.assertEquals("Person", utils.extractBadgeHolderName(application));

  }
}