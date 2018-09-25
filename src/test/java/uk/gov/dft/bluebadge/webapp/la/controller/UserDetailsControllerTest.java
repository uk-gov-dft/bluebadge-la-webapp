package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static uk.gov.dft.bluebadge.webapp.la.controller.ManageUsersController.URL_MANAGE_USERS;

import com.google.common.collect.Lists;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.api.model.Error;
import uk.gov.dft.bluebadge.common.api.model.ErrorErrors;
import uk.gov.dft.bluebadge.common.security.Role;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class UserDetailsControllerTest extends BaseControllerTest {

  private static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  private static final String EMAIL_ADDRESS_UPDATED = "updated@joe.com";
  private static final String EMAIL_ADDRESS_ERROR = "updated joe.com";

  private static final String NAME = "joeblogs";
  private static final String NAME_UPDATED = "updated";
  private static final String NAME_ERROR = "updated11";

  private static final int ROLE_ID = Role.LA_ADMIN.getRoleId();
  private static final int ROLE_ID_UPDATED = Role.LA_EDITOR.getRoleId();

  private static final String ROLE_NAME = Role.LA_ADMIN.name();
  private static final String ROLE_NAME_UPDATED = Role.LA_EDITOR.name();

  private static final String LOCAL_AUTHORITY_SHORT_CODE = "BIRM";
  private static final UUID USER_ID = UUID.randomUUID();
  private static final String NAME_PARAM = "name";
  private static final String ROLE_NAME_PARAM = "roleName";
  private static final String EMAIL_ADDRESS_PARAM = "emailAddress";
  private static final String LOCAL_AUTHORITY_ID_PARAM = "localAuthorityShortCode";
  private static final String MODEL_FORM_REQUEST = "formRequest";
  private static final String MODEL_ID = "uuid";

  private static final String ERROR_MSG_EMAIL_ADDRESS = "error in emailAddress";
  private static final String ERROR_MSG_NAME = "error in name";

  @Mock private UserService userServiceMock;

  // Test Data
  private User userSignedIn;
  private User user;
  private User userWithId;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    UserDetailsController controller =
        new UserDetailsController(userServiceMock, new UserFormRequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userSignedIn =
        User.builder()
            .name("Joe")
            .uuid(USER_ID)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();

    user =
        User.builder()
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();

    userWithId =
        User.builder()
            .uuid(USER_ID)
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .roleId(ROLE_ID)
            .build();
  }

  private UserFormRequest getUserDetails(String emailAddress, String name, String roleName) {
    UserFormRequest form = new UserFormRequest();
    form.setEmailAddress(emailAddress);
    form.setName(name);
    form.setRoleName(roleName);

    return form;
  }

  @Test
  public void
      showUserDetails_shouldShowUserDetailsTemplateWithUserDetails_WhenYouAreSignedInAndUserExists()
          throws Exception {
    when(userServiceMock.retrieve(USER_ID)).thenReturn(user);
    UserFormRequest formRequest = getUserDetails(EMAIL_ADDRESS, NAME, ROLE_NAME);
    formRequest.setLocalAuthorityShortCode(user.getLocalAuthorityShortCode());
    mockMvc
        .perform(get(URL_USER_DETAILS + USER_ID).sessionAttr("user", userSignedIn))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_USER_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, formRequest))
        .andExpect(model().attribute(MODEL_ID, USER_ID));
  }

  @Test
  public void
      updateUserDetails_shouldShowUserDetailsTemplateWithNewUserDetails_WhenYouAreSignedInAndThereAreNoValidationErrors()
          throws Exception {
    when(userServiceMock.retrieve(USER_ID)).thenReturn(userWithId);
    UserFormRequest formRequest =
        getUserDetails(EMAIL_ADDRESS_UPDATED, NAME_UPDATED, ROLE_NAME_UPDATED);
    mockMvc
        .perform(
            post(URL_USER_DETAILS + USER_ID)
                .param(NAME_PARAM, NAME_UPDATED)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS_UPDATED)
                .param(ROLE_NAME_PARAM, ROLE_NAME_UPDATED))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/manage-users"))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, formRequest));
    User user =
        User.builder()
            .uuid(USER_ID)
            .name(NAME_UPDATED)
            .emailAddress(EMAIL_ADDRESS_UPDATED)
            .roleId(ROLE_ID_UPDATED)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .build();
    verify(userServiceMock, times(1)).update(user);
  }

  @Test
  public void
      updateUserDetails_shouldShowUserDetailsTemplateWithNewUserDetailsAndValidationErrors_WhenYouAreSignedInAndThereAreValidationErrors()
          throws Exception {
    when(userServiceMock.retrieve(USER_ID)).thenReturn(userWithId);

    ErrorErrors emailAddressError =
        new ErrorErrors().field(EMAIL_ADDRESS_PARAM).message(ERROR_MSG_EMAIL_ADDRESS);
    ErrorErrors nameError = new ErrorErrors().field(NAME_PARAM).message(ERROR_MSG_NAME);
    CommonResponse userResponseUpdate = new UserResponse();
    userResponseUpdate.setError(
        new Error().errors(Lists.newArrayList(emailAddressError, nameError)));
    user =
        User.builder()
            .uuid(USER_ID)
            .name(NAME_ERROR)
            .emailAddress(EMAIL_ADDRESS_ERROR)
            .roleId(ROLE_ID)
            .localAuthorityShortCode(LOCAL_AUTHORITY_SHORT_CODE)
            .build();

    when(userServiceMock.update(any())).thenThrow(new BadRequestException(userResponseUpdate));

    UserFormRequest formRequest = getUserDetails(EMAIL_ADDRESS_ERROR, NAME_ERROR, ROLE_NAME);

    mockMvc
        .perform(
            post(URL_USER_DETAILS + USER_ID)
                .param(NAME_PARAM, NAME_ERROR)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS_ERROR)
                .param(ROLE_NAME_PARAM, ROLE_NAME))
        .andExpect(status().isOk())
        .andExpect(view().name(TEMPLATE_USER_DETAILS))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, formRequest))
        .andExpect(model().errorCount(2))
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    MODEL_FORM_REQUEST, "emailAddress", ERROR_MSG_EMAIL_ADDRESS))
        .andExpect(model().attributeHasFieldErrorCode(MODEL_FORM_REQUEST, "name", ERROR_MSG_NAME));

    verify(userServiceMock, times(1)).update(user);
  }

  @Test
  public void deleteUser_shouldRedirectToManageUsers_WhenYouAreSignedInAndThereAreNoErrors()
      throws Exception {
    mockMvc
        .perform(
            delete(URL_USER_DETAILS + USER_ID)
                .param(LOCAL_AUTHORITY_ID_PARAM, String.valueOf(LOCAL_AUTHORITY_SHORT_CODE)))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_USERS));
    verify(userServiceMock, times(1)).delete(USER_ID);
  }

  @Test
  public void requestPasswordReset_shouldRedirectToManageUsers_WhenThereAreNoErrors()
      throws Exception {
    mockMvc
        .perform(
            post(URL_REQUEST_PASSWORD_RESET + USER_ID)
                .param(LOCAL_AUTHORITY_ID_PARAM, String.valueOf(LOCAL_AUTHORITY_SHORT_CODE)))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_USERS));
    verify(userServiceMock, times(1)).requestPasswordReset(USER_ID);
  }
}
