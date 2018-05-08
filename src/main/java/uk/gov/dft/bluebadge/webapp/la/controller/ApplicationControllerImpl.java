package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.Application2ApplicationViewModelConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationCreateRequest2ApplicationConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ApplicationUpdateRequest2ApplicationConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.controller.converter.ListConverter;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationCreateRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ApplicationUpdateRequest;
import uk.gov.dft.bluebadge.webapp.la.controller.viewmodel.ApplicationViewModel;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralControllerException;
import uk.gov.dft.bluebadge.webapp.la.exception.GeneralServiceException;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;
import uk.gov.dft.bluebadge.webapp.la.service.model.Application;

@Controller
public class ApplicationControllerImpl implements ApplicationController {

  public static final String MODEL_APPLICATION = "application";
  public static final String MODEL_APPLICATIONS = "applications";
  public static final String MODEL_ID = "id";

  public static final String TEMPLATE_NOT_CREATED = "notCreated";
  public static final String TEMPLATE_NOT_DELETED = "notDeleted";
  public static final String TEMPLATE_NOT_FOUND = "applications/notFound";
  public static final String TEMPLATE_NOT_UPDATED = "notUpdated";
  public static final String TEMPLATE_SHOW = "applications/show";
  public static final String TEMPLATE_SHOW_ALL = "applications/showAll";
  public static final String TEMPLATE_SHOW_CREATE = "applications/showCreate";
  public static final String TEMPLATE_SHOW_UPDATE = "applications/showUpdate";

  public static final String URL_CREATE = "/applications/create";
  public static final String URL_DELETE = "/applications/{id}/delete";
  public static final String URL_UPDATE = "/applications/update";
  public static final String URL_SHOW = "/applications/{id}/show";
  public static final String URL_SHOW_ALL = "/applications/showAll";
  public static final String URL_SHOW_CREATE = "/applications/showCreate";
  public static final String URL_SHOW_UPDATE = "/applications/{id}/showUpdate";

  public static final String PARAM_ID = "id";

  private ApplicationService service;

  private ApplicationCreateRequest2ApplicationConverterImpl createRequest2ApplicationConverter;

  private ApplicationUpdateRequest2ApplicationConverterImpl updateRequest2ApplicationConverter;

  private Application2ApplicationViewModelConverterImpl application2ViewModelConverter;

  private ListConverter<Application, ApplicationViewModel>
      applications2ApplicationViewModelConverter;

  @Autowired
  public ApplicationControllerImpl(
      ApplicationService service,
      ApplicationCreateRequest2ApplicationConverterImpl createRequest2ApplicationConverter,
      ApplicationUpdateRequest2ApplicationConverterImpl updateRequest2ApplicationConverter,
      Application2ApplicationViewModelConverterImpl application2ViewModelConverter,
      ListConverter<Application, ApplicationViewModel> applications2ApplicationViewModelConverter) {
    this.service = service;
    this.createRequest2ApplicationConverter = createRequest2ApplicationConverter;
    this.updateRequest2ApplicationConverter = updateRequest2ApplicationConverter;
    this.application2ViewModelConverter = application2ViewModelConverter;
    this.applications2ApplicationViewModelConverter = applications2ApplicationViewModelConverter;
  }

  @GetMapping(URL_SHOW)
  public String show(@PathVariable(PARAM_ID) Long id, Model model) {
    try {
      Optional<Application> application = service.findById((id));
      if (application.isPresent()) {
        model.addAttribute(
            MODEL_APPLICATION, application2ViewModelConverter.convert(application.get()));
        return TEMPLATE_SHOW;
      }
      model.addAttribute(MODEL_ID, id);
      return TEMPLATE_NOT_FOUND;
    } catch (GeneralServiceException ex) {
      throw new GeneralControllerException("There was a general controller exception", ex);
    }
  }

  @GetMapping(URL_SHOW_ALL)
  public String showAll(Model model) {
    List<Application> applications = service.findAll();
    model.addAttribute(
        MODEL_APPLICATIONS,
        applications2ApplicationViewModelConverter.convert(
            applications, application2ViewModelConverter));
    return TEMPLATE_SHOW_ALL;
  }

  @GetMapping(URL_SHOW_CREATE)
  public String showCreate(Model model) {
    return TEMPLATE_SHOW_CREATE;
  }

  @GetMapping(URL_CREATE)
  public String create(@ModelAttribute ApplicationCreateRequest createRequest) {
    if (service.create(createRequest2ApplicationConverter.convert(createRequest)) < 1) {
      return TEMPLATE_NOT_CREATED;
    }
    return "redirect:" + URL_SHOW_ALL;
  }

  @GetMapping(URL_SHOW_UPDATE)
  public String showUpdate(@PathVariable(PARAM_ID) Long id, Model model) {
    Optional<Application> application = service.findById((id));
    if (application.isPresent()) {
      model.addAttribute(
          MODEL_APPLICATION, application2ViewModelConverter.convert(application.get()));
      return TEMPLATE_SHOW_UPDATE;
    }
    model.addAttribute(MODEL_ID, id);
    return TEMPLATE_NOT_FOUND;
  }

  @GetMapping(URL_UPDATE)
  public String update(@ModelAttribute ApplicationUpdateRequest updateRequest) {
    if (service.update(updateRequest2ApplicationConverter.convert(updateRequest)) < 1) {
      return TEMPLATE_NOT_UPDATED;
    }
    return "redirect:" + URL_SHOW_ALL;
  }

  @GetMapping(URL_DELETE)
  public String delete(@PathVariable(PARAM_ID) Long id, Model model) {
    if (service.delete(id) < 1) {
      return TEMPLATE_NOT_DELETED;
    }
    return "redirect:" + URL_SHOW_ALL;
  }
}
