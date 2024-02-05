package es.cifpcm.IzquierdoAndres_SPRING.Data.Services;

import es.cifpcm.IzquierdoAndres_SPRING.Data.Respositories.AlbumRepository;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Album;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }


    public List<Album> getSortedAlbums() {
        return albumRepository.getAlbumsSorted();
    }

    public Integer save(Album album) {
        Album bean = new Album();
        BeanUtils.copyProperties(album, bean);
        bean = albumRepository.save(bean);
        return bean.getAlbumId();
    }

    public void delete(Integer id) {
        albumRepository.deleteById(id);
    }

    public void update(Integer id, Album album) {
        Album bean = requireOne(id);
        BeanUtils.copyProperties(album, bean);
        albumRepository.save(bean);
    }

    public Album getById(Integer id) {
        Album original = requireOne(id);
        return toDTO(original);
    }

    private Album toDTO(Album original) {
        Album bean = new Album();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Album requireOne(Integer id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
