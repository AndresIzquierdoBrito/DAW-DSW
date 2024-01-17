package es.cifpcm.demoDB.controllers;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "animal")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "VidaMedia", nullable = false)
    private BigDecimal vidaMedia;

    @Column(name = "Extinto", nullable = false)
    private Boolean extinto;

}
