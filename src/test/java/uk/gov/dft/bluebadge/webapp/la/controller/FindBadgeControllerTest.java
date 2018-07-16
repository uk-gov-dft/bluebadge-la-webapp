package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeEntityToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

public class FindBadgeControllerTest {

  private final String BADGE_NUMBER = "AAAAA1";
  private final Badge BADGE =
      new Badge().badgeNumber(BADGE_NUMBER).localAuthorityRef("LocalAuthorityRef");
  private final FindBadgeSearchResultViewModel VIEW_MODEL =
      FindBadgeSearchResultViewModel.builder().badgeNumber(BADGE_NUMBER).build();

  private MockMvc mockMvc;

  @Mock BadgeService badgeServiceMock;

  @Mock BadgeEntityToFindBadgeSearchResultViewModel converterToViewModelMock;

  private FindBadgeController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new FindBadgeController(badgeServiceMock, converterToViewModelMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayFindBadgeTemplateWithEmptyValues() throws Exception {
    FindBadgeFormRequest formRequest = FindBadgeFormRequest.builder().build();
    mockMvc
        .perform(get("/find-a-badge"))
        .andExpect(status().isOk())
        .andExpect(view().name("find-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void
      submit_shouldRedirectToFindBadgeTemplateWithValidationErrors_WhenFormIsSubmittedWithEmptyValues()
          throws Exception {
    mockMvc
        .perform(post("/find-a-badge"))
        .andExpect(status().isOk())
        .andExpect(view().name("find-a-badge/index"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "findBadgeBy", "NotBlank"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void
      submit_shouldRedirectToSearchResultsWithResultsPopulated_WhenFormIsSubmittedWithValidFormValues()
          throws Exception {
    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(BADGE));
    when(converterToViewModelMock.convert(BADGE)).thenReturn(VIEW_MODEL);
    List<FindBadgeSearchResultViewModel> expectedResults = Lists.newArrayList(VIEW_MODEL);

    mockMvc
        .perform(
            post("/find-a-badge")
                .param("findBadgeBy", "badgeNumber")
                .param("searchTerm", BADGE_NUMBER))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"))
        .andExpect(flash().attribute("results", expectedResults))
        .andExpect(flash().attribute("searchTerm", BADGE_NUMBER));
  }
}
