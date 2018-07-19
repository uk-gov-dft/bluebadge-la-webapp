package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class OrderBadgePersonDetailsControllerTest extends OrderBadgeBaseControllerTest {

  private MockMvc mockMvc;

  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private SecurityUtils securityUtilsMock;

  private OrderBadgePersonDetailsController controller;

  private ReferenceData referenceData1;
  private ReferenceData referenceData2;
  private ReferenceData referenceData3;
  private ReferenceData referenceData4;
  private ReferenceData referenceData5;
  private ReferenceData referenceData6;
  private ReferenceData referenceData7;
  private ReferenceData referenceData8;
  private List<ReferenceData> referenceDataEligibilityList;
  private List<ReferenceData> referenceDataGenderList;

  private ReferenceData buildReferenceData(String groupShortCode, int i) {
    return new ReferenceData()
        .description("description" + 1)
        .displayOrder(i)
        .groupDescription("groupDescription" + i)
        .groupShortCode(groupShortCode)
        .shortCode("shortCode" + i)
        .subgroupDescription("subGroupDescription" + i)
        .subgroupShortCode("subGroupShortCode" + i);
  }

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
  public void
      show_shouldDisplayOrderABadgePersonDetailsTemplate_AndPopulateEligibiltiesAndGenderFieldsFromReferenceDataService()
          throws Exception {

    // Mock Data
    referenceData1 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1)
            .subgroupShortCode("ELIG_AUTO");
    referenceData2 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 2)
            .subgroupShortCode("ELIG_AUTO");
    referenceData3 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 3)
            .subgroupShortCode("ELIG_AUTO");
    referenceData4 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 4)
            .subgroupShortCode("ELIG_FURTH");
    referenceData5 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 5)
            .subgroupShortCode("ELIG_FURTH");
    referenceData6 =
        buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 6)
            .subgroupShortCode("ELIG_FURTH");

    referenceDataEligibilityList =
        Lists.newArrayList(
            referenceData1,
            referenceData2,
            referenceData3,
            referenceData4,
            referenceData5,
            referenceData6);

    referenceData7 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 3);
    referenceData8 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4);
    referenceDataGenderList = Lists.newArrayList(referenceData3, referenceData4);

    when(referenceDataServiceMock.retrieveEligilities()).thenReturn(referenceDataEligibilityList);
    when(referenceDataServiceMock.retrieveGender()).thenReturn(referenceDataGenderList);

    // Expected Result Shape
    TreeMap<String, List<ReferenceData>> eligibilityMap =
        new TreeMap<String, List<ReferenceData>>();
    List<ReferenceData> automaticList =
        Lists.newArrayList(referenceData1, referenceData2, referenceData3);
    List<ReferenceData> furtherList =
        Lists.newArrayList(referenceData4, referenceData5, referenceData6);
    eligibilityMap.put("Automatic", automaticList);
    eligibilityMap.put("Further", furtherList);

    mockMvc
        .perform(get("/order-a-badge/details"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attribute("eligibilityOptions", eligibilityMap))
        .andExpect(model().attribute("genderOptions", referenceDataGenderList));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithValuesCommingFromSession_WhenTheFormWasSavedToSessionBefore()
          throws Exception {
    mockMvc
        .perform(
            get("/order-a-badge/details")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_DETAILS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/details"))
        .andExpect(model().attribute("formRequest", FORM_REQUEST_DETAILS));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithoutValuesCommingFromSession_WhenTheFormWasSavedToSessionBeforeButRequestParamActionEqualsReset()
          throws Exception {
    OrderBadgePersonDetailsFormRequest expectedFormRequest =
        OrderBadgePersonDetailsFormRequest.builder().build();
    mockMvc
        .perform(
            get("/order-a-badge/details?action=reset")
                .sessionAttr("formRequest-order-a-badge-details", FORM_REQUEST_DETAILS))
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
