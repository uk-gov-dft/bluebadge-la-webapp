package uk.gov.dft.bluebadge.webapp.la.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.badgemanagement.model.BadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.OrderBadgeFormsToBadgeOrderRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.OrderBadgeFormsToOrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgePersonDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.OrderBadgeProcessingFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.OrderBadgeCheckOrderViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.BadgeService;

@Slf4j
@Controller
public class OrderBadgeCheckOrderController {
    public static final String URL = "/order-a-badge/check-order";

    public static final String TEMPLATE = "order-a-badge/check-order";

    public static final String REDIRECT_BADGE_ORDERED =
            "redirect:" + OrderBadgeBadgeOrderedController.URL;

    private BadgeService badgeService;
    private OrderBadgeFormsToBadgeOrderRequest converterToServiceModel;
    private OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel;

    @Autowired
    public OrderBadgeCheckOrderController(
            BadgeService badgeService,
            OrderBadgeFormsToBadgeOrderRequest converterToServiceModel,
            OrderBadgeFormsToOrderBadgeCheckOrderViewModel converterToViewModel) {
        this.badgeService = badgeService;
        this.converterToServiceModel = converterToServiceModel;
        this.converterToViewModel = converterToViewModel;
    }

    @GetMapping(URL)
    public String show(Model model, HttpSession session) {
        OrderBadgePersonDetailsFormRequest detailsForm =
                (OrderBadgePersonDetailsFormRequest)
                        session.getAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);

        OrderBadgeProcessingFormRequest processingForm =
                (OrderBadgeProcessingFormRequest)
                        session.getAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);

        OrderBadgeCheckOrderViewModel data = converterToViewModel.convert(detailsForm, processingForm);

        Map<String, String> photo = (HashMap<String, String >) session.getAttribute("photos");
        data.setPhoto(photo.get("thumb"));

        model.addAttribute("data", data);
        return TEMPLATE;
    }

    @PostMapping(URL)
    public String submit(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        OrderBadgePersonDetailsFormRequest detailsForm =
                (OrderBadgePersonDetailsFormRequest)
                        session.getAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);
        OrderBadgeProcessingFormRequest processingForm =
                (OrderBadgeProcessingFormRequest)
                        session.getAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
        BadgeOrderRequest badgeOrderRequest =
                converterToServiceModel.convert(detailsForm, processingForm);

        String badgeNumber = badgeService.orderABadgeForAPerson(badgeOrderRequest);
        redirectAttributes.addFlashAttribute("badgeNumber", badgeNumber);

        session.removeAttribute(OrderBadgeIndexController.FORM_REQUEST_SESSION);
        session.removeAttribute(OrderBadgePersonDetailsController.FORM_REQUEST_SESSION);
        session.removeAttribute(OrderBadgeProcessingController.FORM_REQUEST_SESSION);
        return REDIRECT_BADGE_ORDERED;
    }
}
