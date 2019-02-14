package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.advice.ErrorControllerAdvice;

public class OrderBadgeBadgeOrderedControllerTest extends OrderBadgeControllerTestData {

  private static final String BADGE_NUMBER = "KKKKH4";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    OrderBadgeBadgeOrderedController controller = new OrderBadgeBadgeOrderedController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .setControllerAdvice(new ErrorControllerAdvice(new ObjectMapper()))
            .build();
  }

  @Test
  public void show_shouldDisplayBadgeOrderedTemplateWithBadgeNumber() throws Exception {
    mockMvc
        .perform(get("/order-a-badge/badge-ordered").flashAttr("badgeNumbers", BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/badge-ordered"))
        .andExpect(model().attribute("badgeNumbers", BADGE_NUMBER));
  }

  @Test
  public void show_whenNoBadgeNumbers_thenRedirectToOrderBadgeHome() throws Exception {
    mockMvc
        .perform(get("/order-a-badge/badge-ordered"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(OrderBadgeIndexController.ORDER_BADGE_RESET_URL));
  }
}
