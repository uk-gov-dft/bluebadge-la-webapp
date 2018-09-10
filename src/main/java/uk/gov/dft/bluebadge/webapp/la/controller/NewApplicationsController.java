package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
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
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
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

    saveParams(searchBy, searchTerm, redirectAttributes, model);

    applications =
        searchTerm
            .map(
                term -> {
                  if (searchBy.get().equals("name")) {
                    return applicationService.findApplicationByName(term);
                  }
                  return applicationService.findApplicationByPostCode(term);
                })
            .orElse(applicationService.retrieve());

    List<ApplicationViewModel> applicationsViewModel =
        applications
            .stream()
            .map(app -> converterToViewModel.convert(app))
            .collect(Collectors.toList());

    model.addAttribute("searchByOptions", getSearchByOptions());
    model.addAttribute("applications", applicationsViewModel);

    return TEMPLATE;
  }

  private List<ReferenceData> getSearchByOptions() {
    ReferenceData name = new ReferenceData();
    name.setShortCode("name");
    name.setDescription("Name");

    ReferenceData postcode = new ReferenceData();
    postcode.setShortCode("postcode");
    postcode.setDescription("Postcode");

    return Lists.newArrayList(name, postcode);
  }

  private void saveParams(
      Optional<String> searchBy,
      Optional<String> searchTerm,
      RedirectAttributes redirectAttributes,
      Model model) {

    searchBy.ifPresent(
        s -> {
          model.addAttribute("searchBy", s);
          //redirectAttributes.addFlashAttribute("searchBy", s);
        });

    searchTerm.ifPresent(
        s -> {
          model.addAttribute("searchTerm", s);
          //redirectAttributes.addFlashAttribute("searchTerm", s);
        });
  }
}
