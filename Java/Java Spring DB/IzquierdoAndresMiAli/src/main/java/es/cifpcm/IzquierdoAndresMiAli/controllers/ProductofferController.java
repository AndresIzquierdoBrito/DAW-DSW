package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.MunicipioService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.PedidoService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Validated
@Controller
@RequestMapping("/productos")
public class ProductofferController {

    public static ArrayList<Productoffer> carrito = new ArrayList<Productoffer>();
    private final ProductofferService productService;
    private final ProvinciaService provinciaService;
    private final MunicipioService municipioService;
    private final ImageService imageService;
    private final PedidoService pedidoService;
    @Autowired
    public ProductofferController(ProductofferService productservice,
                                  ProvinciaService provinciaService,
                                  MunicipioService municipioService,
                                  ImageService imageService,
                                  PedidoService pedidoService){
        this.productService = productservice;
        this.provinciaService = provinciaService;
        this.municipioService = municipioService;
        this.imageService = imageService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String getProducts(@RequestParam(value="provinciaId", required = false) Short provinciaId,
                              @RequestParam(value="municipioId", required = false) Short municipioId,
                              Model model) {
        List<Provincia> provincias = provinciaService.getAllProvincias();
        List<Productoffer> products;
        model.addAttribute("provincias", provincias);
        model.addAttribute("provinciaId", provinciaId);
        model.addAttribute("municipioId", municipioId);

        double total = carrito.stream().mapToDouble(product -> product.getProductPrice()).sum();
        model.addAttribute("total", total);
        model.addAttribute("carrito", carrito);

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

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Integer productId) {
        Productoffer product = productService.getById(productId);
        carrito.add(product);
        return "redirect:/productos";
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
                                @Validated @ModelAttribute("product") Productoffer product,
                                @RequestParam("image") MultipartFile file,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        imageService.saveImage(file);
        product.setProductPicture(file.getOriginalFilename());
        productService.save(product);

        return "redirect:/productos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@Valid @NotNull @PathVariable("id") Integer id) {
        productService.delete(id);
        return "redirect:/productos";
    }
}


