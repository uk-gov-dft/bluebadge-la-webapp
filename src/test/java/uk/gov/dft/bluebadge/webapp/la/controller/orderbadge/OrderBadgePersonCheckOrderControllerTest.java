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

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgePersonFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;

public class OrderBadgePersonCheckOrderControllerTest extends OrderBadgeBaseControllerTest {

  private static final String BADGE_NUMBER = "MyBadgeNumber123";
  private static final List<String> BADGE_NUMBERS = Lists.newArrayList(BADGE_NUMBER);

  private static final OrderBadgeCheckOrderViewModel VIEW_MODEL =
      OrderBadgeCheckOrderViewModel.builder()
          .gender(GENDER)
          .deliveryOptions(DELIVERY_OPTIONS)
          .applicationChannel(APPLICATION_CHANNEL)
          .applicationDate(VIEW_MODEL_APPLICATION_DATE)
          .applicationChannel(APPLICATION_CHANNEL)
          .deliverTo(DELIVER_TO_SHORTCODE)
          .badgeStartDate(VIEW_MODEL_BADGE_START_DATE)
          .badgeExpiryDate(VIEW_MODEL_BADGE_EXPIRY_DATE)
          .localAuthorityReference(LOCAL_AUTHORITY_REFERENCE_NUMBER)
          .eligibility(ELIGIBILITY)
          .emailAddress(CONTACT_DETAILS_EMAIL_ADDRESS)
          .contactFullName(CONTACT_DETAILS_NAME)
          .contactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
          .dob(VIEW_MODEL_DOB)
          .fullName(NAME)
          .address(VIEW_MODEL_ADDRESS)
          .nino(NINO)
          .build();

  @Mock private OrderBadgePersonFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private OrderBadgePersonFormsToOrderBadgeCheckOrderViewModel converterToViewModelMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    OrderBadgeBaseController controller =
        new OrderBadgePersonCheckOrderController(
            badgeServiceMock, converterToServiceModelMock, converterToViewModelMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayCheckOrderTemplateWithDataPopulated() throws Exception {

    when(converterToViewModelMock.convert(any(), any())).thenReturn(VIEW_MODEL);

    mockMvc
        .perform(
            get("/order-a-badge/person/check-order")
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("data", VIEW_MODEL));
  }

  @Test
  public void submit_shouldRedirectToHomePageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBERS);

    when(converterToServiceModelMock.convert(any(), any())).thenReturn(BADGE_ORDER_REQUEST_PERSON);

    mockMvc
        .perform(
            post("/order-a-badge/person/check-order")
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITHOUT_IMAGE)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));

    verify(badgeServiceMock).orderABadge(BADGE_ORDER_REQUEST_PERSON);
  }

  @Test
  public void submit_withImageUploaded_shouldRedirectToHomePageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBERS);

    when(converterToServiceModelMock.convert(any(), any()))
        .thenReturn(BADGE_ORDER_REQUEST_PERSON_WITH_IMAGE);

    mockMvc
        .perform(
            post("/order-a-badge/person/check-order")
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));

    verify(badgeServiceMock)
        .orderABadgeForAPerson(BADGE_ORDER_REQUEST_PERSON_WITH_IMAGE, "thumbnail".getBytes());
  }
}
