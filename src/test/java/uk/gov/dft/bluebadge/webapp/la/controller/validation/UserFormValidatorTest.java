package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

public class UserFormValidatorTest {
	
	
	private UserFormValidator validator = new UserFormValidator();
	
	@Test(expected=uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException.class)
	public void validateUserForm_LAnotSelectedForNonDfTAdminUser_shouldThrowBadRequestException() {
		UserFormRequest user  = new UserFormRequest();
		user.setEmailAddress("email@mail.com");
		user.setName("first last");
		user.setRole(Role.LA_ADMIN);
		user.setLocalAuthorityShortCode(null);
		
		validator.validate(user);
	}
	
	@Test
	public void validateUserForm_LASelectedForDfTAdminUser_shouldClearLAShortCode() {
		UserFormRequest user  = new UserFormRequest();
		user.setEmailAddress("email@mail.com");
		user.setName("first last");
		user.setRole(Role.DFT_ADMIN);
		user.setLocalAuthorityShortCode("ABERD");
		
		validator.validate(user);
		assertNull(user.getLocalAuthorityShortCode());
	}
}
