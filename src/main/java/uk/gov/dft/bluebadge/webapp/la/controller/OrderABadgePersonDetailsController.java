package uk.gov.dft.bluebadge.webapp.la.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderABadgePersonDetailsController {
  public static final String URL = "/order-a-badge/details";

  public static final String TEMPLATE = "order-a-badge/details";

  public static final String REDIRECT_URL_HOME = "/";

  private BadgeService badgeService;

  @Autowired
  public OrderABadgePersonDetailsController(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @GetMapping(URL)
  public String show(
      @ModelAttribute("formRequest") final OrderABadgePersonDetailsFormRequest formRequest) {
    return TEMPLATE;
  }

  @PostMapping(URL)
  public String submit(
      @ModelAttribute("formRequest") OrderABadgePersonDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    badgeService.validateOrder();

    //      UserData signedInUser = SignInUtils.getUserSignedIn(session).get();
    // TODO: Role id should come from the form
    /*User user =
    createANewUserRequest2User
        .convert(formRequest)
        .localAuthorityId(signedInUser.getLocalAuthorityId())
        .roleId(1);*/
    // UserResponse userResponse = userService.create(user);

    return ErrorHandlingUtils.handleError(null, REDIRECT_URL_HOME, URL, bindingResult, model);
  }
}
