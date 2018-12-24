package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.gov.dft.bluebadge.common.api.model.PagingInfo;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummary;
import uk.gov.dft.bluebadge.webapp.la.client.applications.model.ApplicationSummaryResponse;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.servicetoviewmodel.ApplicationSummaryToApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.controller.request.FindApplicationFormRequest;
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
  public String show(@ModelAttribute @Valid FindApplicationFormRequest formRequest, Model model) {

    ApplicationSummaryResponse result = null;
    if (formRequest.getSearchTerm().isPresent()) {
      String searchBy = formRequest.getSearchBy().map(w -> w).orElse("");
      switch (searchBy) {
        case "name":
          result =
              applicationService.findNewApplicationsByName(formRequest.getSearchTerm().get(), formRequest.getPagingInfo());
          break;
        case "postcode":
          result =
              applicationService.findNewApplicationsByPostCode(
            		  formRequest.getSearchTerm().get(), formRequest.getPagingInfo());
          break;
        default:
          throw new IllegalArgumentException("Unsupported search by value:" + searchBy);
      }
    } else {
      result = applicationService.findAllNew(formRequest.getPagingInfo());
    }

    List<ApplicationSummary> applications = result.getData();
    List<ApplicationSummaryViewModel> applicationsView =
        applications
            .stream()
            .map(app -> converterToViewModel.convert(app))
            .collect(Collectors.toList());

    setupModel(model, formRequest, result.getPagingInfo(), applicationsView);

    return TEMPLATE;
  }

  private Long getAllNewApplicationSize() {
    PagingInfo pagingInfo = new PagingInfo();
    pagingInfo.setPageSize(1);
    pagingInfo.setPageNum(1);

    ApplicationSummaryResponse allNew = applicationService.findAllNew(pagingInfo);

    return allNew.getPagingInfo().getTotal();
  }

  private void setupModel(
      Model model,
      FindApplicationFormRequest formRequest,
      PagingInfo info,
      List<ApplicationSummaryViewModel> applicationsView) {
    formRequest.getSearchBy().ifPresent(s -> model.addAttribute("searchBy", s));
    formRequest.getSearchTerm().ifPresent(s -> model.addAttribute("searchTerm", s));

    model.addAttribute("searchByOptions", getSearchByOptions());
    model.addAttribute("pagingInfo", info);

    model.addAttribute("applicationCount", getAllNewApplicationSize());

    model.addAttribute("applications", applicationsView);
    if (!formRequest.isSearchTermEmpty()) {
      model.addAttribute("filteredApplicationCount", applicationsView.size());
    }
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
