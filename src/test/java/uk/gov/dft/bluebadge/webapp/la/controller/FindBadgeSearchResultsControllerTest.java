package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;

public class FindBadgeSearchResultsControllerTest {

  private MockMvc mockMvc;

  private FindBadgeSearchResultsController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new FindBadgeSearchResultsController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldFindBadgeSearchResultsTemplateWithResults() throws Exception {
    FindBadgeSearchResultViewModel result =
        FindBadgeSearchResultViewModel.builder()
            .badgeNumber("HAS67SDDS3")
            .name("Joe BLoggs")
            .postCode("M12 8N")
            .localAuthority("Manchester city council")
            .expiryDate("20/12/22")
            .status("Active")
            .build();
    List<FindBadgeSearchResultViewModel> results = Lists.newArrayList(result);

    mockMvc
        .perform(get("/find-a-badge/search-results").flashAttr("results", results))
        .andExpect(status().isOk())
        .andExpect(view().name("find-a-badge/search-results"))
        .andExpect(model().attribute("results", results));
  }
}
