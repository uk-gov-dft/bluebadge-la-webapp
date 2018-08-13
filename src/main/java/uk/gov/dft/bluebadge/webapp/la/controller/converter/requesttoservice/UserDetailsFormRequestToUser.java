package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;

@Component
public class UserDetailsFormRequestToUser implements Converter<UserDetailsFormRequest, User> {

  @Override
  public User convert(UserDetailsFormRequest formRequest) {
    Assert.notNull(formRequest, "formRequest cannot be null");
    return User.builder()
        .name(formRequest.getName())
        .emailAddress(formRequest.getEmailAddress())
        .build();
  }
}
