package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.test.web.servlet.MockMvc;

public abstract class ControllerTest {
  protected static final String TEMPLATE_MANAGE_USERS = "manage-users";
  protected static final String TEMPLATE_USER_DETAILS = "manage-users/user-details";

  protected static final String URL_USER_DETAILS = "/manage-users/user-details/";

  protected MockMvc mockMvc;
}
