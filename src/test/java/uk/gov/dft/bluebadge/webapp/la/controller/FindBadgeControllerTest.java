package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.FindBadgeSearchResultViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

import javax.servlet.http.HttpSession;

public class FindBadgeControllerTest {

  private static final String NAME = "jason";
  private static final String INVALID_SEARCH_BADGE_BY_OPTION = "badOptionValue";
  private static final String INVALID_BADGE_NUMBER = "aaa/aaa";
  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String FIND_BY_POSTCODE = "postcode";
  private static final String POSTCODE = "L129PZ";

  private static final Badge BADGE =
      new Badge().badgeNumber(BADGE_NUMBER).localAuthorityRef("LocalAuthorityRef");
  private static final FindBadgeSearchResultViewModel VIEW_MODEL =
      FindBadgeSearchResultViewModel.builder().badgeNumber(BADGE_NUMBER).build();
  private static final String LA_SHORT_CODE = "ABERD";

  private static final PagingInfo PAGING_INFO = new PagingInfo();

  static {
    PAGING_INFO.setPageNum(1);
    PAGING_INFO.setPageSize(50);
  }

  private MockMvc mockMvc;

  @Mock BadgeService badgeServiceMock;
  @Mock SecurityUtils securityUtilsMock;

  private FindBadgeController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    controller = new FindBadgeController(badgeServiceMock, securityUtilsMock);

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
      submit_shouldRedirectToFindBadgeTemplateWithValidationErrors_WhenFormIsSubmittedWithInvalidBadgeNumber()
          throws Exception {
    mockMvc
        .perform(
            post("/manage-badges")
                .param("findBadgeBy", "badgeNumber")
                .param("searchTerm", INVALID_BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-badges/index"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "searchTerm", "Pattern"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void
  submit_shouldRedirectToFindBadgeSearchResultsTemplate()
    throws Exception {
    HttpSession session =
      mockMvc
      .perform(
        post("/manage-badges")
          .param("findBadgeBy", "badgeNumber")
          .param("searchTerm", BADGE_NUMBER))
      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/manage-badges/search-results")).andReturn()
        .getRequest()
        .getSession();

    assertThat(session.getAttribute("findBadgeBy"))
      .isEqualTo("badgeNumber");
    assertThat(session.getAttribute("searchTerm")).isEqualTo(BADGE_NUMBER);
  }

  @Test
  public void exportAllLaBadges_shouldReturnFile() throws Exception {
    when(securityUtilsMock.getCurrentLocalAuthorityShortCode()).thenReturn(LA_SHORT_CODE);
    ResponseEntity<byte[]> expectedResponse =
        new ResponseEntity("response".getBytes(), HttpStatus.OK);
    when(badgeServiceMock.exportBadgesByLa(LA_SHORT_CODE)).thenReturn(expectedResponse);

    mockMvc
        .perform(get("/manage-badges/export-all-la-badges"))
        .andExpect(status().isOk())
        .andExpect(content().bytes(expectedResponse.getBody()))
        .andReturn();
  }
}
