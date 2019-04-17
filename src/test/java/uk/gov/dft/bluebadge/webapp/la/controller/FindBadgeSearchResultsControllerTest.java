package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeSummary;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgesResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeSummaryToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToFindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

public class FindBadgeSearchResultsControllerTest {
  public static final String SEARCH_TERM_BLANK = "    ";
  private static String BADGE_NUMBER = "HAS67SDDS3";

  private static final String NAME = "Joe BLoggs";
  private static final String POST_CODE = "M12 8N";
  private static final String LOCAL_AUTHORITY = "Manchester city council";
  private static final String LOCAL_AUTHORITY_SHORT_CODE = "MANC";
  private static final String EXPIRY_DATE = "20/12/2022";
  private static final String STATUS = "Active";
  private static final String STATUS_CODE = "A";

  private static final FindBadgeSearchResultViewModel RESULT_VIEW_MODEL =
      FindBadgeSearchResultViewModel.builder()
          .badgeNumber(BADGE_NUMBER)
          .name(NAME)
          .postCode(POST_CODE)
          .localAuthority(LOCAL_AUTHORITY)
          .expiryDate(EXPIRY_DATE)
          .status(STATUS)
          .build();
  private static BadgeSummary BADGE_SUMMARY = new BadgeSummary();

