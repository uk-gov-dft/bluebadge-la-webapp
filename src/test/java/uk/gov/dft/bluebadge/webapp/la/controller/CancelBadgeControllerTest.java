package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CancelBadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataCancellationEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class CancelBadgeControllerTest {

  @Mock private BadgeService badgeServiceMock;
  @Mock private ReferenceDataService referenceDataServiceMock;

  private MockMvc mockMvc;
  private CancelBadgeController controller;

  private final String BADGE_NUMBER = "AAAAA1";
  private static final String URL_CANCEL_BADGE = "/manage-badges/cancel-badge/AAAAA1";
  private static final String TEMPLATE_CANCEL_BADGE = "manage-badges/cancel-badge";

  private static final String URL_BADGE_CANCELLED = "/manage-badges/badge-cancelled/AAAAA1";
  private static final String TEMPLATE_BADGE_CANCELLED = "manage-badges/badge-cancelled";

  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);
    controller = new CancelBadgeController(referenceDataServiceMock, badgeServiceMock);

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void show_shouldDisplayTemplateWithCancellationOptionsPopulated() throws Exception {
    CancelBadgeFormRequest formRequest = CancelBadgeFormRequest.builder().build();

    ReferenceData refData1 = new ReferenceData();
    ReferenceData refData2 = new ReferenceData();
    ReferenceData refData3 = new ReferenceData();
    List<ReferenceData> reasonOptions = Lists.newArrayList(refData1, refData2, refData3);

    when(referenceDataServiceMock.retrieveCancellations()).thenReturn(reasonOptions);

    mockMvc
        .perform(get(URL_CANCEL_BADGE))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_CANCEL_BADGE))
        .andExpect(model().attribute("formRequest", formRequest))
        .andExpect(model().attribute("reasonOptions", reasonOptions));
  }

  @Test
  public void submit_shouldRedirectToBadgeCancelled_whenCancellationReasonIsGiven()
      throws Exception {
    mockMvc
        .perform(post(URL_CANCEL_BADGE).param("reason", RefDataCancellationEnum.REVOKE.getValue()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(URL_BADGE_CANCELLED));

    verify(badgeServiceMock, times(1)).cancelBadge(BADGE_NUMBER, RefDataCancellationEnum.REVOKE);
  }

  @Test
  public void submit_shouldTriggerContextValidation_whenNoReasonIsSelected() throws Exception {
    mockMvc
        .perform(post(URL_CANCEL_BADGE))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_CANCEL_BADGE))
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "reason", "NotBlank"));

    verify(badgeServiceMock, times(0)).cancelBadge(any(), any());
  }

  @Test
  public void show_shouldRenderBadgeCancelledTemplate_withBadgeNumber() throws Exception {
    mockMvc
        .perform(get(URL_BADGE_CANCELLED))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_CANCELLED))
        .andExpect(model().attribute("badgeNumber", BADGE_NUMBER));
  }
}
