package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

@Component
public class UserFormRequestToUser implements Converter<UserFormRequest, User> {

  private final SecurityUtils securityUtils;

  @Autowired
  public UserFormRequestToUser(SecurityUtils securityUtils) {
    this.securityUtils = securityUtils;
  }

  @Override
  public User convert(UserFormRequest formRequest) {
    Assert.notNull(formRequest, "formRequest cannot be null");

    String laShortCode = null;
    if (DFT_ADMIN.equals(formRequest.getRole())) {
      if (!securityUtils.isPermitted(Permissions.CREATE_DFT_USER)) {
        throw new AccessDeniedException("User not permitted to create DFT user");
      }
    } else if (securityUtils.isPermitted(Permissions.CREATE_DFT_USER)) {
      laShortCode = formRequest.getLocalAuthorityShortCode();
    } else {
      laShortCode = securityUtils.getCurrentLocalAuthorityShortCode();
    }

    return User.builder()
        .name(formRequest.getName())
        .emailAddress(formRequest.getEmailAddress())
        .localAuthorityShortCode(laShortCode)
        .roleId(formRequest.getRole().getRoleId())
        .build();
  }
}
