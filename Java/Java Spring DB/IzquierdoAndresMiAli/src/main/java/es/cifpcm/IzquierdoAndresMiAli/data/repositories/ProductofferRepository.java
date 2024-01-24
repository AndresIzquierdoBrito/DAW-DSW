package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductofferRepository extends JpaRepository<Productoffer, Integer>, JpaSpecificationExecutor<Productoffer> {

    @Query("select p from Productoffer p where p.productId = ?1")
    Productoffer findByProductId(Integer productId);

    @Query("SELECT p FROM Productoffer p JOIN p.municipio m JOIN m.provincia pr WHERE pr.idProvincia = :provinciaId")
    List<Productoffer> findByProvinciaId(Short provinciaId);

    List<Productoffer> findByIdMunicipio(Integer idMunicipio);
}