  static {
    BADGE_SUMMARY.badgeNumber(BADGE_NUMBER);
    BADGE_SUMMARY.name(NAME);
    BADGE_SUMMARY.postCode(POST_CODE);
    BADGE_SUMMARY.localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE);
    BADGE_SUMMARY.expiryDate(
        LocalDate.parse(EXPIRY_DATE, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    BADGE_SUMMARY.statusCode(STATUS_CODE);
  }

  private static Badge BADGE = new Badge();

  static {
    BADGE.setBadgeNumber(BADGE_NUMBER);
  }

  private static final String SEARCH_TERM_NAME = "a";
  private static final String SEARCH_TERM_POSTCODE = "aaa111";
  private static final String SEARCH_TERM_BADGE_NUMBER = "BadgeNumber1";

  private static final PagingInfo DEFAULT_PAGING_INFO = new PagingInfo();

  static {
    DEFAULT_PAGING_INFO.setPageNum(1);
    DEFAULT_PAGING_INFO.setPageSize(50);
  }

  private static final Integer PAGE_NUM = 6;
  private static final Integer PAGE_SIZE = 13;

  private static final PagingInfo PAGING_INFO = new PagingInfo();

  static {
    PAGING_INFO.setPageNum(PAGE_NUM);
    PAGING_INFO.setPageSize(PAGE_SIZE);
  }

  private static final List<FindBadgeSearchResultViewModel> RESULTS_VIEW_MODEL =
      Lists.newArrayList(RESULT_VIEW_MODEL, RESULT_VIEW_MODEL);

  static List<BadgeSummary> getBadgesResults() {
    return Lists.newArrayList(BADGE_SUMMARY, BADGE_SUMMARY);
  }

  static BadgesResponse getBadgesResponse(Boolean withPagingInfo) {
    List<BadgeSummary> badgesResults = getBadgesResults();
    return (BadgesResponse)
        new BadgesResponse()
            .data(badgesResults)
            .pagingInfo(withPagingInfo ? PAGING_INFO : DEFAULT_PAGING_INFO);
  }

  static BadgesResponse getSingleBadgeResponse(Boolean withPagingInfo) {
    BadgesResponse response = getBadgesResponse(withPagingInfo);
    response.getData().remove(0);
    return response;
  }

  private MockMvc mockMvc;

  @Mock private BadgeService mockBadgeService;
  @Mock private BadgeToFindBadgeSearchResultViewModel mockBadgeToFindBadgeSearchResultViewModel;

  @Mock
  private BadgeSummaryToFindBadgeSearchResultViewModel
      mockBadgeSummaryToFindBadgeSearchResultViewModel;

  private FindBadgeSearchResultsController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    controller =
        new FindBadgeSearchResultsController(
            mockBadgeService,
            mockBadgeToFindBadgeSearchResultViewModel,
            mockBadgeSummaryToFindBadgeSearchResultViewModel);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  @SneakyThrows
  public void
      show_shouldShowResultsWithDefaultPaginationConfiguration_whenFindByNameAndPagingInfoIsNotProvided() {
    BadgesResponse response = getBadgesResponse(false);

    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "name")
                .sessionAttr("searchTerm", SEARCH_TERM_NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "name"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_NAME))
        .andExpect(model().attribute("results", RESULTS_VIEW_MODEL))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void
      show_shouldShowResultsWithPaginationConfiguration_whenFindByNameAndPagingInfoIsProvided() {
    BadgesResponse response = getBadgesResponse(true);

    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, PAGING_INFO)).thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results?pageNum=" + PAGE_NUM + "&pageSize=" + PAGE_SIZE)
                .sessionAttr("findBadgeBy", "name")
                .sessionAttr("searchTerm", SEARCH_TERM_NAME))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "name"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_NAME))
        .andExpect(model().attribute("results", RESULTS_VIEW_MODEL))
        .andExpect(model().attribute("pagingInfo", PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void
      show_shouldShowResultsWithDefaultPaginationConfiguration_whenFindByPostcodeAndPagingInfoIsNotProvided() {
    BadgesResponse response = getBadgesResponse(false);

    when(mockBadgeService.findBadgeByPostcode(SEARCH_TERM_POSTCODE, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "postcode")
                .sessionAttr("searchTerm", SEARCH_TERM_POSTCODE))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "postcode"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_POSTCODE))
        .andExpect(model().attribute("results", RESULTS_VIEW_MODEL))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void show_shouldShowNoResults_whenFindByPostcodeAndPostcodeIsBlank() {
    BadgesResponse response = getBadgesResponse(false);

    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "postcode")
                .sessionAttr("searchTerm", SEARCH_TERM_BLANK))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "postcode"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_BLANK))
        .andExpect(model().attribute("results", Lists.newArrayList()))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void show_shouldShowNoResults_whenFindByNameAndNameIsBlank() {
    BadgesResponse response = getBadgesResponse(false);

    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "name")
                .sessionAttr("searchTerm", SEARCH_TERM_BLANK))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "name"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_BLANK))
        .andExpect(model().attribute("results", Lists.newArrayList()))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void show_shouldRedirectToBadgeDetails_whenFindByNameAndPagingInfoIsNotProvided() {
    BadgesResponse response = getSingleBadgeResponse(false);
    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "name")
                .sessionAttr("searchTerm", SEARCH_TERM_NAME))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            view().name("redirect:/manage-badges/" + BADGE_NUMBER + "?prev-step=find-badge"));
  }

  @Test
  @SneakyThrows
  public void show_shouldRedirectToBadgeDetails_whenFindByPostcodeAndPagingInfoIsNotProvided() {
    BadgesResponse response = getSingleBadgeResponse(false);
    when(mockBadgeService.findBadgeByPostcode(SEARCH_TERM_POSTCODE, DEFAULT_PAGING_INFO))
        .thenReturn(response);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "postcode")
                .sessionAttr("searchTerm", SEARCH_TERM_POSTCODE))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            view().name("redirect:/manage-badges/" + BADGE_NUMBER + "?prev-step=find-badge"));
  }

  @Test
  @SneakyThrows
  public void show_shouldRedirectToBadgeDetails_whenFindByBadgeNumberAndPagingInfoIsNotProvided() {
    when(mockBadgeService.retrieve(SEARCH_TERM_BADGE_NUMBER)).thenReturn(Optional.of(BADGE));
    when(mockBadgeToFindBadgeSearchResultViewModel.convert(BADGE)).thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "badgeNumber")
                .sessionAttr("searchTerm", SEARCH_TERM_BADGE_NUMBER))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            view().name("redirect:/manage-badges/" + BADGE_NUMBER + "?prev-step=find-badge"));
  }
}
