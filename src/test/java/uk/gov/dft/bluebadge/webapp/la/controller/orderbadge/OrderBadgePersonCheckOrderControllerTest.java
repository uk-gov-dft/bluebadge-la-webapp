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
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class OrderBadgePersonCheckOrderControllerTest extends OrderBadgeControllerTestData {

  private static final String BADGE_NUMBER = "MyBadgeNumber123";
  private static final List<String> BADGE_NUMBERS = Lists.newArrayList(BADGE_NUMBER);

  @Mock private OrderBadgePersonFormsToBadgeOrderRequest converterToServiceModelMock;
  @Mock private ReferenceDataService referenceDataServiceMock;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    OrderBadgeCheckOrderController controller =
        new OrderBadgeCheckOrderController(
            badgeServiceMock, referenceDataServiceMock, null, converterToServiceModelMock);

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
                .sessionAttr("formRequest-order-a-badge-index", FORM_REQUEST_INDEX_PERSON)
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING)
                .param("fid", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/check-order"))
        .andExpect(model().attribute("details", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE))
        .andExpect(model().attribute("processing", FORM_REQUEST_PERSON_PROCESSING))
        .andExpect(model().attribute("flowId", FLOW_ID));
  }

  @Test
  public void submit_shouldRedirectToOrderedPageAndCreateABadge() throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBERS);
    when(converterToServiceModelMock.convert(any(), any())).thenReturn(BADGE_ORDER_REQUEST_PERSON);

    mockMvc
        .perform(
            post("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-index", FORM_REQUEST_INDEX_PERSON)
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITHOUT_IMAGE)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING)
                .param("flowId", FLOW_ID))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));

    verify(badgeServiceMock).orderABadge(BADGE_ORDER_REQUEST_PERSON);
  }

  @Test
  public void submit_withImageUploaded_shouldRedirectToOrderedPageAndCreateABadge()
      throws Exception {
    when(badgeServiceMock.orderABadge(any())).thenReturn(BADGE_NUMBERS);

    when(converterToServiceModelMock.convert(any(), any()))
        .thenReturn(BADGE_ORDER_REQUEST_PERSON_WITH_IMAGE);

    mockMvc
        .perform(
            post("/order-a-badge/check-order")
                .sessionAttr("formRequest-order-a-badge-index", FORM_REQUEST_INDEX_PERSON)
                .sessionAttr(
                    "formRequest-order-a-badge-details", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE)
                .sessionAttr("formRequest-order-a-badge-processing", FORM_REQUEST_PERSON_PROCESSING)
                .param("flowId", FLOW_ID))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeBadgeOrderedController.URL));

    verify(badgeServiceMock)
        .orderABadgeForAPerson(BADGE_ORDER_REQUEST_PERSON_WITH_IMAGE, "thumbnail".getBytes());
  }
}
