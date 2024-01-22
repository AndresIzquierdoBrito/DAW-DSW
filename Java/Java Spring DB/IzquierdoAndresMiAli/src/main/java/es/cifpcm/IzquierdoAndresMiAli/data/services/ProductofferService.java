package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.ProductofferRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import es.cifpcm.IzquierdoAndresMiAli.models.ProductofferDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductofferService {

    @Autowired
    private ProductofferRepository productofferRepository;

    public Integer save(Productoffer vO) {
        Productoffer bean = new Productoffer();
        BeanUtils.copyProperties(vO, bean);
        bean = productofferRepository.save(bean);
        return bean.getProductId();
    }

    public List<Productoffer> getAllProductOffers(){
        return productofferRepository.findAll();
    }

    public List<Productoffer> getProductsByMunicipio(Integer municipioId){
        return productofferRepository.findAll();
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

    public Page<ProductofferDTO> query(Productoffer productoffer) {
        throw new UnsupportedOperationException();
    }

    private Productoffer toDTO(Productoffer original) {
        Productoffer bean = new Productoffer();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Productoffer requireOne(Integer id) {
        return productofferRepository.findByProductId(id);
    }
}
