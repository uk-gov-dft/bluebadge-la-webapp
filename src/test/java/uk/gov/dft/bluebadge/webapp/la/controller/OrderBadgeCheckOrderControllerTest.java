package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.OrderBadgeFormsToOrderBadgeCheckOrderViewModel;

public class OrderBadgeCheckOrderControllerTest extends OrderBadgeBaseControllerTest {

  private OrderBadgeCheckOrderController controller;

  @Mock private OrderBadgeFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModelMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new OrderBadgeCheckOrderController(
            badgeServiceMock,
            referenceDataServiceMock,
            converterToServiceModelMock,
            converterToViewModelMock);

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
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_DETAILS)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PROCESSING))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"));
  }
}
