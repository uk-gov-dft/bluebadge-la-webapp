package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ApplicationToOrderBadgeIndexFormRequestTest extends OrderBadgeTestData {

    private ApplicationToOrderBadgeIndexFormRequest converter;

    // Mock application data
    private static final UUID APPLICATION_ID = UUID.randomUUID();
    private static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
    private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
    private static final OffsetDateTime SUBMISSION_DATE = OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
    private static final String EXISTING_BADGE_NO = "ABCDEF";

    private static final AppContact APP_CONTACT =
            new AppContact()
                    .fullName(OrderBadgeTestData.NAME)
                    .primaryPhoneNumber(OrderBadgeTestData.CONTACT_DETAILS_CONTACT_NUMBER)
                    .emailAddress(OrderBadgeTestData.CONTACT_DETAILS_EMAIL_ADDRESS)
                    .buildingStreet(OrderBadgeTestData.BUILDING_AND_STREET)
                    .townCity(OrderBadgeTestData.TOWN_OR_CITY)
                    .postCode(OrderBadgeTestData.POSTCODE);

    private static final AppParty APP_PARTY = new AppParty().contact(APP_CONTACT);

    // Application Summary objects
    protected static final Application APPLICATION =
            Application.builder()
                    .applicationId(APPLICATION_ID.toString())
                    .applicationTypeCode(APPLICATION_TYPE)
                    .localAuthorityCode(LOCAL_AUTHORITY_SHORT_CODE)
                    .paymentTaken(false)
                    .submissionDate(SUBMISSION_DATE)
                    .existingBadgeNumber(EXISTING_BADGE_NO)
                    .party(APP_PARTY)
                    .build();

    @Before
    public void setUp() {
        converter = new ApplicationToOrderBadgeIndexFormRequest();
    }

    @Test
    public void convert_shouldWork() {
        OrderBadgeIndexFormRequest formRequest = converter.convert(APPLICATION);
        assertThat(formRequest).isEqualTo(FORM_REQUEST_INDEX);
    }
}
