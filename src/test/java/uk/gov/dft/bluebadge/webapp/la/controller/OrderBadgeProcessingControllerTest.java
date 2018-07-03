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
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.service.ReferenceDataService;

public class OrderBadgeProcessingControllerTest {
  private static final String APPLICATION_DATE_DAY_FIELD = "applicationDateDay";
  private static final String APPLICATION_DATE_MONTH_FIELD = "applicationDateMonth";
  private static final String APPLICATION_DATE_YEAR_FIELD = "applicationDateYear";
  private static final String APPLICATION_DATE_FIELD = "applicationDate";
  private static final String APPLICATION_CHANNEL_FIELD = "applicationChannel";
  private static final String LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD =
      "localAuthorityReferenceNumber";
  private static final String BADGE_START_DATE_DAY_FIELD = "badgeStartDateDay";
  private static final String BADGE_START_DATE_MONTH_FIELD = "badgeStartDateMonth";
  private static final String BADGE_START_DATE_YEAR_FIELD = "badgeStartDateYear";
  private static final String BADGE_START_DATE_FIELD = "badgeStartDate";
  private static final String BADGE_EXPIRY_DATE_DAY_FIELD = "badgeExpiryDateDay";
  private static final String BADGE_EXPIRY_DATE_MONTH_FIELD = "badgeExpiryDateMonth";
  private static final String BADGE_EXPIRY_DATE_YEAR_FIELD = "badgeExpiryDateYear";
  private static final String BADGE_EXPIRY_DATE_VALID_FIELD = "badgeExpiryDateValid";

  private static final String DELIVER_TO_FIELD = "deliverTo";
  private static final String DELIVERY_OPTIONS_FIELD = "deliveryOptions";

  private static final String APPLICATION_DATE_DAY = "2";
  private static final String APPLICATION_DATE_MONTH = "7";
  private static final String APPLICATION_DATE_YEAR = "2018";
  private static final String APPLICATION_CHANNEL = "paper";
  private static final String LOCAL_AUTHORITY_REFERENCE_NUMBER = "AA110";
  private static final String BADGE_START_DATE_DAY = "7";
  private static final String BADGE_START_DATE_MONTH = "8";
  private static final String BADGE_START_DATE_YEAR = "2018";
  private static final String BADGE_EXPIRY_DATE_DAY = "7";
  private static final String BADGE_EXPIRY_DATE_MONTH = "8";
  private static final String BADGE_EXPIRY_DATE_YEAR = "2021";
  private static final String DELIVER_TO = "badgeHolder";
  private static final String DELIVERY_OPTIONS = "fast";

  private static final String APPLICATION_DATE_DAY_WRONG = "32";
  private static final String APPLICATION_DATE_MONTH_WRONG = "13";
  private static final String APPLICATION_DATE_YEAR_WRONG = "201";
  private static final String APPLICATION_CHANNEL_WRONG = "paper";
  private static final String BADGE_START_DATE_DAY_WRONG = "7";
  private static final String BADGE_START_DATE_MONTH_WRONG = "6";
  private static final String BADGE_START_DATE_YEAR_WRONG = "2018";
  private static final String BADGE_EXPIRY_DATE_DAY_WRONG = "5";
  private static final String BADGE_EXPIRY_DATE_MONTH_WRONG = "6";
  private static final String BADGE_EXPIRY_DATE_YEAR_WRONG = "2018";
  private static final String DELIVER_TO_WRONG = "deliverTo";
  private static final String DELIVERY_OPTIONS_WRONG = "deliveryOptions";

  private MockMvc mockMvc;

  @Mock private ReferenceDataService referenceDataServiceMock;
  @Mock private SecurityUtils securityUtilsMock;

