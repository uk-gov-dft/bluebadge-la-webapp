package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

@Component
@Slf4j
public class UserFormValidator {

	private final SecurityUtils securityUtils;

	public UserFormValidator(SecurityUtils securityUtils) {
		this.securityUtils = securityUtils;
	}

	public void validate(UserFormRequest user) throws BadRequestException {
		if (!DFT_ADMIN.equals(user.getRole()) && StringUtils.isEmpty(user.getLocalAuthorityShortCode())) {

			CommonResponse response = new CommonResponse()
					.error(new Error().reason("NotNull.user.localAuthorityShortCode")).id("localAuthorityShortCode");
			throw new BadRequestException(response);
		}

		if (DFT_ADMIN.equals(user.getRole())) {
			if (!securityUtils.isPermitted(Permissions.CREATE_DFT_USER)) {
				log.error("User {} not permitted to create DFT user", securityUtils.getCurrentAuth().getEmailAddress());
				throw new AccessDeniedException("User not permitted to create DFT user") ;
			} else if (null != user.getLocalAuthorityShortCode()) {
				log.error("Local authority can't be assigned to DfT Administrator");
				user.setLocalAuthorityShortCode(null);
			}
		}
	}
}
