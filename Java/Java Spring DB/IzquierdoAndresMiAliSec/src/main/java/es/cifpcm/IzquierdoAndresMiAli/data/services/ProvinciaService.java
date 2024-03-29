package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.ProvinciasRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Provincia;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProvinciaService {

    private final ProvinciasRepository provinciasRepository;

    public ProvinciaService(ProvinciasRepository provinciasRepository) {
        this.provinciasRepository = provinciasRepository;
    }

    public Provincia getById(Integer id) {
        Provincia original = requireOne(id);
        return toDTO(original);
    }

    public List<Provincia> getAllProvincias(){
        return provinciasRepository.findAll();
    }


    private Provincia toDTO(Provincia original) {
        Provincia bean = new Provincia();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Provincia requireOne(Integer id) {
        return provinciasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
