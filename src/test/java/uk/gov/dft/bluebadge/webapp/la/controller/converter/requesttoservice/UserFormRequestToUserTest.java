package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

public class UserFormRequestToUserTest {

  @Mock
  private SecurityUtils securityUtilsMock;
  private UserFormRequestToUser userFormRequestToUser;

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    userFormRequestToUser = new UserFormRequestToUser(securityUtilsMock);
  }

  @Test
  public void convertFormToUser_whenAdminRole_thenLACopiedFromUser(){
    when(securityUtilsMock.getCurrentLocalAuthorityShortCode()).thenReturn("BIRM");

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress("b@b.com");
    form.setLocalAuthorityShortCode(null);
    form.setName("bob");
    form.setRole(Role.LA_ADMIN);
    User user = userFormRequestToUser.convert(form);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("bob");
    assertThat(user.getEmailAddress()).isEqualTo("b@b.com");
    assertThat(user.getLocalAuthorityShortCode()).isEqualTo("BIRM");
    assertThat(user.getRoleId()).isEqualTo(Role.LA_ADMIN.getRoleId());
  }

  @Test
  public void convertFormToUser_whenDftAdminRole(){
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress("b@b.com");
    form.setLocalAuthorityShortCode("doesn't matter");
    form.setName("bob");
    form.setRole(Role.DFT_ADMIN);
    User user = userFormRequestToUser.convert(form);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("bob");
    assertThat(user.getEmailAddress()).isEqualTo("b@b.com");
    assertThat(user.getLocalAuthorityShortCode()).isNull();
    assertThat(user.getRoleId()).isEqualTo(Role.DFT_ADMIN.getRoleId());

    verify(securityUtilsMock, never()).getCurrentLocalAuthorityShortCode();
  }

  @Test
  public void convertFormToUser_whenDftUserAndNoneDftRole_thenLACopied(){
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);

    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress("b@b.com");
    form.setLocalAuthorityShortCode("Some LA");
    form.setName("bob");
    form.setRole(Role.LA_EDITOR);
    User user = userFormRequestToUser.convert(form);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("bob");
    assertThat(user.getEmailAddress()).isEqualTo("b@b.com");
    assertThat(user.getLocalAuthorityShortCode()).isEqualTo("Some LA");
    assertThat(user.getRoleId()).isEqualTo(Role.LA_EDITOR.getRoleId());

    verify(securityUtilsMock, never()).getCurrentLocalAuthorityShortCode();
  }

  @Test(expected = AccessDeniedException.class)
  public void convertFormToUser_whenDftAdminRole_andUserNotGotPermission_thenException(){
    when(securityUtilsMock.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(false);

    UserFormRequest form = new UserFormRequest();
    form.setRole(Role.DFT_ADMIN);

    userFormRequestToUser.convert(form);
  }
}