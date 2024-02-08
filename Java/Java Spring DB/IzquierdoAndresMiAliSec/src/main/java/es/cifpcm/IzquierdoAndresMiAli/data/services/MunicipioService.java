package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.MunicipiosRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Municipio;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MunicipioService {

    private final MunicipiosRepository municipiosRepository;

    public MunicipioService(MunicipiosRepository municipiosRepository) {
        this.municipiosRepository = municipiosRepository;
    }

    public List<Municipio> getAllMunicipios() {
        return municipiosRepository.findAll();
    }

    public List<Municipio> getMunicipiosByProvinciaId(Short id){
        return municipiosRepository.findByIdProvincia(id);
    }

    public Municipio getMunicipioById(Integer id) {
        return requireOne(id);
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
