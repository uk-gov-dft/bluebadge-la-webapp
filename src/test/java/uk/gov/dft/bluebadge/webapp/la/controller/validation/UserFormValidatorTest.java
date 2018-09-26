package uk.gov.dft.bluebadge.webapp.la.controller.validation;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;

import uk.gov.dft.bluebadge.common.security.Permissions;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.common.security.model.BBPrincipal;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;

public class UserFormValidatorTest {
	
	private SecurityUtils sercurity = mock(SecurityUtils.class);
	
	private UserFormValidator validator = new UserFormValidator(sercurity);
	
	@Test(expected=uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException.class)
	public void validateUserForm_LAnotSelectedForNonDfTAdminUser_shouldThrowBadRequestException() {
		UserFormRequest user  = new UserFormRequest();
		user.setEmailAddress("email@mail.com");
		user.setName("first last");
		user.setRole(Role.LA_ADMIN);
		user.setLocalAuthorityShortCode(null);
		
		validator.validate(user);
	}
	
	@Test(expected=AccessDeniedException.class)
	public void validateUserForm_userNotAllowedCreateDfTAdmin_shouldThrowAccessDeniedException() {
		UserFormRequest user  = new UserFormRequest();
		user.setEmailAddress("email@mail.com");
		user.setName("first last");
		user.setRole(Role.DFT_ADMIN);
		user.setLocalAuthorityShortCode("ABERD");
		
		when(sercurity.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(false);
		BBPrincipal admin = BBPrincipal.builder()
											.emailAddress("cant_create_dft_admin@mail.com")
											.clientId("client_id")
											.roleName("role_name")
									  .build();
		when(sercurity.getCurrentAuth()).thenReturn(admin);

		validator.validate(user);
	}

	@Test
	public void validateUserForm_userAllowedCreateDfTAdmin_shouldClearLAifPresent() {
		UserFormRequest user  = new UserFormRequest();
		user.setEmailAddress("email@mail.com");
		user.setName("first last");
		user.setRole(Role.DFT_ADMIN);
		user.setLocalAuthorityShortCode("ABERD");
		
		when(sercurity.isPermitted(Permissions.CREATE_DFT_USER)).thenReturn(true);

		validator.validate(user);
		assertNull(user.getLocalAuthorityShortCode());
	}
}
