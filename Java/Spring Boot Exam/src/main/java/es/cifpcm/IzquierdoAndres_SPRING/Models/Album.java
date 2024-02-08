package es.cifpcm.IzquierdoAndres_SPRING.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "album")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AlbumId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer albumId;

    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 1, max = 160, message = "El título debe tener al menos un carácter y como máximo 160.")
    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "ArtistId", nullable = false)
    private Integer artistId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ArtistId", nullable = false, insertable = false, updatable = false)
    private Artist artist;

    public Album(Integer albumId, String title, Integer artistId) {
        this.albumId = albumId;
        this.title = title;
        this.artistId = artistId;
    }

    public Album(){

    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
