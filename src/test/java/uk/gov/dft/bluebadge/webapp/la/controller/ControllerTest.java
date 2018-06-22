package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.test.web.servlet.MockMvc;

abstract class ControllerTest {
  static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  static final String URL_USER_DETAILS = "/manage-users/user-details/";
  static final String URL_REQUEST_PASSWORD_RESET = "/manage-users/request-reset***REMOVED***/";

  MockMvc mockMvc;
}
