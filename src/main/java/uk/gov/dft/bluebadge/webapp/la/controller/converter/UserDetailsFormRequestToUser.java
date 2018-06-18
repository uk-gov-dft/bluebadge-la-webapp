package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.UserDetailsFormRequest;

@Component
public class UserDetailsFormRequestToUser implements Converter<UserDetailsFormRequest, User> {

  @Override
  public User convert(UserDetailsFormRequest source) {
    Assert.notNull(source, "Source cannot be null");
    return new User().name(source.getName()).emailAddress(source.getEmailAddress());
  }
}
