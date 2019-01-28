package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppContact;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppParty;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.AppPerson;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Eligibility;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.GenderCodeField;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.PartyTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.OrderBadgeTestData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgeIndexFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.orderbadge.OrderBadgePersonDetailsFormRequest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class ApplicationToOrderBadgeBaseFormRequestTest extends OrderBadgeTestData {

    private ApplicationToOrderBadgeIndexFormRequest converter;

    // Mock application data
    private static final UUID APPLICATION_ID = UUID.randomUUID();
    private static final ApplicationTypeCodeField APPLICATION_TYPE = ApplicationTypeCodeField.NEW;
    private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
    private static final OffsetDateTime SUBMISSION_DATE = OffsetDateTime.of(2018, 6, 20, 10, 10, 0, 0, ZoneOffset.UTC);
    private static final String EXISTING_BADGE_NO = "ABCDEF";
    private static final LocalDate DOB = LocalDate.of(1980, 3, 15);
    protected static final Eligibility APPLICANT_ELIGIBILITY = new Eligibility().typeCode(EligibilityCodeField.fromValue(ELIGIBILITY.toUpperCase()));

    // APPLICANT PERSON
    protected static final AppPerson APP_PERSON =
            new AppPerson()
                    .genderCode(GenderCodeField.fromValue(GENDER_SHORTCODE))
                    .badgeHolderName(NAME)
                    .dob(DOB )
                    .nino(NINO);

    // APPLICANT CONTACT
    private static final AppContact APP_CONTACT =
            new AppContact()
                    .fullName(OrderBadgeTestData.NAME)
                    .primaryPhoneNumber(OrderBadgeTestData.CONTACT_DETAILS_CONTACT_NUMBER)
                    .emailAddress(OrderBadgeTestData.CONTACT_DETAILS_EMAIL_ADDRESS)
                    .buildingStreet(OrderBadgeTestData.BUILDING_AND_STREET)
                    .townCity(OrderBadgeTestData.TOWN_OR_CITY)
                    .postCode(OrderBadgeTestData.POSTCODE);

    // APPLICANT PARTY
    private static final AppParty APP_PARTY = new AppParty().contact(APP_CONTACT).person(APP_PERSON).organisation(null).typeCode(PartyTypeCodeField.PERSON);

    // APPLICATION
    protected static final Application APPLICATION =
            Application.builder()
                    .applicationId(APPLICATION_ID.toString())
                    .applicationTypeCode(APPLICATION_TYPE)
                    .localAuthorityCode(LOCAL_AUTHORITY_SHORT_CODE)
                    .eligibility(APPLICANT_ELIGIBILITY)
                    .paymentTaken(false)
                    .submissionDate(SUBMISSION_DATE)
                    .existingBadgeNumber(EXISTING_BADGE_NO)
                    .party(APP_PARTY)
                    .build();

    // FORM REQUESTS
    protected static final OrderBadgeIndexFormRequest FORM_REQUEST_INDEX =
            OrderBadgeIndexFormRequest
                    .builder()
                    .applicantType(PartyTypeCodeField.PERSON.toString().toLowerCase())
                    .build();

    protected static final OrderBadgePersonDetailsFormRequest FORM_REQUEST_PERSON_DETAILS =
            OrderBadgePersonDetailsFormRequest
                    .builder()
                    .name(NAME)
                    .nino(NINO)
                    .gender(GENDER_SHORTCODE)
                    .eligibility(ELIGIBILITY_SHORTCODE)
                    .dobDay(Integer.parseInt(DOB_DAY))
                    .dobMonth(Integer.parseInt(DOB_MONTH))
                    .dobYear(Integer.parseInt(DOB_YEAR))
                    .buildingAndStreet(BUILDING_AND_STREET)
                    .postcode(POSTCODE)
                    .townOrCity(TOWN_OR_CITY)
                    .contactDetailsName(NAME)
                    .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
                    .contactDetailsEmailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
                    .build();
}
