package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

public class FindBadgeControllerTest {

  private static final String NAME = "jason";
  private static final String INVALID_SEARCH_BADGE_BY_OPTION = "badOptionValue";
  private static final String INVALID_BADGE_NUMBER = "12345678";
  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String FIND_BY_POSTCODE = "postcode";
  private static final String POSTCODE = "L129PZ";
  private static final Badge BADGE =
      new Badge().badgeNumber(BADGE_NUMBER).localAuthorityRef("LocalAuthorityRef");
  private static final FindBadgeSearchResultViewModel VIEW_MODEL =
      FindBadgeSearchResultViewModel.builder().badgeNumber(BADGE_NUMBER).build();
  private static final String LA_SHORT_CODE = "ABERD";

  private MockMvc mockMvc;

  @Mock BadgeService badgeServiceMock;

  @Mock BadgeToFindBadgeSearchResultViewModel converterToViewModelMock;
  @Mock BadgeSummaryToFindBadgeSearchResultViewModel badgeSummartyconverterToViewModelMock;
  @Mock SecurityUtils securityUtilsMock;

  private FindBadgeController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller =
        new FindBadgeController(
            badgeServiceMock,
            converterToViewModelMock,
            badgeSummartyconverterToViewModelMock,
            securityUtilsMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayFindBadgeTemplateWithEmptyValues() throws Exception {
    FindBadgeFormRequest formRequest = FindBadgeFormRequest.builder().build();
    mockMvc
        .perform(get("/manage-badges"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void
      submit_shouldRedirectToFindBadgeTemplateWithValidationErrors_WhenFormIsSubmittedWithEmptyValues()
          throws Exception {
    mockMvc
        .perform(post("/manage-badges"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/index"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "findBadgeBy", "NotBlank"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void
      submit_shouldRedirectToSearchResultsWithResultsPopulated_WhenFormIsSubmittedWithValidFormValues()
          throws Exception {
    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(BADGE));
    when(converterToViewModelMock.convert(BADGE)).thenReturn(VIEW_MODEL);

    HttpSession session =
        mockMvc
            .perform(
                post("/manage-badges")
                    .param("findBadgeBy", "badgeNumber")
                    .param("searchTerm", BADGE_NUMBER))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();
    assertThat(session.getAttribute("results")).isEqualTo(Lists.newArrayList(VIEW_MODEL));
    assertThat(session.getAttribute("searchTerm")).isEqualTo(BADGE_NUMBER);
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenNoResultsAreFoundForBadgeNumber()
      throws Exception {
    when(badgeServiceMock.retrieve("12345678")).thenReturn(Optional.empty());
    HttpSession session =
        mockMvc
            .perform(
                post("/manage-badges")
                    .param("findBadgeBy", "badgeNumber")
                    .param("searchTerm", INVALID_BADGE_NUMBER))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();
    assertThat(session.getAttribute("results")).isEqualTo(Lists.newArrayList());
    assertThat(session.getAttribute("searchTerm")).isEqualTo(INVALID_BADGE_NUMBER);
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenInvalidSearchTermIsPassed()
      throws Exception {
    HttpSession session =
        mockMvc
            .perform(
                post("/manage-badges")
                    .param("findBadgeBy", FIND_BY_POSTCODE)
                    .param("searchTerm", ""))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();

    assertThat(session.getAttribute("results")).isEqualTo(Lists.newArrayList());
    assertThat(session.getAttribute("searchTerm")).isEqualTo("");
    verify(badgeServiceMock, times(0)).retrieve(any());
  }

  @Test
  public void submit_shouldRedirectToSearchResults_whenFindABadgeByOptionIsInvalid()
      throws Exception {
    HttpSession session =
        mockMvc
            .perform(
                post("/manage-badges")
                    .param("findBadgeBy", INVALID_SEARCH_BADGE_BY_OPTION)
                    .param("searchTerm", "12345678"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();

    assertThat(session.getAttribute("results")).isEqualTo(Lists.newArrayList());
    assertThat(session.getAttribute("searchTerm")).isEqualTo("12345678");
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

    when(badgeServiceMock.findBadgeByPostcode(POSTCODE)).thenReturn(badges);
    when(badgeSummartyconverterToViewModelMock.convert(badgeOne)).thenReturn(viewModel1);
    when(badgeSummartyconverterToViewModelMock.convert(badgeTwo)).thenReturn(viewModel2);

    HttpSession session =
        mockMvc
            .perform(
                post("/manage-badges")
                    .param("findBadgeBy", FIND_BY_POSTCODE)
                    .param("searchTerm", POSTCODE))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();

    assertThat(session.getAttribute("results"))
        .isEqualTo(Lists.newArrayList(viewModel1, viewModel2));
    assertThat(session.getAttribute("searchTerm")).isEqualTo(POSTCODE);
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

    HttpSession session =
        mockMvc
            .perform(post("/manage-badges").param("findBadgeBy", "name").param("searchTerm", NAME))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/manage-badges/search-results"))
            .andReturn()
            .getRequest()
            .getSession();

    assertThat(session.getAttribute("results")).isEqualTo(expectedResults);
    assertThat(session.getAttribute("searchTerm")).isEqualTo(NAME);
  }

  @Test
  public void exportAllLaBadges_shouldReturnFile() throws Exception {
    when(securityUtilsMock.getCurrentLocalAuthorityShortCode()).thenReturn(LA_SHORT_CODE);
    byte[] byteContent = "response".getBytes();
    when(badgeServiceMock.exportBadgesByLa(LA_SHORT_CODE)).thenReturn(byteContent);

    mockMvc
        .perform(get("/manage-badges/export-all-la-badges"))
        .andExpect(status().isOk())
        .andExpect(
            header()
                .string(
                    "Content-Disposition",
                    "attachment;filename=" + LocalDate.now() + "_" + LA_SHORT_CODE + ".zip"))
        .andExpect(header().string("Content-Type", "application/zip"))
        .andExpect(content().bytes(byteContent))
        .andReturn();
  }
}
