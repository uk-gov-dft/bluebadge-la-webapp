package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.BadgeToBadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.BadgeDetailsViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;
import uk.gov.dft.bluebadge.webapp.la.service.enums.BadgePartyTypeEnum;
import uk.gov.dft.bluebadge.webapp.la.service.enums.Status;

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
    badge.setStatusCode(Status.ISSUED.name());
    badge.setExpiryDate(LocalDate.now().plusYears(1));
    badgeViewModel = BadgeDetailsViewModel.builder().build();
  }

  @Test
  public void show_shouldDisplayBadgeDetailsForPerson_WhenBadgeExists() throws Exception {
    Party party = new Party();
    party.setTypeCode(BadgePartyTypeEnum.PERSON.getCode());
    badge.setParty(party);
    badge.setStartDate(LocalDate.now().minusDays(10));
    badge.setExpiryDate(LocalDate.now().plusDays(1));

    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(badge));
    when(badgeToBadgeDetailsViewModelMock.convert(badge)).thenReturn(badgeViewModel);
    mockMvc
        .perform(get(URL_BADGE_DETAILS + BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_DETAILS))
        .andExpect(model().attribute("badge", badgeViewModel))
        .andExpect(model().attribute("canBeReplaced", true))
        .andExpect(model().attribute("canBeCancelled", true));
  }

  @Test
  public void
      show_shouldDisplayBadgeDetailsForPersonWithoutCancelAndReplaceOption_WhenBadgeIsExpired()
          throws Exception {
    Party party = new Party();
    party.setTypeCode(BadgePartyTypeEnum.PERSON.getCode());
    badge.setParty(party);
    badge.setStartDate(LocalDate.now().minusDays(10));
    badge.setExpiryDate(LocalDate.now().minusDays(5));

    when(badgeServiceMock.retrieve(BADGE_NUMBER)).thenReturn(Optional.of(badge));
    when(badgeToBadgeDetailsViewModelMock.convert(badge)).thenReturn(badgeViewModel);
    mockMvc
        .perform(get(URL_BADGE_DETAILS + BADGE_NUMBER))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_BADGE_DETAILS))
        .andExpect(model().attribute("badge", badgeViewModel))
        .andExpect(model().attribute("canBeReplaced", false))
        .andExpect(model().attribute("canBeCancelled", false));
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

  @Test
  public void deleteBadge_shouldRedirectToFindBadges() throws Exception {
    mockMvc
        .perform(delete(URL_DELETE_BADGE + BADGE_NUMBER))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/manage-badges"));

    verify(badgeServiceMock, times(1)).deleteBadge(BADGE_NUMBER);
  }
}
