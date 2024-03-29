package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.MunicipioService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProductofferService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProvinciaService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.impl.ImageService;
import es.cifpcm.IzquierdoAndresMiAli.models.Municipio;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import es.cifpcm.IzquierdoAndresMiAli.models.Provincia;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller
@RequestMapping("/productos")
public class ProductofferController {

    public static ArrayList<Productoffer> carrito = new ArrayList<>();
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
        List<Productoffer> products;
        model.addAttribute("provincias", provincias);
        model.addAttribute("provinciaId", provinciaId);
        model.addAttribute("municipioId", municipioId);

        double total = carrito.stream().mapToDouble(Productoffer::getProductPrice).sum();
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
        if (product.getProductStock() == 0)
            return "redirect:/productos";
        carrito.add(product);
        product.setProductStock(product.getProductStock() - 1);
        return "redirect:/productos";
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('administradores') or hasRole('gestores')")
    public String displayUploadForm(Model model) {
        List<Municipio> municipios = municipioService.getAllMunicipios();
        model.addAttribute("product", new Productoffer());
        model.addAttribute("municipios", municipios);
        return "create";
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('administradores') or hasRole('gestores')")
    public String uploadProduct(@Validated @ModelAttribute("product") Productoffer product,
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
    @PreAuthorize("hasRole('administradores') or hasRole('gestores')")
    public String delete(@Valid @NotNull @PathVariable("id") Integer id) {
        productService.delete(id);
        return "redirect:/productos";
    }

    @GetMapping("/{id}/edit")
    public String updateProduct(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Productoffer productById = productService.getById(id);
        List<Municipio> municipios = municipioService.getAllMunicipios();
        model.addAttribute("product", productById);
        model.addAttribute("municipios", municipios);

        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAlbum(@Valid @NotNull @PathVariable("id") Integer id,
                              @Validated @ModelAttribute("product") Productoffer product,
                              @RequestParam("image") MultipartFile file,
                              BindingResult result)
    {
        if (result.hasErrors()) {
            return "/" + id + "/edit";
        }
        if (!file.getOriginalFilename().isBlank()) {
            imageService.saveImage(file);
            product.setProductPicture(file.getOriginalFilename());
        }
        else{
            product.setProductPicture(productService.getById(id).getProductPicture());
        }
        productService.update(id, product);
        return "redirect:/productos";
        //return "redirect:/album/" + id;
    }

    @GetMapping("/{id}")
    public String getById(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Productoffer product = productService.getById(id);
        Municipio municipio = municipioService.getMunicipioById(product.getIdMunicipio());
        model.addAttribute("product", product);
        model.addAttribute("municipio", municipio);

        return "details";
    }
}


