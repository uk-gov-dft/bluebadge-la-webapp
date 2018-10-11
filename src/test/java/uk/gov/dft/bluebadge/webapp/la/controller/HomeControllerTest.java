package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;

public class HomeControllerTest {

  @Mock private SecurityUtils securityUtilsMock;

  private MockMvc mockMvc;

  private HomeController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    controller = new HomeController(securityUtilsMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void
      showHome_shouldDisplayManageBadgesPageAndAddEmailAttribute_WhenUserIsSignedInAndIsLaReadOnly()
          throws Exception {
    when(securityUtilsMock.isPermitted(Permissions.FIND_APPLICATION)).thenReturn(false);
    when(securityUtilsMock.isPermitted(Permissions.FIND_BADGES)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.FIND_USERS)).thenReturn(false);
    when(securityUtilsMock.isPermitted(Permissions.ORDER_BADGE)).thenReturn(false);

    mockMvc
        .perform(get("/"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-badges"));
  }

  @Test
  public void
      showHome_shouldDisplayNewApplicationsPageAndAddEmailAttribute_WhenUserIsSignedInAndIsLaEditor()
          throws Exception {
    when(securityUtilsMock.isPermitted(Permissions.FIND_APPLICATION)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.FIND_BADGES)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.FIND_USERS)).thenReturn(false);
    when(securityUtilsMock.isPermitted(Permissions.ORDER_BADGE)).thenReturn(true);

    mockMvc
        .perform(get("/"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/new-applications"));
  }

  @Test
  public void
      showHome_shouldDisplayNewApplicationsPageAndAddEmailAttribute_WhenUserIsSignedInAndIsLaAdmin()
          throws Exception {
    when(securityUtilsMock.isPermitted(Permissions.FIND_APPLICATION)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.FIND_BADGES)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.FIND_USERS)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.ORDER_BADGE)).thenReturn(true);

    mockMvc
        .perform(get("/"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));
  }

  @Test
  public void
      showHome_shouldDisplayManageUsersPageAndAddEmailAttribute_WhenUserIsSignedInAndIsDftAdmin()
          throws Exception {
    when(securityUtilsMock.isPermitted(Permissions.FIND_APPLICATION)).thenReturn(false);
    when(securityUtilsMock.isPermitted(Permissions.FIND_BADGES)).thenReturn(false);
    when(securityUtilsMock.isPermitted(Permissions.FIND_USERS)).thenReturn(true);
    when(securityUtilsMock.isPermitted(Permissions.ORDER_BADGE)).thenReturn(false);

    mockMvc
        .perform(get("/"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/manage-users"));
  }
}
