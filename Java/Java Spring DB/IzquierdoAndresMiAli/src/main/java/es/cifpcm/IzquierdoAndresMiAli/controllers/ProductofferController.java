package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.ProductofferService;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import es.cifpcm.IzquierdoAndresMiAli.models.ProductofferDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/productoffer")
public class ProductofferController {

    @Autowired
    private ProductofferService productofferService;

    @PostMapping
    public String save(@Valid @RequestBody Productoffer productoffer) {
        return productofferService.save(productoffer).toString();
    }

    @GetMapping
    public List<Productoffer> getAllProducts() {
        return productofferService.getAllProductOffers();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        productofferService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Productoffer productoffer) {
        productofferService.update(id, productoffer);
    }

    @GetMapping("/{id}")
    public Productoffer getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return productofferService.getById(id);
    }

//    @GetMapping
//    public Page<ProductofferDTO> query(@Valid Productoffer productoffer) {
//        return productofferService.query(productoffer);
//    }
}
