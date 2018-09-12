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
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationSummaryToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationSummaryViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
public class NewApplicationsController {

  public static final String URL = "/new-applications";

  public static final String TEMPLATE = "new-applications/index";

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
      Model model) {

    saveParams(searchBy, searchTerm, model);

    List<ApplicationSummary> applications =
        searchTerm
            .map(
                term -> {
                  if (!term.isEmpty()) {
                    if (searchBy.get().equals("name")) {
                      return applicationService.findNewApplicationsByName(term);
                    }
                    return applicationService.findNewApplicationsByPostCode(term);
                  } else {
                    return applicationService.findAllNew();
                  }
                })
            .orElse(applicationService.findAllNew());

    List<ApplicationSummaryViewModel> applicationsView =
        applications
            .stream()
            .map(app -> converterToViewModel.convert(app))
            .collect(Collectors.toList());

    model.addAttribute("applications", applicationsView);
    // it's wrong thing to do, but for the sake of speeding delivery time we're going to call
    // service twice to get amount of 'new' applications without filters applied
    // TODO: should be revisited to proper solution
    model.addAttribute("applicationCount", applicationService.findAllNew().size());
    if (searchTerm.isPresent() && !searchTerm.get().isEmpty()) {
      model.addAttribute("filteredApplicationCount", applicationsView.size());
    }

    return TEMPLATE;
  }

  private void saveParams(Optional<String> searchBy, Optional<String> searchTerm, Model model) {

    searchBy.ifPresent(
        s -> {
          model.addAttribute("searchBy", s);
        });

    searchTerm.ifPresent(
        s -> {
          model.addAttribute("searchTerm", s);
        });

    model.addAttribute("searchByOptions", getSearchByOptions());
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
}
