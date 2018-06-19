package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderABadgeFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.TemplateModelUtils;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderABadgeController {
  public static final String URL_ORDER_A_BADGE = "/order-a-badge";

  public static final String TEMPLATE_ORDER_A_BADGE = "order-a-badge";

  public static final String REDIRECT_URL_HOME = "/";

  private BadgeService badgeService;

  @Autowired
  public OrderABadgeController(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @GetMapping(URL_ORDER_A_BADGE)
  public String showOrderABadge(
      @ModelAttribute("formRequest") final OrderABadgeFormRequest formRequest,
      HttpSession session) {
    return TEMPLATE_ORDER_A_BADGE;
  }

  @PostMapping(URL_ORDER_A_BADGE)
  public String orderABadge(
      @ModelAttribute("formRequest") OrderABadgeFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    try {

      badgeService.validateOrder();

      //      UserData signedInUser = SignInUtils.getUserSignedIn(session).get();
      // TODO: Role id should come from the form
      /*User user =
      createANewUserRequest2User
          .convert(formRequest)
          .localAuthorityId(signedInUser.getLocalAuthorityId())
          .roleId(1);*/
      //UserResponse userResponse = userService.create(user);
      return ErrorHandlingUtils.handleError(
          null, REDIRECT_URL_HOME, URL_ORDER_A_BADGE, bindingResult, model);
    } catch (Exception ex) {
      TemplateModelUtils.addCustomError(
          "error.validateOrder.generalError.title",
          "error.validateOrder.generalError.description",
          model);
      return URL_ORDER_A_BADGE;
    }
  }
}
