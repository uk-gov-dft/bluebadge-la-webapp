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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeOrganisationFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeOrganisationFormsToOrderBadgeCheckOrderViewModel;

public class OrderBadgeOrganisationCheckOrderControllerTest extends OrderBadgeBaseControllerTest {

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

    when(converterToViewModelMock.convert(any(), any())).thenReturn(VIEW_MODEL);

    mockMvc
        .perform(
            get("/order-a-badge/organisation/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("data", VIEW_MODEL));
  }

  @Test
  public void submit_shouldRedirectToHomePageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBER);

    when(converterToServiceModelMock.convert(any(), any()))
        .thenReturn(BADGE_ORDER_REQUEST_ORGANISATION);
    mockMvc
        .perform(
            post("/order-a-badge/organisation/check-order")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));
    verify(badgeServiceMock).orderABadge(BADGE_ORDER_REQUEST_ORGANISATION);
  }
}
