package uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.gov.dft.bluebadge.webapp.la.client.usermanagement.model.User;
import uk.gov.dft.bluebadge.webapp.la.controller.request.CreateANewUserFormRequest;

@Component
public class CreateANewUserFormRequestToUser implements Converter<CreateANewUserFormRequest, User> {

  @Override
  public User convert(CreateANewUserFormRequest formRequest) {
    Assert.notNull(formRequest, "formRequest cannot be null");
    return User.builder()
        .name(formRequest.getName())
        .emailAddress(formRequest.getEmailAddress())
        .build();
  }
}
