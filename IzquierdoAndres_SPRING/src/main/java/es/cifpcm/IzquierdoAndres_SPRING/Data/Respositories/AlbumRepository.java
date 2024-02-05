package es.cifpcm.IzquierdoAndres_SPRING.Data.Respositories;

import es.cifpcm.IzquierdoAndres_SPRING.Models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer>, JpaSpecificationExecutor<Album> {

    @Query("select a from Album a order by a.albumId DESC")
    List<Album> getAlbumsSorted();
}