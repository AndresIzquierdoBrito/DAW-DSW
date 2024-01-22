package es.cifpcm.IzquierdoAndresMiAli.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "municipios")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_municipio", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMunicipio;

    @Column(name = "id_provincia", nullable = false)
    private Integer idProvincia;

    /**
     * Código de muncipio DENTRO de la provincia, campo no único
     */
    @Column(name = "cod_municipio", nullable = false)
    private Integer codMunicipio;

    /**
     * Digito Control. El INE no revela cómo se calcula, secreto nuclear.
     */
    @Column(name = "DC", nullable = false)
    private Integer dc;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    /**
     * Código de muncipio DENTRO de la provincia, campo no único
     */
    public void setCodMunicipio(Integer codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    /**
     * Código de muncipio DENTRO de la provincia, campo no único
     */
    public Integer getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * Digito Control. El INE no revela cómo se calcula, secreto nuclear.
     */
    public void setDc(Integer dc) {
        this.dc = dc;
    }

    /**
     * Digito Control. El INE no revela cómo se calcula, secreto nuclear.
     */
    public Integer getDc() {
        return dc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "idMunicipio=" + idMunicipio + '\'' +
                "idProvincia=" + idProvincia + '\'' +
                "codMunicipio=" + codMunicipio + '\'' +
                "dc=" + dc + '\'' +
                "nombre=" + nombre + '\'' +
                '}';
    }
}
