package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

public class OrderBadgePersonDetailsControllerTest {
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

  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private SecurityUtils securityUtilsMock;

  private OrderBadgePersonDetailsController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new OrderBadgePersonDetailsController(referenceDataServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayOrderABadgePersonDetailsTemplate() throws Exception {
    mockMvc
        .perform(get("/order-a-badge/details"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithValuesCommingFromSession_WhenTheFormWasSavedToSessionBefore()
          throws Exception {
    OrderBadgePersonDetailsFormRequest formRequest =
        OrderBadgePersonDetailsFormRequest.builder()
            .buildingAndStreet(BUILDING_AND_STREET)
            .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .contactDetailsName(CONTACT_DETAILS_NAME)
            .dobDay(Integer.valueOf(DOB_DAY))
            .dobMonth(Integer.valueOf(DOB_MONTH))
            .dobYear(Integer.valueOf(DOB_YEAR))
            .eligibility(ELIGIBILITY)
            .name(NAME)
            .nino(NINO)
            .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
            .postcode(POSTCODE)
            .townOrCity(TOWN_OR_CITY)
            .build();
    mockMvc
        .perform(
            get("/order-a-badge/details")
                .sessionAttr("formRequest-order-a-badge-details", formRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithoutValuesCommingFromSession_WhenTheFormWasSavedToSessionBeforeButRequestParamActionEqualsReset()
          throws Exception {
    OrderBadgePersonDetailsFormRequest beforeResetFormRequest =
        OrderBadgePersonDetailsFormRequest.builder()
            .buildingAndStreet(BUILDING_AND_STREET)
            .contactDetailsContactNumber(CONTACT_DETAILS_CONTACT_NUMBER)
            .contactDetailsName(CONTACT_DETAILS_NAME)
            .dobDay(Integer.valueOf(DOB_DAY))
            .dobMonth(Integer.valueOf(DOB_MONTH))
            .dobYear(Integer.valueOf(DOB_YEAR))
            .eligibility(ELIGIBILITY)
            .name(NAME)
            .nino(NINO)
            .optionalAddressField(OPTIONAL_ADDRESS_FIELD)
            .postcode(POSTCODE)
            .townOrCity(TOWN_OR_CITY)
            .build();

    OrderBadgePersonDetailsFormRequest expectedFormRequest =
        OrderBadgePersonDetailsFormRequest.builder().build();
    mockMvc
        .perform(
            get("/order-a-badge/details?action=reset")
                .sessionAttr("formRequest-order-a-badge-details", beforeResetFormRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attribute("formRequest", expectedFormRequest));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenOnlyMandatoryFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/details")
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIED, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/details")
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIED, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing"));
  }

  @Test
  public void submit_shouldRedirectToDetailsPageAndDisplayErrors_WhenNoFieldsAreSet()
      throws Exception {
    mockMvc
        .perform(post("/order-a-badge/details"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", GENDER_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", DOB_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", POSTCODE_FIED, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_CONTACT_NUMBER_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", ELIGIBILITY_FIELD, "NotBlank"))
        .andExpect(model().errorCount(8));
  }

  @Test
  public void
      submit_shouldRedirectToDetailsPage_WhenAllFieldsAreSetAndMandatoryFieldsAreOkButNonMandatoryFieldsAreWrong()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/details")
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIED, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME_WRONG)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NINO_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().errorCount(3));
  }

  public void submit_shouldRedirectToDetailsPage_WhenAllFieldsAreWrong() throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/details")
                .param(NAME_FIELD, NAME_WRONG)
                .param(DOB_DAY_FIELD, DOB_DAY_WRONG)
                .param(DOB_MONTH_FIELD, DOB_MONTH_WRONG)
                .param(DOB_YEAR_FIELD, DOB_YEAR_WRONG)
                .param(DOB_FIELD, DOB_WRONG)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET_WRONG)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY_WRONG)
                .param(POSTCODE_FIED, POSTCODE_WRONG)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER_WRONG)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY_WRONG)
                .param(NINO_FIELD, NINO_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME_WRONG)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", GENDER_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", DOB_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", POSTCODE_FIED, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_CONTACT_NUMBER_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", ELIGIBILITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NINO_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().errorCount(11));
  }
}
