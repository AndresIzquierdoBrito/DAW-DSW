package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.ProductofferRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @Validated
public class ProductofferService {

    private final ProductofferRepository productofferRepository;

    public ProductofferService(ProductofferRepository productofferRepository) {
        this.productofferRepository = productofferRepository;
    }

    public void save(@Valid Productoffer vO) {
        Productoffer bean = new Productoffer();
        BeanUtils.copyProperties(vO, bean);
        productofferRepository.save(bean);

    }

    public List<Productoffer> getAllProductOffers(){
        return productofferRepository.findAll();
    }

    public List<Productoffer> getProductsByMunicipio(Integer municipioId){
        return productofferRepository.findByIdMunicipio(municipioId);
    }

    public List<Productoffer> getProdutcsByProvincia(Short municipioId){
        return productofferRepository.findByProvinciaId(municipioId);
    }

    public void delete(Integer id) {
        productofferRepository.deleteById(id);
    }

    public void update(Integer id, Productoffer productoffer) {
        Productoffer bean = requireOne(id);
        BeanUtils.copyProperties(productoffer, bean);
        productofferRepository.save(bean);
    }

    public Productoffer getById(Integer id) {
        return requireOne(id);
    }

    private Productoffer requireOne(Integer id) {
        return productofferRepository.findByProductId(id);
    }
}
