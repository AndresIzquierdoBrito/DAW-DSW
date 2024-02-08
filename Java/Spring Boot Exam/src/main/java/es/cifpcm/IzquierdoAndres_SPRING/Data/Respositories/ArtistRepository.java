package es.cifpcm.IzquierdoAndres_SPRING.Data.Respositories;

import es.cifpcm.IzquierdoAndres_SPRING.Models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist> {

    @Query("select a from Artist a order by a.artistId DESC")
    List<Artist> findByOrderByArtistIdDesc();

    @Query("select a from Artist a order by a.name")
    List<Artist> findByOrderByNameAsc();
}