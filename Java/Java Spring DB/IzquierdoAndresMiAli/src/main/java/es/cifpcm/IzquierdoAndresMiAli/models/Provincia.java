package es.cifpcm.IzquierdoAndresMiAli.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_provincia", nullable = false)
    private Short idProvincia;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    private List<Municipio> municipios;

    public void setIdProvincia(Short idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Short getIdProvincia() {
        return idProvincia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Municipio> getMunicipios (Short idProvincia) {
        return this.municipios;
    }
    @Override
    public String toString() {
        return "Provincia{" +
                "idProvincia=" + idProvincia + '\'' +
                "nombre=" + nombre + '\'' +
                '}';
    }
}
