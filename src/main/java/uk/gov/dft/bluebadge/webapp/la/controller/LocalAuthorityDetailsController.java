package uk.gov.dft.bluebadge.webapp.la.controller;

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.common.api.model.CommonResponse;
import uk.gov.dft.bluebadge.common.service.enums.Nation;
import uk.gov.dft.bluebadge.webapp.la.client.common.BadRequestException;
import uk.gov.dft.bluebadge.webapp.la.client.common.NotFoundException;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.LocalAuthorityRefData;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoservice.LocalAuthorityDetailsFormRequestToLocalAuthority;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.requesttoviewmodel.LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.LocalAuthorityDetailsFormRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.Options;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ErrorViewModel;
import uk.gov.dft.bluebadge.webapp.la.service.enums.ClockType;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

@Slf4j
@Controller
public class LocalAuthorityDetailsController {

  public static final String URL = "/manage-local-authorities/local-authority-details/{shortCode}";

  private static final String TEMPLATE = "manage-local-authorities/local-authority-details";

  private static final String REDIRECT_URL_MANAGE_LOCAL_AUTHORITIES =
      "redirect:" + ManageLocalAuthoritiesController.URL;

  private static final String MODEL_FORM_REQUEST = "formRequest";

  private static final BigDecimal MIN_COST = new BigDecimal("1.00");
  private static final BigDecimal MAX_COST = new BigDecimal("999.99");
  public static final String BADGE_COST_PARAM = "badgeCost";

  private ReferenceDataService referenceDataService;
  private LocalAuthorityDetailsFormRequestToLocalAuthority toLocalAuthority;
  private LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest toFormRequest;

  @Autowired
  public LocalAuthorityDetailsController(
      ReferenceDataService referenceDataService,
      LocalAuthorityDetailsFormRequestToLocalAuthority toLocalAuthority,
      LocalAuthorityMetaDataToLocalAuthorityDetailsFormRequest toFormRequest) {
    this.referenceDataService = referenceDataService;
    this.toLocalAuthority = toLocalAuthority;
    this.toFormRequest = toFormRequest;
  }

  @GetMapping(URL)
  public String show(
      @PathVariable("shortCode") String shortCode,
      @ModelAttribute(MODEL_FORM_REQUEST) final LocalAuthorityDetailsFormRequest formRequest,
      Model model) {
    Optional<ReferenceData> localAuthorityMaybe =
        referenceDataService.retrieveBadgeReferenceDataItem(RefDataGroupEnum.LA, shortCode);

    LocalAuthorityRefData localAuthority =
        (LocalAuthorityRefData)
            localAuthorityMaybe.orElseThrow(() -> new NotFoundException(new CommonResponse()));

    Optional<LocalAuthorityRefData.LocalAuthorityMetaData> metaDataMaybe =
        localAuthority.getLocalAuthorityMetaData();
    metaDataMaybe.ifPresent(
        metadata -> {
          LocalAuthorityDetailsFormRequest localAuthorityDetailsFormRequest =
              toFormRequest.convert(metadata);
          localAuthorityDetailsFormRequest.setDescription(localAuthority.getDescription());
          model.addAttribute(MODEL_FORM_REQUEST, localAuthorityDetailsFormRequest);
        });

    model.addAttribute("shortCode", shortCode);
    return TEMPLATE;
  }

  @ModelAttribute("clockTypeOptions")
  public List<ReferenceData> clockTypeOptions() {
    ReferenceData standardOption =
        ReferenceData.builder()
            .description(ClockType.STANDARD.getCode())
            .shortCode(ClockType.STANDARD.getCode())
            .build();
    ReferenceData walletOption =
        ReferenceData.builder()
            .description(ClockType.WALLET.getCode())
            .shortCode(ClockType.WALLET.getCode())
            .build();
    return Lists.newArrayList(standardOption, walletOption);
  }

  @ModelAttribute("nationOptions")
  public List<ReferenceData> nationOptions() {
    return Options.optionsFromEnum("NATION", Nation.class);
  }

  @PostMapping(URL)
  public String submit(
      @PathVariable("shortCode") String shortCode,
      @Valid @ModelAttribute(MODEL_FORM_REQUEST) final LocalAuthorityDetailsFormRequest formRequest,
      BindingResult bindingResult,
      Model model) {
    log.info("Submit local authority details");
    model.addAttribute("errorSummary", new ErrorViewModel());

    if (Boolean.TRUE.equals(formRequest.getPaymentsEnabled())) {
      String badgeCost = formRequest.getBadgeCost();
      if (StringUtils.isEmpty(badgeCost)) {
        bindingResult.rejectValue(BADGE_COST_PARAM, "NotNull.localAuthorityDetailPage.badgeCost");
      } else {
        String pattern = "^(\\d{1,3}+(?:[\\.]\\d{1,2})?)$";
        if (!badgeCost.matches(pattern)) {
          bindingResult.rejectValue(BADGE_COST_PARAM, "Range.localAuthorityDetailPage.badgeCost");
        } else {
          BigDecimal badgeCostBigDecimal = new BigDecimal(badgeCost);
          if (badgeCostBigDecimal.compareTo(MIN_COST) < 0
              || badgeCostBigDecimal.compareTo(MAX_COST) > 0) {
            bindingResult.rejectValue(BADGE_COST_PARAM, "Range.localAuthorityDetailPage.badgeCost");
          }
        }
      }
    }

    if (bindingResult.hasErrors()) {
      log.debug("Submit, have binding errors.");
      return TEMPLATE;
    } else {
      try {
        LocalAuthority localAuthority = toLocalAuthority.convert(formRequest);
        referenceDataService.updateLocalAuthority(shortCode, localAuthority);
      } catch (BadRequestException e) {
        log.debug("Submit, have binding errors.");
        ErrorHandlingUtils.bindBadRequestException(e, bindingResult, model);
        return TEMPLATE;
      }
      return REDIRECT_URL_MANAGE_LOCAL_AUTHORITIES;
    }
  }
}
