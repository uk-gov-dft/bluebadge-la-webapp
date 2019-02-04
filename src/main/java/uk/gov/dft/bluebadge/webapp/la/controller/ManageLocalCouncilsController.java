package uk.gov.dft.bluebadge.webapp.la.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageLocalCouncilsController extends BaseController{

  public static final String URL = "/manage-local-councils";

  private static final String TEMPLATE = "manage-local-councils";

  private ReferenceDataService referenceDataService;

  @Autowired
  public ManageLocalCouncilsController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(Model model) {
    List<ReferenceData> localCouncils =
        referenceDataService
            .retrieveBadgeReferenceDataList(RefDataGroupEnum.LC)
            .stream()
            .sorted(Comparator.comparing(ReferenceData::getDescription))
            .collect(Collectors.toList());
    model.addAttribute("councils", localCouncils);
    return TEMPLATE;
  }

  /*
  @GetMapping
  public String updateCouncil(@PathVariable("shortCode") String shortCode){
    return "redirect:" +
  }
  */
}
