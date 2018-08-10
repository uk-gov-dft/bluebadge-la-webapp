package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

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
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Organisation;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeOrganisationFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgeOrganisationCheckOrderControllerTest extends OrderBadgeBaseControllerTest {

  private static final String BADGE_NUMBER = "MyBadgeNumber123";
  private static final LocalDate SERVICE_MODEL_APPLICATION_DATE = LocalDate.now();
  private static final LocalDate SERVICE_MODEL_DOB = LocalDate.now().plusDays(1);
  private static final int SERVICE_MODEL_NUMBER_OF_BADGES =
      Integer.parseInt(NUMBER_OF_BADGES_ORGANISATION);
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "ABERD";
  private static final LocalDate SERVICE_MODEL_EXPIRY_DATE = LocalDate.now().plusDays(2);
  private static final LocalDate SERVICE_MODEL_START_DATE = LocalDate.now().plusDays(1);

  private OrderBadgeOrganisationCheckOrderController controller;

  @Mock private OrderBadgeOrganisationFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel converterToViewModelMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new OrderBadgeOrganisationCheckOrderController(
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
            .deliveryOptions(DELIVERY_OPTIONS)
            .applicationChannel(APPLICATION_CHANNEL)
            .applicationDate(VIEW_MODEL_APPLICATION_DATE)
            .applicationChannel(APPLICATION_CHANNEL)
            .deliverTo(DELIVER_TO)
            .badgeStartDate(VIEW_MODEL_BADGE_START_DATE)
            .badgeExpiryDate(VIEW_MODEL_BADGE_EXPIRY_DATE)
            .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
            .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
            .contactFullName(CONTACT_DETAILS_NAME)
            .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .secondaryContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .fullName(NAME)
            .address(VIEW_MODEL_ADDRESS)
            .numberOfBadges(NUMBER_OF_BADGES_ORGANISATION)
            .build();
    when(converterToViewModelMock.convert(any(), any())).thenReturn(orderBadgeCheckOrderViewModel);

    mockMvc
        .perform(
            get("/order-a-badge/organisation/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("data", orderBadgeCheckOrderViewModel));
  }

  @Test
  public void submit_shouldRedirectToHomePageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBER);
    Contact contact =
        new Contact()
            .buildingStreet(BUILDING_AND_STREET)
            .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
            .fullName(CONTACT_DETAILS_NAME)
            .line2(OPTIONAL_ADDRESS_FIELD)
            .postCode(POSTCODE)
            .townCity(TOWN_OR_CITY)
            .primaryPhoneNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .secondaryPhoneNumber(CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER);
    Organisation organisation = new Organisation().badgeHolderName(NAME);
    Party party =
        new Party().contact(contact).organisation(organisation).typeCode("ORG").person(null);
    BadgeOrderRequest badgeOrderRequest =
        new BadgeOrderRequest()
            .applicationDate(SERVICE_MODEL_APPLICATION_DATE)
            .applicationChannelCode(APPLICATION_CHANNEL)
            .deliverToCode(DELIVER_TO)
            .deliveryOptionCode(DELIVERY_OPTIONS)
            .startDate(SERVICE_MODEL_START_DATE)
            .expiryDate(SERVICE_MODEL_EXPIRY_DATE)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .localAuthorityRef(LOCAL_AUTHORITY_REFERENCE_NUMBER)
            .numberOfBadges(SERVICE_MODEL_NUMBER_OF_BADGES)
            .party(party);
    when(converterToServiceModelMock.convert(any(), any())).thenReturn(badgeOrderRequest);
    mockMvc
        .perform(
            post("/order-a-badge/organisation/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));
    verify(badgeServiceMock).orderABadge(badgeOrderRequest);
  }
}
