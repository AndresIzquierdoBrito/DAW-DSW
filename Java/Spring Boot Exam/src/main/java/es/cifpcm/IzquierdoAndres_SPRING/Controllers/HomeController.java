package es.cifpcm.IzquierdoAndres_SPRING.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String viewHomePage() { return "index_izquierdo"; }

    @GetMapping("/list")
    public String page() { return "list_albums"; }

}