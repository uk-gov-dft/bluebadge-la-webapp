package uk.gov.dft.bluebadge.webapp.la.controller;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestControllerImpl {

  @GetMapping("/test/endpoint1")
  public String endpoint1(Model model) {
    return "test/endpoint1";
  }

  @GetMapping("/test/endpoint2")
  public String endpoint2(Model model) {
    return "test/endpoint2";
  }

  @GetMapping("/test/endpoint3")
  public String endpoint3(Model model) {
    return "test/endpoint3";
  }

  @GetMapping("/test/endpoint4")
  public String endpoint4(Model model) {
    return "test/endpoint4";
  }

  // Example: http://localhost:8080/test/smart-endpoint?id=11111
  @GetMapping("/test/smart-endpoint")
  public String smartEndpoint(@PathParam("id") String id, Model model) {
    model.addAttribute("id", id);
    return "test/smart-endpoint";
  }
}
