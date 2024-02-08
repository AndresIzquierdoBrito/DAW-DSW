package es.cifpcm.IzquierdoAndresMiAli.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "precio_total", nullable = false)
    private Float precioTotal;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "pedidos_productoffer",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_productoffer")
    )
    private List<Productoffer> productos;

    public Pedido() {
    }

    public Pedido(Integer id, String usuario, Float precioTotal, List<Productoffer> productos, LocalDate fecha) {
        this.id = id;
        this.usuario = usuario;
        this.precioTotal = precioTotal;
        this.productos = productos;
        this.fecha = fecha;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Productoffer> getProductos() {
        return productos;
    }

    public void setProductos(List<Productoffer> productos) {
        this.productos = productos;
    }
}
