package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.test.web.servlet.MockMvc;

abstract class BaseControllerTest {
  static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";
  static final String TEMPLATE_BADGE_DETAILS = "manage-badges/badge-details";
  static final String TEMPLATE_LOCAL_AUTHORITY_DETAILS =
      "manage-local-authorities/local-authority-details";

  static final String URL_USER_DETAILS = "/manage-users/user-details/";
  static final String URL_REQUEST_PASSWORD_RESET = "/manage-users/request***REMOVED***-reset/";

  static final String URL_BADGE_DETAILS = "/manage-badges/";
  static final String URL_DELETE_BADGE = "/manage-badges/delete-badge/";

  static final String URL_MANAGE_LOCAL_AUTHORITIES = "/manage-local-authorities";
  static final String URL_LOCAL_AUTHORITY_DETAILS =
      "/manage-local-authorities/local-authority-details/";

  static final String MODEL_FORM_REQUEST = "formRequest";

  MockMvc mockMvc;
}
