package es.cifpcm.demoDB.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link Animal}
 */
public class AnimalDTO implements Serializable {
    private final Integer id;
    private final String nombre;
    private final BigDecimal vidaMedia;
    private final Boolean extinto;

    public AnimalDTO(Integer id, String nombre, BigDecimal vidaMedia, Boolean extinto) {
        this.id = id;
        this.nombre = nombre;
        this.vidaMedia = vidaMedia;
        this.extinto = extinto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getVidaMedia() {
        return vidaMedia;
    }

    public Boolean getExtinto() {
        return extinto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDTO entity = (AnimalDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.vidaMedia, entity.vidaMedia) &&
                Objects.equals(this.extinto, entity.extinto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, vidaMedia, extinto);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "vidaMedia = " + vidaMedia + ", " +
                "extinto = " + extinto + ")";
    }
}