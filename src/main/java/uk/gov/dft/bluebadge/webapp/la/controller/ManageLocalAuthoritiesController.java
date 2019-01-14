package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Controller
public class ManageLocalAuthoritiesController {

  public static final String URL = "manage-local-authorities";

  public static final String TEMPLATE = "manage-local-authorities";

  private ReferenceDataService referenceDataService;

  @Autowired
  public ManageLocalAuthoritiesController(ReferenceDataService referenceDataService) {
    this.referenceDataService = referenceDataService;
  }

  @GetMapping(URL)
  public String show(Model model) {
    List<ReferenceData> allLocalAuthorities =
        referenceDataService
            .retrieveBadgeReferenceDataList(RefDataGroupEnum.LA)
            .stream()
            .sorted(Comparator.comparing(ReferenceData::getDescription))
            .collect(Collectors.toList());
    model.addAttribute("localAuthorities", allLocalAuthorities);
    return TEMPLATE;
  }
}
