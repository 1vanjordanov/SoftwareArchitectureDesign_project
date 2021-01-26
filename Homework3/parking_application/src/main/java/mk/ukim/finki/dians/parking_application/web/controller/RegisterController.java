package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.dians.parking_application.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.dians.parking_application.model.exceptions.UsernameExistsException;
import mk.ukim.finki.dians.parking_application.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";

    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           Model model) {
        try {
            this.userService.register(username, password, repeatedPassword, name, surname);
            return "redirect:/login";
        }
        catch (PasswordsDoNotMatchException | UsernameExistsException | InvalidArgumentsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("bodyContent", "register");
            return "master-template";
        }
    }
}
