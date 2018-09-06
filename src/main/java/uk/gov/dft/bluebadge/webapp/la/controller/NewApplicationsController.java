package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationTypeCodeField;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationSummaryToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
public class NewApplicationsController {

    public static final String URL = "/new-applications";

    public static final String TEMPLATE = "new-applications";

    private ApplicationService applicationService;
    private ApplicationSummaryToApplicationViewModel converterToViewModel;

    @Autowired
    public NewApplicationsController(
            ApplicationService applicationService,
            ApplicationSummaryToApplicationViewModel converterToViewModel) {
        this.applicationService = applicationService;
        this.converterToViewModel = converterToViewModel;
    }

    @GetMapping(URL)
    public String show(
            @RequestParam("searchBy") Optional<String> searchBy,
            @RequestParam("searchTerm") Optional<String> searchTerm,
            RedirectAttributes redirectAttributes,
            Model model) {

        List<ApplicationSummary> applications;

        if(searchTerm.isPresent() && searchBy.isPresent()) {
            model.addAttribute("searchTerm", searchTerm.get());
            model.addAttribute("searchBy", searchBy.get());
            redirectAttributes.addFlashAttribute("searchTerm", searchTerm.get());
            redirectAttributes.addFlashAttribute("searchBy", searchBy.get());
        }

        if(searchTerm.isPresent() && searchBy.get().equals("name") && searchTerm.isPresent()) {
            applications = applicationService.findApplicationByName(searchTerm.get());
        } else if(searchTerm.isPresent() && searchBy.get().equals("postcode") && searchTerm.isPresent()) {
            applications = applicationService.findApplicationByPostCode(searchTerm.get());
        } else {
            applications = applicationService.retrieve();
        }

        List<ApplicationViewModel> applicationsViewModel = applications
                .stream()
                .map(app -> converterToViewModel.convert(app))
                .collect(Collectors.toList());

        model.addAttribute("applications", applicationsViewModel);
        model.addAttribute("applicationCount", applicationsViewModel.size());

        return TEMPLATE;
    }
}
