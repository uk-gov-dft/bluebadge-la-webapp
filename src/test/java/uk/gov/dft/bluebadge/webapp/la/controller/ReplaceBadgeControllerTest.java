package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ReplaceBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class ReplaceBadgeControllerTest {

  private static final String BADGE_NUMBER = "AAAAA1";
  private static final String NEW_BADGE_NUMBER = "AAAAA2";
  private static final String URL_REPLACE_BADGE = "/manage-badges/replace-badge/" + BADGE_NUMBER;
  private static final String TEMPLATE_REPLACE_BADGE = "manage-badges/replace-badge";
  private static final String URL_BADGE_REPLACED =
      "/manage-badges/replacement-ordered/" + NEW_BADGE_NUMBER;
  private static final String TEMPLATE_BADGE_REPLACED = "manage-badges/replacement-ordered";

  @Mock private BadgeService badgeServiceMock;
  @Mock private ReferenceDataService referenceDataServiceMock;

  private MockMvc mockMvc;
  private ReplaceBadgeController controller;

  private ReferenceData ref1 = new ReferenceData();
  private ReferenceData ref2 = new ReferenceData();
  private ReferenceData ref3 = new ReferenceData();

  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);
    controller = new ReplaceBadgeController(referenceDataServiceMock, badgeServiceMock);

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayTemplateWithAllOptionsPopulated() throws Exception {
    ReplaceBadgeFormRequest formRequest = ReplaceBadgeFormRequest.builder().build();

    List<ReferenceData> reasonOptions = Lists.newArrayList(ref1, ref2, ref3);

    List<ReferenceData> deliverTo = Lists.newArrayList(ref1, ref2);

    when(referenceDataServiceMock.retrieveBadgeReplaceReasons()).thenReturn(reasonOptions);
    when(referenceDataServiceMock.retrieveBadgeDeliverTos()).thenReturn(deliverTo);

    mockMvc
        .perform(get(URL_REPLACE_BADGE))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_REPLACE_BADGE))
        .andExpect(model().attribute("formRequest", formRequest))
        .andExpect(model().attribute("reasonOptions", reasonOptions))
        .andExpect(model().attribute("deliverToOptions", deliverTo));
  }

  @Test
  public void submit_shouldRedirectToBadgeReplaced_whenNoErrors_deliverHome() throws Exception {

    MultiValueMap<String, String> params = new HttpHeaders();
    params.add("reason", "LOST");
    params.add("deliverTo", "HOME");
    params.add("deliveryOptions", "FAST");

    when(badgeServiceMock.replaceBadge(any())).thenReturn(NEW_BADGE_NUMBER);
    mockMvc
        .perform(post(URL_REPLACE_BADGE).params(params))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(URL_BADGE_REPLACED));

    verify(badgeServiceMock, times(1)).replaceBadge(any());
  }

  @Test
  public void submit_shouldRedirectToBadgeReplaced_whenNoErrors_deliverCouncil() throws Exception {

    MultiValueMap<String, String> params = new HttpHeaders();
    params.add("reason", "LOST");
    params.add("deliverTo", "COUNCIL");

    when(badgeServiceMock.replaceBadge(any())).thenReturn(NEW_BADGE_NUMBER);
    mockMvc
        .perform(post(URL_REPLACE_BADGE).params(params))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(URL_BADGE_REPLACED));

    verify(badgeServiceMock, times(1)).replaceBadge(any());
  }

  @Test
  public void submit_shouldTriggerContextValidation_whenNecessaryInfoNotProvided()
      throws Exception {
    mockMvc
        .perform(post(URL_REPLACE_BADGE))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_REPLACE_BADGE))
        .andExpect(model().errorCount(2))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "reason", "NotBlank"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "deliverTo", "NotBlank"));

    verify(badgeServiceMock, never()).replaceBadge(any());
  }

  @Test
  public void submit_shouldTriggerContextValidation_whenDeliveryOptionNotProvided()
      throws Exception {
    MultiValueMap<String, String> params = new HttpHeaders();
    params.add("reason", "LOST");
    params.add("deliverTo", "HOME");

    mockMvc
        .perform(post(URL_REPLACE_BADGE).params(params))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_REPLACE_BADGE))
        .andExpect(model().errorCount(1))
        .andExpect(
            model().attributeHasFieldErrorCode("formRequest", "deliveryOptions", "NotBlank"));

    verify(badgeServiceMock, never()).replaceBadge(any());
  }

  @Test
  public void show_shouldRenderBadgeCancelledTemplate_withBadgeNumber() throws Exception {
    mockMvc
        .perform(get(URL_BADGE_REPLACED))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_REPLACED))
        .andExpect(model().attribute("badgeNumber", NEW_BADGE_NUMBER));
  }
}
