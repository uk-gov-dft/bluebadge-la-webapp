package uk.gov.dft.bluebadge.webapp.la.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.model.usermanagement.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;

@Component
public class CreateANewUserRequestToUser implements Converter<CreateANewUserFormRequest, User> {

  @Override
  public User convert(CreateANewUserFormRequest source) {
    Assert.notNull(source, "Source cannot be null");
    // TODO: Remove hardcoded values: localauthority
    return new User().name(source.getName()).emailAddress(source.getEmailAddress());
  }
}
