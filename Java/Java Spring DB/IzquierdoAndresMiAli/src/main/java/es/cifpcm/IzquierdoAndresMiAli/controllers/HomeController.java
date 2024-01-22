package es.cifpcm.IzquierdoAndresMiAli.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String viewHomePage() { return "inicio"; }

    @GetMapping("/login")
    public String viewLoginPage() { return "login"; }

    @GetMapping("/user/create")
    public String viewRegister() { return "usuario/create"; }
}
