package uk.gov.dft.bluebadge.webapp.la.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class UserDetailsTokenServiceTest {
  UserDetailsTokenService userDetailsTokenService;
  @Mock private ApplicationContext mockAppContext;
  @Mock private UserManagementApiClient mockUserManagementApiClient;
  @Mock private ReferenceDataService mockReferenceDataService;

  @Before
  public void setup() throws Exception {
    initMocks(this);
    userDetailsTokenService = new UserDetailsTokenService();
    userDetailsTokenService.setApplicationContext(mockAppContext);

    when(mockAppContext.getBean(UserManagementApiClient.class))
        .thenReturn(mockUserManagementApiClient);
    when(mockAppContext.getBean(ReferenceDataService.class)).thenReturn(mockReferenceDataService);
    userDetailsTokenService.afterPropertiesSet();
  }

  @Test
  public void whenLoadUser_thenPrincipalReturned() {
    User user =
        User.builder()
            .emailAddress("a@a.com")
            .localAuthorityShortCode("ABC")
            .roleName("testRole")
            .build();
    when(mockUserManagementApiClient.currentUserDetails()).thenReturn(user);
    when(mockReferenceDataService.retrieveLocalAuthorityDisplayValue("ABC")).thenReturn("LA Name");

    UserPrincipal userPrincipal = userDetailsTokenService.loadUserByUsername("bob@bob.com");

    assertThat(userPrincipal).isNotNull();
    assertThat(userPrincipal.getEmailAddress()).isEqualTo("a@a.com");
    assertThat(userPrincipal.getLocalAuthorityShortCode()).isEqualTo("ABC");
    assertThat(userPrincipal.getLocalAuthorityName()).isEqualTo("LA Name");
    assertThat(userPrincipal.getRoleName()).isEqualTo("testRole");
    assertThat(userPrincipal.getAuthorities()).extracting("authority").containsOnly("testRole");
  }
}
