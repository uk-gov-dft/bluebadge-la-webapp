package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class OrderBadgeOrganisationDetailsControllerTest extends OrderBadgeControllerTestData {

  private MockMvc mockMvc;

  private OrderBadgeOrganisationDetailsController controller;

  @Before
  public void setup() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new OrderBadgeOrganisationDetailsController();

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayOrderABadgeOrganisationDetailsTemplate() throws Exception {
    mockMvc
        .perform(
            get("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("fid", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/organisation/details"));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithValuesComingFromSession_WhenTheFormWasSavedToSessionBefore()
          throws Exception {
    mockMvc
        .perform(
            get("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .sessionAttr(SESSION_FORM_REQUEST_DETAILS, FORM_REQUEST_ORGANISATION_DETAILS)
                .param("fid", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/organisation/details"))
        .andExpect(model().attribute("formRequest", FORM_REQUEST_ORGANISATION_DETAILS));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenOnlyMandatoryFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing?fid=" + FLOW_ID));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing?fid=" + FLOW_ID));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAndPhoneNumberHas10CharactersAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(
                    CONTACT_DETAILS_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_CONTACT_NUMBER_10_CHARACTERS)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_10_CHARACTERS)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing?fid=" + FLOW_ID));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAndPhoneNumberHas44PrefixAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(
                    CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER_44_PREFIX)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_44_PREFIX)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing?fid=" + FLOW_ID));
  }

  @Test
  public void submit_shouldRedirectToDetailsPageAndDisplayErrors_WhenNoFieldsAreSet()
      throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/organisation/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", POSTCODE_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_CONTACT_NUMBER_FIELD, "NotBlank"))
        .andExpect(model().errorCount(6));
  }

  @Test
  public void
      submit_shouldRedirectToDetailsPage_WhenAllFieldsAreSetAndMandatoryFieldsAreOkButNonMandatoryFieldsAreWrong()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/organisation/details"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().errorCount(2));
  }

  @Test
  public void submit_shouldRedirectToDetailsPage_WhenAllFieldsAreWrong() throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/organisation/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME_WRONG)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET_WRONG)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY_WRONG)
                .param(POSTCODE_FIELD, POSTCODE_WRONG)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER_WRONG)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME_WRONG)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/organisation/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", POSTCODE_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_CONTACT_NUMBER_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().errorCount(8));
  }
}
