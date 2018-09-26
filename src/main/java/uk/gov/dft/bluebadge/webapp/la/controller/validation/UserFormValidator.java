package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static uk.gov.dft.bluebadge.common.security.Role.DFT_ADMIN;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

@Component
@Slf4j
public class UserFormValidator {

	public void validate(UserFormRequest user) throws BadRequestException {
		if (!DFT_ADMIN.equals(user.getRole()) && StringUtils.isEmpty(user.getLocalAuthorityShortCode())) {
			CommonResponse commonResponse = new CommonResponse();
			ErrorErrors laError = new ErrorErrors().field("localAuthorityShortCode")
					.message("NotNull.user.localAuthorityShortCode");
			commonResponse.setError(new Error().errors(Lists.newArrayList(laError)));

			throw new BadRequestException(commonResponse);
		} else {
			log.error("Local authority can't be assigned to DfT Administrator");
			user.setLocalAuthorityShortCode(null);
		}
	}

}
