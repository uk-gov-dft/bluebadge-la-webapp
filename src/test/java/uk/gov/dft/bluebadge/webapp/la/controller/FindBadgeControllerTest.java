package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

public class FindBadgeControllerTest {

  public static final String NAME = "jason";
  public static final String INVALID_SEARCH_BADGE_BY_OPTION = "badOptionValue";
  public static final String INVALID_BADGE_NUMBER = "12345678";
  private final String BADGE_NUMBER = "AAAAA1";
  private final String FIND_BY_POSTCODE = "postCode";
  private final String POSTCODE = "L129PZ";
  private final Badge BADGE =
      new Badge().badgeNumber(BADGE_NUMBER).localAuthorityRef("LocalAuthorityRef");
  private final FindBadgeSearchResultViewModel VIEW_MODEL =
      FindBadgeSearchResultViewModel.builder().badgeNumber(BADGE_NUMBER).build();

  private MockMvc mockMvc;

  @Mock BadgeService badgeServiceMock;

  @Mock BadgeToFindBadgeSearchResultViewModel converterToViewModelMock;
  @Mock BadgeSummaryToFindBadgeSearchResultViewModel badgeSummartyconverterToViewModelMock;

  private FindBadgeController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new FindBadgeController(
            badgeServiceMock, converterToViewModelMock, badgeSummartyconverterToViewModelMock);

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
        .andExpect(redirectedUrl("/find-a-badge/search-results"));
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenNoResultsAreFoundForBadgeNumber()
      throws Exception {
    when(badgeServiceMock.retrieve("12345678")).thenReturn(Optional.empty());
    List<FindBadgeSearchResultViewModel> expectedResults = Lists.newArrayList();

    mockMvc
        .perform(
            post("/find-a-badge")
                .param("findBadgeBy", "badgeNumber")
                .param("searchTerm", INVALID_BADGE_NUMBER))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"));
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenInvalidSearchTermIsPassed()
      throws Exception {
    List<FindBadgeSearchResultViewModel> expectedResults = Lists.newArrayList();

    mockMvc
        .perform(post("/find-a-badge").param("findBadgeBy", "postcode").param("searchTerm", ""))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"));

    verify(badgeServiceMock, times(0)).retrieve(any());
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenFindABadgeByOptionIsInvalid()
      throws Exception {

    List<FindBadgeSearchResultViewModel> expectedResults = Lists.newArrayList();

    mockMvc
        .perform(
            post("/find-a-badge")
                .param("findBadgeBy", INVALID_SEARCH_BADGE_BY_OPTION)
                .param("searchTerm", "12345678"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"));

    verify(badgeServiceMock, times(0)).retrieve(any());
  }

  @Test
  public void
      submit_shouldRedirectToSearchResultsWithResultsPopulated_WhenFormSearchingUsingPostCode()
          throws Exception {

    BadgeSummary badgeOne = new BadgeSummary();
    BadgeSummary badgeTwo = new BadgeSummary();
    List<BadgeSummary> badges = Lists.newArrayList(badgeOne, badgeTwo);

    FindBadgeSearchResultViewModel viewModel1 = FindBadgeSearchResultViewModel.builder().build();
    FindBadgeSearchResultViewModel viewModel2 = FindBadgeSearchResultViewModel.builder().build();
    List<FindBadgeSearchResultViewModel> expectedResults =
        Lists.newArrayList(viewModel1, viewModel2);

    when(badgeServiceMock.findBadgeByPostcode(POSTCODE)).thenReturn(badges);
    when(badgeSummartyconverterToViewModelMock.convert(badgeOne)).thenReturn(viewModel1);
    when(badgeSummartyconverterToViewModelMock.convert(badgeTwo)).thenReturn(viewModel2);

    mockMvc
        .perform(
            post("/find-a-badge").param("findBadgeBy", "postcode").param("searchTerm", POSTCODE))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"));
  }

  @Test
  public void submit_shouldRedirectToSearchResultsWithResultsPopulated_WhenFormSearchingUsingName()
      throws Exception {

    BadgeSummary badgeOne = new BadgeSummary();
    BadgeSummary badgeTwo = new BadgeSummary();
    List<BadgeSummary> badges = Lists.newArrayList(badgeOne, badgeTwo);

    FindBadgeSearchResultViewModel viewModel1 = FindBadgeSearchResultViewModel.builder().build();
    FindBadgeSearchResultViewModel viewModel2 = FindBadgeSearchResultViewModel.builder().build();
    List<FindBadgeSearchResultViewModel> expectedResults =
        Lists.newArrayList(viewModel1, viewModel2);

    when(badgeServiceMock.findBadgeByName(NAME)).thenReturn(badges);
    when(badgeSummartyconverterToViewModelMock.convert(badgeOne)).thenReturn(viewModel1);
    when(badgeSummartyconverterToViewModelMock.convert(badgeTwo)).thenReturn(viewModel2);

    mockMvc
        .perform(post("/find-a-badge").param("findBadgeBy", "name").param("searchTerm", NAME))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"));
  }
}
