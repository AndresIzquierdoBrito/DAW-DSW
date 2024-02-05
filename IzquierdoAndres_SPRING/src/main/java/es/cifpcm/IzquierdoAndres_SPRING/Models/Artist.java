package es.cifpcm.IzquierdoAndres_SPRING.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ArtistId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artistId;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 1, max = 120, message = "El título debe tener al menos un carácter y como máximo 120.")
    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    public Artist(Integer artistId, String name, List<Album> albums) {
        this.artistId = artistId;
        this.name = name;
        this.albums = albums;
    }

    public Artist() {

    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
