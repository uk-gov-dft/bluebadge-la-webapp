package uk.gov.dft.bluebadge.webapp.la.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.gov.dft.bluebadge.model.usermanagement.UserData;

@Component
public class SecurityUtils {
  public UserData getCurrentUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // TODO handle no user? maybe?

    //    UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
    //    return userDetails.getBlueBadgeUser();
    // TODO
    UserData userData = new UserData();
    userData.setLocalAuthorityId(2);
    userData.setEmailAddress(authentication.getName());
    userData.setName("TODO SecurityUtils"); // TODO
    // @see uk.gov.dft.bluebadge.webapp.la.controller.CreateANewUserController.createANewUser()
    userData.setRoleId(1); // TODO

    return userData;
  }
}
