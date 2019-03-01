package uk.gov.dft.bluebadge.webapp.la.controller.orderbadge;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static uk.gov.dft.bluebadge.webapp.la.controller.utils.ReferenceDataUtils.buildReferenceData;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.service.enums.EligibilityType;
import uk.gov.dft.bluebadge.common.service.enums.Nation;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.config.GeneralConfig;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;

public class OrderBadgePersonDetailsControllerTest extends OrderBadgeControllerTestData {

  private MockMvc mockMvc;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    GeneralConfig generalConfig = new GeneralConfig();
    generalConfig.setThumbnailHeight(300);
    OrderBadgePersonDetailsController controller =
        new OrderBadgePersonDetailsController(referenceDataServiceMock, generalConfig);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void
      show_shouldDisplayOrderABadgePersonDetailsTemplate_AndPopulateEligibiltiesAndGenderFieldsFromReferenceDataService()
          throws Exception {

    List<ReferenceData> referenceDataEligibilityList = new ArrayList<>();
    List<ReferenceData> eligibilityAutoList = new ArrayList<>();
    List<ReferenceData> eligibilityFurtherEngNirList = new ArrayList<>();
    List<ReferenceData> eligibilityFurtherScoList = new ArrayList<>();
    List<ReferenceData> eligibilityFurtherWlsList = new ArrayList<>();
    List<ReferenceData> eligibilityFurtherAllList = new ArrayList<>();

    EnumSet<EligibilityType> auto =
        EnumSet.of(
            EligibilityType.PIP,
            EligibilityType.DLA,
            EligibilityType.AFRFCS,
            EligibilityType.WPMS,
            EligibilityType.BLIND);
    EnumSet<EligibilityType> discretionary = EnumSet.complementOf(auto);

    auto.forEach(
        t ->
            eligibilityAutoList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_AUTO")
                    .build()));
    discretionary.forEach(
        t -> {
          eligibilityFurtherAllList.add(
              ReferenceData.builder()
                  .shortCode(t.name())
                  .subgroupShortCode("ELIG_FURTHER")
                  .build());
          if (t == EligibilityType.COGNITIVE) {
            eligibilityFurtherWlsList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_FURTHER")
                    .build());
          } else if (t == EligibilityType.TRAF_RISK) {
            eligibilityFurtherScoList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_FURTHER")
                    .build());
          } else {
            eligibilityFurtherWlsList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_FURTHER")
                    .build());
            eligibilityFurtherScoList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_FURTHER")
                    .build());
            eligibilityFurtherEngNirList.add(
                ReferenceData.builder()
                    .shortCode(t.name())
                    .subgroupShortCode("ELIG_FURTHER")
                    .build());
          }
        });

    referenceDataEligibilityList.addAll(eligibilityAutoList);
    referenceDataEligibilityList.addAll(eligibilityFurtherAllList);

    ReferenceData rdGender1 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 3);
    ReferenceData rdGender2 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4);
    List<ReferenceData> referenceDataGenderList = Lists.newArrayList(rdGender1, rdGender2);

    when(referenceDataServiceMock.retrieveBadgeEligilities())
        .thenReturn(referenceDataEligibilityList);
    when(referenceDataServiceMock.retrieveBadgeGenders()).thenReturn(referenceDataGenderList);
    LocalAuthorityRefData la = new LocalAuthorityRefData();
    la.setLocalAuthorityMetaData(
        LocalAuthorityRefData.LocalAuthorityMetaData.builder().nation(Nation.ENG).build());
    when(referenceDataServiceMock.getUserLocalAuthority()).thenReturn(la);

    // Expected Result Shape
    TreeMap<String, List<ReferenceData>> expectedEligibility = new TreeMap<>();
    expectedEligibility.put("Automatic", eligibilityAutoList);
    expectedEligibility.put("Further", eligibilityFurtherEngNirList);

    // For an English la will not get COGNIT or TRAF_RISK
    mockMvc
        .perform(
            get("/order-a-badge/person/details")
                .param("fid", FLOW_ID)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attribute("eligibilityOptions", expectedEligibility))
        .andExpect(model().attribute("genderOptions", referenceDataGenderList));

    expectedEligibility = new TreeMap<>();
    expectedEligibility.put("Automatic", eligibilityAutoList);
    expectedEligibility.put("Further", eligibilityFurtherWlsList);
    la.getLocalAuthorityMetaData().get().setNation(Nation.WLS);

    // For an Wales la will get COGNIT
    mockMvc
        .perform(
            get("/order-a-badge/person/details")
                .param("fid", FLOW_ID)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attribute("eligibilityOptions", expectedEligibility))
        .andExpect(model().attribute("genderOptions", referenceDataGenderList));

    expectedEligibility = new TreeMap<>();
    expectedEligibility.put("Automatic", eligibilityAutoList);
    expectedEligibility.put("Further", eligibilityFurtherScoList);
    la.getLocalAuthorityMetaData().get().setNation(Nation.SCO);

    // For Scotland la will get TRAF_RISK
    mockMvc
        .perform(
            get("/order-a-badge/person/details")
                .param("fid", FLOW_ID)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attribute("eligibilityOptions", expectedEligibility))
        .andExpect(model().attribute("genderOptions", referenceDataGenderList));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeDetailsTemplateWithValuesCommingFromSession_WhenTheFormWasSavedToSessionBefore()
          throws Exception {
    mockMvc
        .perform(
            get("/order-a-badge/person/details")
                .param("fid", FLOW_ID)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .sessionAttr(SESSION_FORM_REQUEST_DETAILS, FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attribute("formRequest", FORM_REQUEST_PERSON_DETAILS_WITH_IMAGE));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenOnlyMandatoryFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            multipart("/order-a-badge/person/details")
                .file(EMPTY_PHOTO)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/order-a-badge/processing?fid=" + FLOW_ID));
  }

  @Test
  public void submit_shouldThrowContextValidation_WhenImageFileHasWrongContent() throws Exception {
    mockMvc
        .perform(
            multipart("/order-a-badge/person/details")
                .file(PHOTO_CONTENT_WRONG)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS)
                .param("thumbBase64", "thumbnail")
                .param("byteImage", "thumbnail"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", PHOTO_FIELD, "NotValid.badge"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {

    mockMvc
        .perform(
            multipart("/order-a-badge/person/details")
                .file(PHOTO())
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO)
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
            multipart("/order-a-badge/person/details")
                .file(EMPTY_PHOTO)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", GENDER_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", DOB_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", POSTCODE_FIELD, "NotBlank"))
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
            MockMvcRequestBuilders.multipart("/order-a-badge/person/details")
                .file(PHOTO_WRONG)
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_WRONG)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME_WRONG)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NINO_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", PHOTO_FIELD, "NotValid.badge"))
        .andExpect(model().errorCount(5));
  }

  @Test
  public void submit_shouldRedirectToDetailsPage_WhenAllFieldsAreWrong() throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/person/details")
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME_WRONG)
                .param(DOB_DAY_FIELD, DOB_DAY_WRONG)
                .param(DOB_MONTH_FIELD, DOB_MONTH_WRONG)
                .param(DOB_YEAR_FIELD, DOB_YEAR_WRONG)
                .param(DOB_FIELD, DOB_WRONG)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET_WRONG)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY_WRONG)
                .param(POSTCODE_FIELD, POSTCODE_WRONG)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER_WRONG)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_WRONG)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY_WRONG)
                .param(NINO_FIELD, NINO_WRONG)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME_WRONG)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS_WRONG))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NAME_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", GENDER_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", DOB_FIELD, "CannotBeInTheFutureDate"))
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
        .andExpect(model().attributeHasFieldErrorCode("formRequest", ELIGIBILITY_FIELD, "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", NINO_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", CONTACT_DETAILS_NAME_FIELD, "Pattern"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, "Pattern"))
        .andExpect(model().errorCount(12));
  }

  @Test
  public void
      submit_shouldRedirectToDetailsPageWithErrors_WhenAllFieldsArePassedAndSizeLimitedFieldsAreWrong()
          throws Exception {

    mockMvc
        .perform(
            multipart("/order-a-badge/person/details")
                .file(PHOTO())
                .sessionAttr(SESSION_FORM_REQUEST_INDEX, FORM_REQUEST_INDEX_PERSON)
                .param("flowId", FLOW_ID)
                .param(NAME_FIELD, NAME)
                .param(GENDER_FIELD, GENDER)
                .param(DOB_DAY_FIELD, DOB_DAY)
                .param(DOB_MONTH_FIELD, DOB_MONTH)
                .param(DOB_YEAR_FIELD, DOB_YEAR)
                .param(DOB_FIELD, DOB)
                .param(BUILDING_AND_STREET_FIELD, BUILDING_AND_STREET_OVERSIZE)
                .param(TOWN_OR_CITY_FIELD, TOWN_OR_CITY_OVERSIZE)
                .param(POSTCODE_FIELD, POSTCODE)
                .param(CONTACT_DETAILS_CONTACT_NUMBER_FIELD, CONTACT_DETAILS_CONTACT_NUMBER)
                .param(
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER_FIELD,
                    CONTACT_DETAILS_SECONDARY_CONTACT_NUMBER)
                .param(ELIGIBILITY_FIELD, ELIGIBILITY)
                .param(NINO_FIELD, NINO)
                .param(OPTIONAL_ADDRESS_FIELD_FIELD, OPTIONAL_ADDRESS_FIELD_OVERSIZE)
                .param(CONTACT_DETAILS_NAME_FIELD, CONTACT_DETAILS_NAME)
                .param(CONTACT_DETAILS_EMAIL_ADDRESS_FIELD, CONTACT_DETAILS_EMAIL_ADDRESS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/person/details"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", BUILDING_AND_STREET_FIELD, "Size"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", OPTIONAL_ADDRESS_FIELD_FIELD, "Size"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", TOWN_OR_CITY_FIELD, "Size"))
        .andExpect(model().errorCount(3));
  }
}
