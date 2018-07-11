package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Contact;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgeCheckOrderControllerTest extends OrderBadgeBaseControllerTest {

  private static final String BADGE_NUMBER = "MyBadgeNumber123";
  private static final LocalDate SERVICE_MODEL_APPLICATION_DATE = LocalDate.now();
  private static final LocalDate SERVICE_MODEL_DOB = LocalDate.now().plusDays(1);
  private static final int SERVICE_MODEL_NUMBER_OF_BADGES = 1;
  private static final int LOCAL_AUTHORITY_ID = 2;
  private static final LocalDate SERVICE_MODEL_EXPIRY_DATE = LocalDate.now().plusDays(2);
  private static final LocalDate SERVICE_MODEL_START_DATE = LocalDate.now().plusDays(1);

  private OrderBadgeCheckOrderController controller;

  @Mock private OrderBadgeFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModelMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new OrderBadgeCheckOrderController(
            badgeServiceMock, converterToServiceModelMock, converterToViewModelMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayCheckOrderTemplateWithDataPopulated() throws Exception {
    OrderBadgeCheckOrderViewModel orderBadgeCheckOrderViewModel =
        OrderBadgeCheckOrderViewModel.builder()
            .gender(GENDER)
            .deliveryOptions(DELIVERY_OPTIONS)
            .applicationChannel(APPLICATION_CHANNEL)
            .applicationDate(VIEW_MODEL_APPLICATION_DATE)
            .applicationChannel(APPLICATION_CHANNEL)
            .deliverTo(DELIVER_TO)
            .badgeStartDate(VIEW_MODEL_BADGE_START_DATE)
            .badgeExpiryDate(VIEW_MODEL_BADGE_EXPIRY_DATE)
            .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
            .eligibility(ELIGIBILITY)
            .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
            .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .dob(VIEW_MODEL_DOB)
            .fullName(NAME)
            .address(VIEW_MODEL_ADDRESS)
            .nino(NINO)
            .build();
    when(converterToViewModelMock.convert(any(), any())).thenReturn(orderBadgeCheckOrderViewModel);

    mockMvc
        .perform(
            get("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_DETAILS)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PROCESSING))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("data", orderBadgeCheckOrderViewModel));
  }

  @Test
  public void submit_shouldRedirectToHomePageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadgeForAPerson(any())).thenReturn(BADGE_NUMBER);
    Contact contact =
        new Contact()
            .buildingStreet(BUILDING_AND_STREET)
            .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
            .fullName(CONTACT_DETAILS_NAME)
            .line2(OPTIONAL_ADDRESS_FIELD)
            .postCode(POSTCODE)
            .townCity(TOWN_OR_CITY)
            .primaryPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER);
    Person person =
        new Person().genderCode(GENDER).badgeHolderName(NAME).dob(SERVICE_MODEL_DOB).nino(NINO);
    Party party = new Party().contact(contact).person(person).typeCode("PERSON").organisation(null);
    BadgeOrderRequest badgeOrderRequest =
        new BadgeOrderRequest()
            .applicationDate(SERVICE_MODEL_APPLICATION_DATE)
            .applicationChannelCode(APPLICATION_CHANNEL)
            .deliverToCode(DELIVER_TO)
            .deliveryOptionCode(DELIVERY_OPTIONS)
            .eligibilityCode(ELIGIBILITY)
            .startDate(SERVICE_MODEL_START_DATE)
            .expiryDate(SERVICE_MODEL_EXPIRY_DATE)
            .localAuthorityId(LOCAL_AUTHORITY_ID)
            .localAuthorityRef(LOCAL_AUTHORITY_REFERENCE_NUMBER)
            .numberOfBadges(SERVICE_MODEL_NUMBER_OF_BADGES)
            .party(party);
    when(converterToServiceModelMock.convert(any(), any(), any())).thenReturn(badgeOrderRequest);
    mockMvc
        .perform(
            post("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_DETAILS)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PROCESSING))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(HomeController.URL));
    verify(badgeServiceMock).orderABadgeForAPerson(badgeOrderRequest);
  }
}
