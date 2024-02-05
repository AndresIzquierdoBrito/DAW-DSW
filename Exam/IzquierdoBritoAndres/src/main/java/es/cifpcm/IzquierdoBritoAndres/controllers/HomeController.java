package es.cifpcm.IzquierdoBritoAndres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String viewHomePage() { return "index"; }

    @GetMapping("/list")
    public String page() { return "list"; }

}