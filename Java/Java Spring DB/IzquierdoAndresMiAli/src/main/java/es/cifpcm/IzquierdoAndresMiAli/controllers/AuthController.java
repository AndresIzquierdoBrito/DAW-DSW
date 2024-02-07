package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "login";
    }

    @GetMapping("/register")
    public String registerView() {
        return "usuario/create";
    }

    @PostMapping("/register")
    public String registerPost() {
        return "usuario/create";
    }
}
