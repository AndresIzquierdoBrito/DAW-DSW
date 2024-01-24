package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.MunicipioService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProductofferService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProvinciaService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.impl.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Validated
@Controller
@RequestMapping("/productos")
public class ProductofferController {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private final ProductofferService productService;
    private final ProvinciaService provinciaService;
    private final MunicipioService municipioService;
    private final ImageService imageService;
    @Autowired
    public ProductofferController(ProductofferService productservice,
                                  ProvinciaService provinciaService,
                                  MunicipioService municipioService,
                                  ImageService imageService){
        this.productService = productservice;
        this.provinciaService = provinciaService;
        this.municipioService = municipioService;
        this.imageService = imageService;
    }

    @GetMapping
    public String getProducts(@RequestParam(value="provinciaId", required = false) Short provinciaId,
                              @RequestParam(value="municipioId", required = false) Short municipioId,
                              Model model) {
        List<Provincia> provincias = provinciaService.getAllProvincias();
        List<Productoffer> products = null;
        model.addAttribute("provincias", provincias);
        model.addAttribute("provinciaId", provinciaId);
        model.addAttribute("municipioId", municipioId);

        if(provinciaId != null){
            List<Municipio> municipios = municipioService.getMunicipiosByProvinciaId(provinciaId);
            Provincia selectedProvincia = provinciaService.getById(Integer.valueOf(provinciaId));
            model.addAttribute("municipios", municipios);
            model.addAttribute("selectedProvincia", selectedProvincia);
            products = (municipioId == null) ? productService.getProdutcsByProvincia(provinciaId) : productService.getProductsByMunicipio(Integer.valueOf(municipioId));
        }
        else
            products = productService.getAllProductOffers();

        model.addAttribute("products", products);
        return "producto";
    }

    @GetMapping("/crear")
    public String displayUploadForm(Model model) {
        List<Municipio> municipios = municipioService.getAllMunicipios();
        model.addAttribute("product", new Productoffer());
        model.addAttribute("municipios", municipios);
        return "create";
    }

    @PostMapping("/crear")
    public String uploadProduct(Model model,
                              @ModelAttribute("product") Productoffer product,
                              @RequestParam("image") MultipartFile file) throws IOException {
        imageService.saveImage(file);
        product.setProductPicture(file.getOriginalFilename());
        productService.save(product);

        return "redirect:/";
    }
}


