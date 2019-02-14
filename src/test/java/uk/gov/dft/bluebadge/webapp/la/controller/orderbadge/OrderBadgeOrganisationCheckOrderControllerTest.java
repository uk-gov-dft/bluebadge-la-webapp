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
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class OrderBadgeOrganisationCheckOrderControllerTest extends OrderBadgeControllerTestData {

  private OrderBadgeCheckOrderController controller;

  @Mock private OrderBadgeOrganisationFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private ReferenceDataService referenceDataServiceMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new OrderBadgeCheckOrderController(
            badgeServiceMock, referenceDataServiceMock, converterToServiceModelMock, null);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayCheckOrderTemplateWithDataPopulated() throws Exception {
    mockMvc
        .perform(
            get("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-index", FORM_REQUEST_INDEX_ORG)
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING)
                .param("fid", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("details", FORM_REQUEST_ORGANISATION_DETAILS))
        .andExpect(model().attribute("processing", FORM_REQUEST_ORGANISATION_PROCESSING))
        .andExpect(model().attribute("flowId", FLOW_ID));
  }

  @Test
  public void submit_shouldRedirectToOrderedPageAndCreateABadge() throws Exception {
    when(converterToServiceModelMock.convert(any(), any()))
        .thenReturn(BADGE_ORDER_REQUEST_ORGANISATION);
    when(badgeServiceMock.orderABadge(BADGE_ORDER_REQUEST_ORGANISATION)).thenReturn(BADGE_NUMBERS);

    mockMvc
        .perform(
            post("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-index", FORM_REQUEST_INDEX_ORG)
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_ORGANISATION_DETAILS)
                .sessionAttr(
                    "formRequest-order-a-badge-processing", FORM_REQUEST_ORGANISATION_PROCESSING)
                .param("flowId", FLOW_ID))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));
    verify(badgeServiceMock).orderABadge(BADGE_ORDER_REQUEST_ORGANISATION);
  }
}