  private OrderBadgeProcessingController controller;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new OrderBadgeProcessingController(referenceDataServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayOrderABadgeProcessingTemplate() throws Exception {
    mockMvc
        .perform(get("/order-a-badge/processing"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"));
  }

  @Test
  public void
      show_shouldDisplayOrderABadgeProcessingTemplateWithValuesCommingFromSession_WhenTheFormWasSavedToSessionBefore()
          throws Exception {
    OrderBadgeProcessingFormRequest formRequest =
        OrderBadgeProcessingFormRequest.builder()
            .applicationChannel(APPLICATION_CHANNEL)
            .applicationDateDay(Integer.valueOf(APPLICATION_DATE_DAY))
            .applicationDateMonth(Integer.valueOf(APPLICATION_DATE_MONTH))
            .applicationDateYear(Integer.valueOf(APPLICATION_DATE_YEAR))
            .localAuthorityReferenceNumber(LOCAL_AUTHORITY_REFERENCE_NUMBER)
            .badgeStartDateDay(Integer.valueOf(BADGE_START_DATE_DAY))
            .badgeStartDateMonth(Integer.valueOf(BADGE_START_DATE_MONTH))
            .badgeStartDateYear(Integer.valueOf(BADGE_START_DATE_YEAR))
            .badgeExpiryDateDay(Integer.valueOf(BADGE_EXPIRY_DATE_DAY))
            .badgeExpiryDateMonth(Integer.valueOf(BADGE_EXPIRY_DATE_MONTH))
            .badgeExpiryDateYear(Integer.valueOf(BADGE_EXPIRY_DATE_YEAR))
            .deliverTo(DELIVER_TO)
            .deliveryOptions(DELIVERY_OPTIONS)
            .build();
    mockMvc
        .perform(
            get("/order-a-badge/processing")
                .sessionAttr("formRequest-order-a-badge-processing", formRequest))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void
      submit_shouldRedirectToHomePage_WhenOnlyMandatoryFieldsAreSetAndThereAreNoValidationErrors()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  public void submit_shouldRedirectToHomePage_WhenAllFieldsAreSetAndThereAreNoValidationErrors()
      throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  public void submit_shouldRedirectToProcessingPageAndDisplayErrors_WhenNoFieldsAreSet()
      throws Exception {
    mockMvc
        .perform(post("/order-a-badge/processing"))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", APPLICATION_DATE_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", APPLICATION_CHANNEL_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", BADGE_START_DATE_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_EXPIRY_DATE_VALID_FIELD, "AssertTrue"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", DELIVER_TO_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", DELIVERY_OPTIONS_FIELD, "NotBlank"))
        .andExpect(model().errorCount(6));
  }

  @Test
  public void submit_shouldRedirectToProcessingPage_WhenAllFieldsAreWrongOrMissing()
      throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY_WRONG)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH_WRONG)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR_WRONG)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY_WRONG)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH_WRONG)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR_WRONG)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY_WRONG)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH_WRONG)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR_WRONG))
        .andExpect(status().isOk())
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", APPLICATION_DATE_FIELD, "CannotBeInTheFutureDate"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode("formRequest", APPLICATION_CHANNEL_FIELD, "NotBlank"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_START_DATE_FIELD, "CannotBeInThePastDate"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_EXPIRY_DATE_VALID_FIELD, "AssertTrue"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", DELIVER_TO_FIELD, "NotBlank"))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", DELIVERY_OPTIONS_FIELD, "NotBlank"))
        .andExpect(model().errorCount(6));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptApplicationDateWrongFormat()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, "32")
                .param(APPLICATION_DATE_MONTH_FIELD, "13")
                .param(APPLICATION_DATE_YEAR_FIELD, "2017")
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", APPLICATION_DATE_FIELD, "CannotBeInTheFutureDate"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptApplicationDateIsInTheFuture()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, "1")
                .param(APPLICATION_DATE_MONTH_FIELD, "12")
                .param(APPLICATION_DATE_YEAR_FIELD, "2100")
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", APPLICATION_DATE_FIELD, "CannotBeInTheFutureDate"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptStartDateIsWrongFormat()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, "31")
                .param(BADGE_START_DATE_MONTH_FIELD, "13")
                .param(BADGE_START_DATE_YEAR_FIELD, "2018")
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_START_DATE_FIELD, "CannotBeInThePastDate"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptStartDateIsInThePast()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, "1")
                .param(BADGE_START_DATE_MONTH_FIELD, "1")
                .param(BADGE_START_DATE_YEAR_FIELD, "1900")
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, BADGE_EXPIRY_DATE_DAY)
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, BADGE_EXPIRY_DATE_MONTH)
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, BADGE_EXPIRY_DATE_YEAR)
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_START_DATE_FIELD, "CannotBeInThePastDate"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptExpiryDateIsWrongFormat()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, BADGE_START_DATE_DAY)
                .param(BADGE_START_DATE_MONTH_FIELD, BADGE_START_DATE_MONTH)
                .param(BADGE_START_DATE_YEAR_FIELD, BADGE_START_DATE_YEAR)
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, "32")
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, "13")
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, "2019")
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_EXPIRY_DATE_VALID_FIELD, "AssertTrue"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptExpiryDateIsBeforeStartDate()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, "1")
                .param(BADGE_START_DATE_MONTH_FIELD, "1")
                .param(BADGE_START_DATE_YEAR_FIELD, "2019")
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, "31")
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, "12")
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, "2018")
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_EXPIRY_DATE_VALID_FIELD, "AssertTrue"));
  }

  @Test
  public void
      submit_shouldRedirectToProcessingPage_WhenAllFieldsAreRightExceptExpiryDateIsMoreThan3YearAfterStartDate()
          throws Exception {
    mockMvc
        .perform(
            post("/order-a-badge/processing")
                .param(APPLICATION_DATE_DAY_FIELD, APPLICATION_DATE_DAY)
                .param(APPLICATION_DATE_MONTH_FIELD, APPLICATION_DATE_MONTH)
                .param(APPLICATION_DATE_YEAR_FIELD, APPLICATION_DATE_YEAR)
                .param(APPLICATION_CHANNEL_FIELD, APPLICATION_CHANNEL)
                .param(LOCAL_AUTHORITY_REFERENCE_NUMBER_FIELD, LOCAL_AUTHORITY_REFERENCE_NUMBER)
                .param(BADGE_START_DATE_DAY_FIELD, "1")
                .param(BADGE_START_DATE_MONTH_FIELD, "1")
                .param(BADGE_START_DATE_YEAR_FIELD, "2019")
                .param(BADGE_EXPIRY_DATE_DAY_FIELD, "2")
                .param(BADGE_EXPIRY_DATE_MONTH_FIELD, "1")
                .param(BADGE_EXPIRY_DATE_YEAR_FIELD, "2022")
                .param(DELIVER_TO_FIELD, DELIVER_TO)
                .param(DELIVERY_OPTIONS_FIELD, DELIVERY_OPTIONS))
        .andExpect(status().isOk())
        .andExpect(view().name("order-a-badge/processing"))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "formRequest", BADGE_EXPIRY_DATE_VALID_FIELD, "AssertTrue"));
  }
}
