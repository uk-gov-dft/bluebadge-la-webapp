package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

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

  static ResultMatcher formRequestFlashAttributeHasFieldErrorCode(
      String fieldName, String error, String modelAttributeName) {
    return flash()
        .attribute(
            "org.springframework.validation.BindingResult." + modelAttributeName,
            hasProperty(
                "fieldErrors",
                hasItem(
                    allOf(
                        hasProperty("field", equalTo(fieldName)),
                        hasProperty("code", equalTo(error))))));
  }

  static ResultMatcher formRequestFlashAttributeHasFieldErrorCode(String fieldName, String error) {
    return formRequestFlashAttributeHasFieldErrorCode(fieldName, error, "formRequest");
  }

  static ResultMatcher formRequestFlashAttributeCount(int expectedErrorCount) {
    return formRequestFlashAttributeCount(expectedErrorCount, "formRequest");
  }

  static ResultMatcher formRequestFlashAttributeCount(
      int expectedErrorCount, String modelAttributeName) {
    return flash()
        .attribute(
            "org.springframework.validation.BindingResult." + modelAttributeName,
            hasProperty("fieldErrors", hasSize(expectedErrorCount)));
  }
}
