package es.cifpcm.IzquierdoAndresFarmaciasWeb.controllers;

import es.cifpcm.IzquierdoAndresFarmaciasWeb.data.PersitanceImplFarm;
import es.cifpcm.IzquierdoAndresFarmaciasWeb.models.Farmacia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FarmaciaController {

    @Autowired
    private PersitanceImplFarm PersitanceImplFarm;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/farmacias")
    public String farmaciasPage(Model model) {
        ArrayList<Farmacia> farmacias = PersitanceImplFarm.list();
        model.addAttribute("farmacias", farmacias);
        return "farmacias";
    }

    @PostMapping("/farmacias")
    public String buscarFarmaciasCercanas(@RequestParam String nombre, Model model) {
        ArrayList<Farmacia> farmaciasCercanas = PersitanceImplFarm.findClosestFarmacias(nombre);
        model.addAttribute("farmaciaBusqueda", nombre);
        model.addAttribute("farmacias", farmaciasCercanas);
        model.addAttribute("isSearchResult", true);
        return "farmacias";
    }
}
