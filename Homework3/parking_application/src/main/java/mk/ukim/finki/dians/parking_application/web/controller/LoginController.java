package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidUserCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(Model model) {

        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @GetMapping("/error")
    public String getLoginPage(Model model, InvalidUserCredentialsException exception) {

        model.addAttribute("hasError", true);
        model.addAttribute("error", exception.getMessage());
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

}
