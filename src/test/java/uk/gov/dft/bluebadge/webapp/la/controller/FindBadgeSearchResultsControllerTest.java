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
      Lists.newArrayList(RESULT_VIEW_MODEL);
  private static final List<BadgeSummary> RESULTS = Lists.newArrayList(BADGE_SUMMARY);
  private static final BadgesResponse RESULTS_RESPONSE =
      (BadgesResponse) new BadgesResponse().data(RESULTS).pagingInfo(DEFAULT_PAGING_INFO);
  private static final BadgesResponse RESULTS_RESPONSE_WITH_PAGING_INFO =
      (BadgesResponse) new BadgesResponse().data(RESULTS).pagingInfo(PAGING_INFO);

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
    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(RESULTS_RESPONSE);
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
    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, PAGING_INFO))
        .thenReturn(RESULTS_RESPONSE_WITH_PAGING_INFO);
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
    when(mockBadgeService.findBadgeByPostcode(SEARCH_TERM_POSTCODE, DEFAULT_PAGING_INFO))
        .thenReturn(RESULTS_RESPONSE);
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
  public void show_shouldShowResultsWithValidationErrors_whenFindByPostcodeAndPostcodeIsBlank() {
    when(mockBadgeService.findBadgeByName(SEARCH_TERM_NAME, DEFAULT_PAGING_INFO))
        .thenReturn(RESULTS_RESPONSE);
    when(mockBadgeSummaryToFindBadgeSearchResultViewModel.convert(BADGE_SUMMARY))
        .thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "postcode")
                .sessionAttr("searchTerm", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "postcode"))
        .andExpect(model().attribute("searchTerm", ""))
        .andExpect(model().attribute("results", Lists.newArrayList()))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }

  @Test
  @SneakyThrows
  public void
      show_shouldShowResultsWithDefaultPaginationConfiguration_whenFindByBadgeNumberAndPagingInfoIsNotProvided() {
    when(mockBadgeService.retrieve(SEARCH_TERM_BADGE_NUMBER)).thenReturn(Optional.of(BADGE));
    when(mockBadgeToFindBadgeSearchResultViewModel.convert(BADGE)).thenReturn(RESULT_VIEW_MODEL);
    mockMvc
        .perform(
            get("/manage-badges/search-results")
                .sessionAttr("findBadgeBy", "badgeNumber")
                .sessionAttr("searchTerm", SEARCH_TERM_BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/search-results"))
        .andExpect(model().attribute("findBadgeBy", "badgeNumber"))
        .andExpect(model().attribute("searchTerm", SEARCH_TERM_BADGE_NUMBER))
        .andExpect(model().attribute("results", RESULTS_VIEW_MODEL))
        .andExpect(model().attribute("pagingInfo", DEFAULT_PAGING_INFO));
  }
}
