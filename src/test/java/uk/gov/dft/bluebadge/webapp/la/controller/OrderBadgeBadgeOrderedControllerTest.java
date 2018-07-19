package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class OrderBadgeBadgeOrderedControllerTest extends OrderBadgeBaseControllerTest {

  private static final String BADGE_NUMBER = "KKKKH4";

  private OrderBadgeBadgeOrderedController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new OrderBadgeBadgeOrderedController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayBadgeOrderedTemplateWithBadgeNumber() throws Exception {
    mockMvc
        .perform(get("/order-a-badge/badge-ordered").flashAttr("badgeNumber", BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/badge-ordered"))
        .andExpect(model().attribute("badgeNumber", BADGE_NUMBER));
  }
}
