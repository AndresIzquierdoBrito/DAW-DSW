package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.MunicipiosRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Municipio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MunicipioService {

    @Autowired
    private MunicipiosRepository municipiosRepository;

    public Municipio getById(Integer id) {
        Municipio original = requireOne(id);
        return toDTO(original);
    }

    public List<Municipio> getAllMunicipios() {
        return municipiosRepository.findAll();
    }
    public List<Municipio> getMunicipiosByProvinciaId(Integer id){
        return municipiosRepository.findAll();
    }

    public Page<Municipio> query(Municipio municipio) {
        throw new UnsupportedOperationException();
    }

    private Municipio toDTO(Municipio original) {
        Municipio bean = new Municipio();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Municipio requireOne(Integer id) {
        return municipiosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
