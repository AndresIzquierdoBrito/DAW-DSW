package es.cifpcm.IzquierdoAndres_SPRING.Data.Services;

import es.cifpcm.IzquierdoAndres_SPRING.Data.Respositories.ArtistRepository;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Album;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Artist;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public List<Artist> getSortedArtists() {
        return artistRepository.findByOrderByArtistIdDesc();
    }

    public List<Artist> getAllArtistsNameAsc() {
        return artistRepository.findByOrderByNameAsc();
    }
    public Integer save(Artist artist) {
        Artist bean = new Artist();
        BeanUtils.copyProperties(artist, bean);
        bean = artistRepository.save(bean);
        return bean.getArtistId();
    }

    public void delete(Integer id) {
        artistRepository.deleteById(id);
    }

    public void update(Integer id, Artist artist) {
        Artist bean = requireOne(id);
        BeanUtils.copyProperties(artist, bean);
        artistRepository.save(bean);
    }

    public Artist getById(Integer id) {
        Artist original = requireOne(id);
        return toDTO(original);
    }

    private Artist toDTO(Artist original) {
        Artist bean = new Artist();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Artist requireOne(Integer id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
