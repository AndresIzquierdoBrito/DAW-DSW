package es.cifpcm.demoDB.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 15)
    private String nombre;

    @Column(name = "VidaMedia", nullable = false, precision = 5, scale = 2)
    private BigDecimal vidaMedia;

    @Column(name = "Extinto", nullable = false)
    private Boolean extinto = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getVidaMedia() {
        return vidaMedia;
    }

    public void setVidaMedia(BigDecimal vidaMedia) {
        this.vidaMedia = vidaMedia;
    }

    public Boolean getExtinto() {
        return extinto;
    }

    public void setExtinto(Boolean extinto) {
        this.extinto = extinto;
    }

}