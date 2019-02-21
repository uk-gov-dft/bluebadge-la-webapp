package uk.gov.dft.bluebadge.webapp.la.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.dft.bluebadge.common.security.SecurityUtils;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.Application;

@Service("applicationSecurity")
public class ApplicationSecurity {
  private final SecurityUtils securityUtils;
  private final ApplicationService applicationService;

  @Autowired
  public ApplicationSecurity(SecurityUtils securityUtils, ApplicationService applicationService) {
    this.securityUtils = securityUtils;
    this.applicationService = applicationService;
  }

  public boolean isAuthorised(String applicationId) {
    Application application = applicationService.retrieve(applicationId);
    return securityUtils.isAuthorisedLACode(application.getLocalAuthorityCode());
  }
}
