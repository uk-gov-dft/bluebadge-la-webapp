package uk.gov.dft.bluebadge.webapp.la.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.dft.bluebadge.webapp.la.converter.ApplicationConverterImpl;
import uk.gov.dft.bluebadge.webapp.la.model.Application;
import uk.gov.dft.bluebadge.webapp.la.service.ApplicationService;

@Controller
public class ApplicationControllerImpl implements ApplicationController {

  @Autowired ApplicationService service;

  @Autowired ApplicationConverterImpl converter;

  @GetMapping("/applications/{id}/show")
  public String show(@PathVariable("id") Long id, Model model) {
    Optional<Application> application = service.findById((id));
    if (application.isPresent()) {
      model.addAttribute("application", converter.toApplicationViewModel(application.get()));
      return "applications/show";
    } else {
      model.addAttribute("id", id);
      return "applications/notFound";
    }
  }

  @GetMapping("/applications/showAll")
  public String showAll(Model model) {
    List<Application> applications = service.findAll();
    model.addAttribute("applications", converter.toApplicationViewModel(applications));
    return "applications/showAll";
  }

  @GetMapping("/applications/showCreate")
  public String showCreate(Model model) {
    return "applications/showCreate";
  }

  @GetMapping("/applications/create")
  public String create(@ModelAttribute ApplicationCreateRequest application) {
    if (service.create(converter.toApplication(application)) < 1) {
      return "notCreated";
    } else {
      return "redirect:/applications/showAll";
    }
  }

  @GetMapping("/applications/{id}/showUpdate")
  public String showUpdate(@PathVariable("id") Long id, Model model) {
    Optional<Application> application = service.findById((id));
    if (application.isPresent()) {
      model.addAttribute("application", converter.toApplicationViewModel(application.get()));
      return "applications/showUpdate";
    } else {
      model.addAttribute("id", id);
      return "applications/notFound";
    }
  }

  @GetMapping("/applications/update")
  public String update(@ModelAttribute ApplicationUpdateRequest application) {
    if (service.update(converter.toApplication(application)) < 1) {
      return "notUpdated";
    } else {
      return "redirect:/applications/showAll";
    }
  }

  @GetMapping("/applications/{id}/delete")
  public String delete(@PathVariable("id") Long id, Model model) {
    if (service.delete(id) < 1) {
      return "notDeleted";
    } else {
      return "redirect:/applications/showAll";
    }
  }

  protected void setConverter(ApplicationConverterImpl converter) {
    this.converter = converter;
  }
}
