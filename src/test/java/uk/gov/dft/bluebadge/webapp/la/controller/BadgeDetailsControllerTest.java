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
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Party;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.Person;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToBadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.enums.BadgePartyTypeEnum;

public class BadgeDetailsControllerTest extends BaseControllerTest {
  private static final String BADGE_NUMBER = "KKKKJ9";

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
  public void show_shouldDisplayBadgeDetailsForPerson_WhenBadgeExists() throws Exception {
    Party party = new Party();
    party.setTypeCode(BadgePartyTypeEnum.PERSON.getCode());
    badge.setParty(party);

    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(badge));
    when(badgeToBadgeDetailsViewModelMock.convert(badge)).thenReturn(badgeViewModel);
    mockMvc
        .perform(get(URL_BADGE_DETAILS + BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_DETAILS))
        .andExpect(model().attribute("badge", badgeViewModel));
  }

  @Test
  public void show_shouldDisplayBadgeDetailsForOrganisation_WhenBadgeExists() throws Exception {
    Party party = new Party();
    party.setTypeCode(BadgePartyTypeEnum.ORGANISATION.getCode());
    badge.setParty(party);

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
