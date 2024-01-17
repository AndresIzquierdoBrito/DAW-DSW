package es.cifpcm.AndresNauzetFarmaciasWeb.controllers;

import es.cifpcm.AndresNauzetFarmaciasWeb.data.ImplementsInterface;
import es.cifpcm.AndresNauzetFarmaciasWeb.models.Farmacia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class FarmaciaController {

    @Autowired
    private ImplementsInterface PersitanceImplFarm;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/farmacias")
    public String farmaciasPage(Model model) {
        List<Farmacia> farmacias = PersitanceImplFarm.list();
        model.addAttribute("farmacias", farmacias);
        return "farmacias";
    }

    @PostMapping("/farmacias")
    public String buscarFarmaciasCercanas(@RequestParam String nombre, Model model) {
        List<Farmacia> farmaciasCercanas = null;
        Farmacia farmaciaBusqueda = PersitanceImplFarm.searchName(nombre);

        if (farmaciaBusqueda == null){
            model.addAttribute("notFound", true);
            model.addAttribute("farmaciaBusqueda", nombre.toUpperCase());
            return "farmacias";
        }

        farmaciasCercanas = PersitanceImplFarm.showNear(farmaciaBusqueda);
        model.addAttribute("farmaciaBusqueda", nombre);
        model.addAttribute("farmacias", farmaciasCercanas);
        model.addAttribute("isSearchResult", true);
        return "farmacias";
    }


    @GetMapping("/addFarmacia")
    public String GetFarmacia(Model model){
        Farmacia farmacia=new Farmacia();
        model.addAttribute("farmacia",farmacia);
        return "addFarmacia";
    }
    @PostMapping("/addFarmacia")
    public String addFarmaciaForm(@ModelAttribute("farmacia") Farmacia farmacia) {
        PersitanceImplFarm.add(farmacia);
        return "redirect:farmacias";
    }

}
