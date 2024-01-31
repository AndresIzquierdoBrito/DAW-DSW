package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MunicipiosRepository extends JpaRepository<Municipio, Integer>, JpaSpecificationExecutor<Municipio> {

    List<Municipio> findByIdProvincia(Short idProvincia);
}