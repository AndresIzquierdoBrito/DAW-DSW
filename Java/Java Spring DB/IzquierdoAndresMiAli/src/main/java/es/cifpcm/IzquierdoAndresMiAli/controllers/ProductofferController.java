package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.ProductofferService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.ProvinciaService;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
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
    @Autowired
    public ProductofferController(ProductofferService productservice, ProvinciaService provinciaService){
        this.productService = productservice;
        this.provinciaService = provinciaService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Productoffer> products = productService.getAllProductOffers();
        model.addAttribute("products", products);
        return "producto";
    }

    @PostMapping("/updateProductList")
    public String updateProductList(@RequestParam("state") Long stateId, @RequestParam("city") Long cityId, Model model) {
        // List<Product> products = productService.getProductsByStateAndCity(stateId, cityId);
        // model.addAttribute("products", products);
        return "productListView";
    }

    @PostMapping
    public String save(@Valid @RequestBody Productoffer productoffer) {
        return productService.save(productoffer).toString();
    }

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
