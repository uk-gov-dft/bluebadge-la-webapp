package uk.gov.dft.bluebadge.webapp.la.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;

@Component
public class SecurityUtils {
  public User getCurrentUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Would be a coding error if this called for a non-authenticated area.
    if (null == authentication) {
      throw new NullPointerException("No user currently authenticated.");
    }

    //    UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
    //    return userDetails.getBlueBadgeUser();
    // TODO This needs to be populated from the details from the auth server. Which needs expanding first.
    User userData = new User();
    userData.setLocalAuthorityId(22);
    userData.setEmailAddress(authentication.getName());
    userData.setName("TODO SecurityUtils");
    // @see uk.gov.dft.bluebadge.webapp.la.controller.CreateANewUserController.submit()
    userData.setRoleId(1);

    return userData;
  }
}
