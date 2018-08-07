package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
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

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CancelBadgeControllerTest {

  @Mock
  private BadgeService badgeServiceMock;
  @Mock
  private ReferenceDataService referenceDataServiceMock;

  private MockMvc mockMvc;
  private CancelBadgeController controller;

  private final String BADGE_NUMBER = "AAAAA1";
  private static final String URL_CANCEL_BADGE = "/manage-badges/cancel-badge/AAAAA1";
  private static final String TEMPLATE_CANCEL_BADGE = "/manage-badges/cancel-badge";

  private static final String URL_BADGE_CANCELLED = "/manage-badges/badge-cancelled/AAAAA1";
  private static final String TEMPLATE_BADGE_CANCELLED = "/manage-badges/badge-cancelled";

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
  public void submit_shouldTriggerContextValidation_whenNoReasonIsSelected() throws Exception {
    CancelBadgeFormRequest formRequest = CancelBadgeFormRequest.builder().build();

    formRequest.setReason(RefDataCancellationEnum.REVOKE.toString());

    mockMvc
      .perform(post(URL_CANCEL_BADGE).param("reason", RefDataCancellationEnum.REVOKE.getValue()))
      .andExpect(status().isFound())
      .andExpect(redirectedUrl(URL_BADGE_CANCELLED));

    verify(badgeServiceMock, times(1)).cancelBadge(BADGE_NUMBER, RefDataCancellationEnum.REVOKE);
  }
}
