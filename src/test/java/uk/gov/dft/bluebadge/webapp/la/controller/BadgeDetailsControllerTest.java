package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Badge;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToBadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

public class BadgeDetailsControllerTest extends BaseControllerTest {
  private static final String BADGE_NUMBER = "KKKKJ9";

  private static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  private static final String EMAIL_ADDRESS_UPDATED = "updated@joe.com";
  private static final String EMAIL_ADDRESS_ERROR = "updated joe.com";

  private static final String NAME = "joeblogs";
  private static final String NAME_UPDATED = "updated";
  private static final String NAME_ERROR = "updated11";

  private static final int ROLE_ID = 1;
  private static final int LOCAL_AUTHORITY_ID = 1;
  private static final int USER_ID = 1;
  private static final String NAME_PARAM = "name";
  private static final String EMAIL_ADDRESS_PARAM = "emailAddress";
  private static final String LOCAL_AUTHORITY_ID_PARAM = "localAuthorityId";
  private static final String MODEL_ID = "id";

  private static final String ERROR_MSG_EMAIL_ADDRESS = "error in emailAddress";
  private static final String ERROR_MSG_NAME = "error in name";

  @Mock private BadgeService badgeServiceMock;
  @Mock private BadgeToBadgeDetailsViewModel badgeToBadgeDetailsViewModelMock;
  @Mock private Model modelMock;

  private BadgeDetailsController controller;

  // Test Data
  private Badge badge;
  private BadgeDetailsViewModel badgeViewModel;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new BadgeDetailsController(badgeServiceMock, badgeToBadgeDetailsViewModelMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    badge = new Badge();
    badgeViewModel = BadgeDetailsViewModel.builder().build();
  }

  @Test
  public void show_shouldDisplayBadgeDetails_WhenBadgeExists() throws Exception {
    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(badge));
    when(badgeToBadgeDetailsViewModelMock.convert(badge)).thenReturn(badgeViewModel);
    mockMvc
        .perform(get(URL_BADGE_DETAILS + BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_DETAILS))
        .andExpect(model().attribute("badge", badgeViewModel));
  }

  @Test(expected = NotFoundException.class)
  public void show_shouldThrowNotFoundException_WhenBadgeDoesNotExist() throws Exception {
    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.empty());

    controller.show(BADGE_NUMBER, modelMock);

    verify(badgeToBadgeDetailsViewModelMock, times(0)).convert(any());
  }
}
