package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindBadgeFormRequest;

public class FindBadgeControllerTest {
  private static final String NAME_FIELD = "name";
  private static final String DOB_DAY_FIELD = "dobDay";
  private static final String DOB_MONTH_FIELD = "dobMonth";
  private static final String DOB_YEAR_FIELD = "dobYear";
  private static final String DOB_FIELD = "dob";
  private static final String BUILDING_AND_STREET_FIELD = "buildingAndStreet";
  private static final String TOWN_OR_CITY_FIELD = "townOrCity";
  private static final String POSTCODE_FIED = "postcode";
  private static final String CONTACT_DETAILS_CONTACT_NUMBER_FIELD = "contactDetailsContactNumber";
  private static final String ELIGIBILITY_FIELD = "eligibility";
  private static final String NINO_FIELD = "nino";
  private static final String OPTIONAL_ADDRESS_FIELD_FIELD = "optionalAddressField";
  private static final String CONTACT_DETAILS_NAME_FIELD = "contactDetailsName";
  private static final String CONTACT_DETAILS_EMAIL_ADDRESS_FIELD = "contactDetailsEmailAddress";
  private static final String GENDER_FIELD = "gender";

  private static final String NAME = "My Name";
  private static final String DOB_DAY = "15";
  private static final String DOB_MONTH = "3";
  private static final String DOB_YEAR = "1980";
  private static final String DOB = "";
  private static final String NINO = "BN102966C";
  private static final String BUILDING_AND_STREET = "Building and street";
  private static final String OPTIONAL_ADDRESS_FIELD = "Optional address field";
  private static final String TOWN_OR_CITY = "Town or city";
  private static final String POSTCODE = "TF8 6GF";
  private static final String CONTACT_DETAILS_NAME = "Contact details name";
  private static final String CONTACT_DETAILS_CONTACT_NUMBER = "07700900077";
  private static final String CONTACT_DETAILS_EMAIL_ADDRESS = "joe@blogs.com";
  private static final String ELIGIBILITY = "PIP";
  private static final String GENDER = "male";

  private static final String NAME_WRONG = "  My Na me 2";
  private static final String DOB_DAY_WRONG = "32";
  private static final String DOB_MONTH_WRONG = "13";
  private static final String DOB_YEAR_WRONG = "2100";
  private static final String DOB_WRONG = "";
  private static final String NINO_WRONG = "BN10296";
  private static final String BUILDING_AND_STREET_WRONG = "";
  private static final String TOWN_OR_CITY_WRONG = "";
  private static final String POSTCODE_WRONG = "TF8 ";
  private static final String CONTACT_DETAILS_NAME_WRONG = "   mu name 2";
  private static final String CONTACT_DETAILS_CONTACT_NUMBER_WRONG = "07700900";
  private static final String CONTACT_DETAILS_EMAIL_ADDRESS_WRONG = "joeblogscom";
  private static final String ELIGIBILITY_WRONG = "";

  private MockMvc mockMvc;

  private FindBadgeController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new FindBadgeController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayFindBadgeTemplateWithEmptyValues_WhenValuesAreNotInTheSession()
      throws Exception {
    FindBadgeFormRequest formRequest = FindBadgeFormRequest.builder().build();
    mockMvc
        .perform(get("/find-a-badge"))
        .andExpect(status().isOk())
        .andExpect(view().name("find-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void
      show_shouldDisplayFindBadgeTemplateWithFormValuesFromSessionAndShouldNotRemoveTheSession_WhenFormValuesWhereStoredInTheSessionBefore()
          throws Exception {
    FindBadgeFormRequest formRequest =
        FindBadgeFormRequest.builder().findBadgeBy("badgeNumber").searchTerm("aaa").build();
    MockHttpSession mockSession = new MockHttpSession();
    mockSession.setAttribute("formRequest-find-badge", formRequest);
    mockMvc
        .perform(get("/find-a-badge").session(mockSession))
        .andExpect(status().isOk())
        .andExpect(view().name("find-a-badge/index"))
        .andExpect(model().attribute("formRequest", formRequest));
    assertThat(mockSession.getAttribute("formRequest-find-badge")).isEqualTo(formRequest);
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
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "searchTerm", "NotBlank"))
        .andExpect(model().errorCount(2));
  }

  @Test
  public void
      submit_shouldRedirectToSearchResultsWithResultsPopulated_WhenFormIsSubmittedWithValidFormValues()
          throws Exception {
    mockMvc
        .perform(
            post("/find-a-badge").param("findBadgeBy", "badgeNumber").param("searchTerm", "AAAAA1"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/find-a-badge/search-results"))
        .andExpect(flash().attributeExists("results"));
  }
}
