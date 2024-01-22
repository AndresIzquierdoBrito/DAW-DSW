package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.MunicipioService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProductofferService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProvinciaService;
import es.cifpcm.IzquierdoAndresMiAli.models.Municipio;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import es.cifpcm.IzquierdoAndresMiAli.models.Provincia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Validated
@Controller
@RequestMapping("/productos")
public class ProductofferController {

    private final ProductofferService productService;
    private final ProvinciaService provinciaService;

    private final MunicipioService municipioService;
    @Autowired
    public ProductofferController(ProductofferService productservice, ProvinciaService provinciaService, MunicipioService municipioService){
        this.productService = productservice;
        this.provinciaService = provinciaService;
        this.municipioService = municipioService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Productoffer> products = productService.getAllProductOffers();
        List<Provincia> allProvincias = provinciaService.getAllProvincias();

        model.addAttribute("products", products);
        model.addAttribute("allProvincias", allProvincias);

        return "producto";
    }

    @GetMapping("/update")
    public String updateProductList(Model model,
                                    @RequestParam(required = false, value = "provinciaId") Integer provinciaId,
                                    @RequestParam(required = false) Integer municipioId) {
        List<Provincia> allProvincias = provinciaService.getAllProvincias();
        List<Municipio> municipiosByProvincia = null;
        List<Productoffer> products = null;

        if (provinciaId != null) {
            municipiosByProvincia = municipioService.getMunicipiosByProvinciaId(provinciaId);
            products = productService.getAllProductOffers();
        }
        else if (municipioId != null) {
            municipiosByProvincia = municipioService.getMunicipiosByProvinciaId(provinciaId);
            //products = productService.getProductsByMunicipio(municipioId);
            products = productService.getAllProductOffers();
        } else {
            products = productService.getAllProductOffers();
        }

        model.addAttribute("allProvincias", allProvincias);
        model.addAttribute("municipiosByProvincia", municipiosByProvincia);
        model.addAttribute("products", products);
        return "producto";
    }

    //@PostMapping
    //public String save(@Valid @RequestBody Productoffer productoffer) {
    //    return productService.save(productoffer).toString();
    //}

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Productoffer productoffer) {
        productService.update(id, productoffer);
    }

    @GetMapping("/{id}")
    public Productoffer getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return productService.getById(id);

    }

//    @GetMapping
//    public Page<ProductofferDTO> query(@Valid Productoffer productoffer) {
//        return productofferService.query(productoffer);
//    }
}
