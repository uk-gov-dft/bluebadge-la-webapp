package uk.gov.dft.bluebadge.webapp.la.security;

import java.util.Collections;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.UserManagementApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Component
public class UserDetailsTokenService
    implements UserDetailsService, ApplicationContextAware, InitializingBean {
  private UserManagementApiClient userManagementApiClient;
  private ReferenceDataService referenceDataService;
  private ApplicationContext applicationContext;

  @Override
  public void afterPropertiesSet() throws Exception {
    userManagementApiClient = applicationContext.getBean(UserManagementApiClient.class);
    referenceDataService = applicationContext.getBean(ReferenceDataService.class);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public UserPrincipal loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userManagementApiClient.currentUserDetails();
    Set<SimpleGrantedAuthority> authorities =
        Collections.singleton(new SimpleGrantedAuthority(user.getRoleName()));

    String localAuthorityDisplayValue =
        referenceDataService.retrieveLocalAuthorityDisplayValue(user.getLocalAuthorityShortCode());

    return UserPrincipal.builder()
        .uuid(user.getUuid())
        .emailAddress(user.getEmailAddress())
        .name(user.getName())
        .roleName(user.getRoleName())
        .authorities(authorities)
        .localAuthorityShortCode(user.getLocalAuthorityShortCode())
        .localAuthorityName(localAuthorityDisplayValue)
        .build();
  }
}
