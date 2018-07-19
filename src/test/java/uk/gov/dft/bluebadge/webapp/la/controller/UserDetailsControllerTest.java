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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.CommonResponse;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.Error;
import uk.gov.dft.bluebadge.webapp.la.client.common.model.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.UserResponse;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.UserDetailsFormRequestToUser;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.service.UserService;

public class UserDetailsControllerTest extends ControllerTest {

  private static final String EMAIL_ADDRESS = "joeblogs@joe.com";
  private static final String EMAIL_ADDRESS_UPDATED = "updated@joe.com";
  private static final String EMAIL_ADDRESS_ERROR = "updated joe.com";

  private static final String NAME = "joeblogs";
  private static final String NAME_UPDATED = "updated";
  private static final String NAME_ERROR = "updated11";

  private static final int ROLE_ID = 1;
  private static final int LOCAL_AUTHORITY = 1;
  private static final int USER_ID = 1;
  private static final String NAME_PARAM = "name";
  private static final String EMAIL_ADDRESS_PARAM = "emailAddress";
  private static final String LOCAL_AUTHORITY_ID_PARAM = "localAuthorityId";
  private static final String MODEL_FORM_REQUEST = "formRequest";
  private static final String MODEL_ID = "id";

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
        new UserDetailsController(userServiceMock, new UserDetailsFormRequestToUser());

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    userSignedIn =
        new User()
            .name("Joe")
            .id(1)
            .emailAddress("joe.blogs@email.com")
            .localAuthorityId(LOCAL_AUTHORITY);
    user =
        new User()
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            .localAuthorityId(LOCAL_AUTHORITY)
            .roleId(ROLE_ID);
    userWithId =
        new User()
            .id(USER_ID)
            .emailAddress(EMAIL_ADDRESS)
            .name(NAME)
            .localAuthorityId(LOCAL_AUTHORITY)
            .roleId(ROLE_ID);
  }

  private UserDetailsFormRequest getUserDetailsFormRequest(String emailAddress, String name) {
    UserDetailsFormRequest userDetailsFormRequest = new UserDetailsFormRequest();
    userDetailsFormRequest.setEmailAddress(emailAddress);
    userDetailsFormRequest.setName(name);
    return userDetailsFormRequest;
  }

  @Test
  public void
      showUserDetails_shouldShowUserDetailsTemplateWithUserDetails_WhenYouAreSignedInAndUserExists()
          throws Exception {
    when(userServiceMock.retrieve(USER_ID)).thenReturn(user);
    UserDetailsFormRequest formRequest = getUserDetailsFormRequest(EMAIL_ADDRESS, NAME);
    formRequest.setLocalAuthorityId(user.getLocalAuthorityId());
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
    UserDetailsFormRequest formRequest =
        getUserDetailsFormRequest(EMAIL_ADDRESS_UPDATED, NAME_UPDATED);
    mockMvc
        .perform(
            post(URL_USER_DETAILS + USER_ID)
                .param(NAME_PARAM, NAME_UPDATED)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS_UPDATED))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/manage-users"))
        .andExpect(model().attribute(MODEL_FORM_REQUEST, formRequest));
    User user =
        new User()
            .id(USER_ID)
            .name(NAME_UPDATED)
            .emailAddress(EMAIL_ADDRESS_UPDATED)
            .roleId(ROLE_ID)
            .localAuthorityId(LOCAL_AUTHORITY);
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
        new User()
            .id(USER_ID)
            .name(NAME_ERROR)
            .emailAddress(EMAIL_ADDRESS_ERROR)
            .roleId(ROLE_ID)
            .localAuthorityId(LOCAL_AUTHORITY);
    when(userServiceMock.update(any())).thenThrow(new BadRequestException(userResponseUpdate));

    UserDetailsFormRequest formRequest = getUserDetailsFormRequest(EMAIL_ADDRESS_ERROR, NAME_ERROR);

    mockMvc
        .perform(
            post(URL_USER_DETAILS + USER_ID)
                .param(NAME_PARAM, NAME_ERROR)
                .param(EMAIL_ADDRESS_PARAM, EMAIL_ADDRESS_ERROR))
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
                .param(LOCAL_AUTHORITY_ID_PARAM, String.valueOf(LOCAL_AUTHORITY)))
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
                .param(LOCAL_AUTHORITY_ID_PARAM, String.valueOf(LOCAL_AUTHORITY)))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(URL_MANAGE_USERS));
    verify(userServiceMock, times(1)).requestPasswordReset(USER_ID);
  }
}
