package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.User;
import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.dians.parking_application.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
