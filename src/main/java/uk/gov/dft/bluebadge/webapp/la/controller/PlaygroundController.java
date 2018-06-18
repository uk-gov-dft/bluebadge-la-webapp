package uk.gov.dft.bluebadge.webapp.la.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.dft.bluebadge.model.usermanagement.Error;
import uk.gov.dft.bluebadge.model.usermanagement.ErrorErrors;
import uk.gov.dft.bluebadge.webapp.la.controller.request.ErrorListOrderInterface;
import uk.gov.dft.bluebadge.webapp.la.controller.utils.ErrorHandlingUtils;

import java.util.ArrayList;
import java.util.List;

class User implements ErrorListOrderInterface {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImmutableList<String> errorListOrder() {
        return ImmutableList.of(
            "email",
            "password"
        );
    }
}

@Controller
public class PlaygroundController {

    private static String[] DISPLAY_ORDER = { " ***REMOVED*** };

    public static final String URL = "/playground";
    public static final String TEMPLATE = "playground";

    @GetMapping(URL)
    public String get(@ModelAttribute User user, BindingResult errors) {

        return TEMPLATE;
    }

    @PostMapping(URL)
    public String post(@ModelAttribute User user, BindingResult bindingResult, Model model){

        // Block simulates getting errors from the client
        Error serverError = new Error();

        ErrorErrors emailError = new ErrorErrors();
        emailError.setField("email");
        emailError.setMessage("NotNull.user.emailAddress");

        ErrorErrors passwordError = new ErrorErrors();
         ***REMOVED***);
         ***REMOVED***);

        List<ErrorErrors> errors = new ArrayList<ErrorErrors>();
        errors.add(passwordError);
        errors.add(emailError);
        serverError.setErrors(errors);

        ErrorHandlingUtils.handleError(
                serverError,
                TEMPLATE,
                TEMPLATE,
                bindingResult,
                model,
                user.errorListOrder());

        try {
            System.out.println((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(bindingResult.getFieldErrors()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return TEMPLATE;
    }
}
