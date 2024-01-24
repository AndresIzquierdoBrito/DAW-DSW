package es.cifpcm.IzquierdoAndresMiAli.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "productoffer", indexes = {
@Index(name = "idx_productoffer_product_id", columnList = "product_id")
})
public class Productoffer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price")
    private Float productPrice;

    @Column(name = "product_picture")
    private String productPicture;

    @Column(name = "id_municipio", nullable = false)
    private Integer idMunicipio;

    @Column(name = "product_stock", nullable = false)
    private Integer productStock;

    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false, insertable = false, updatable = false, referencedColumnName = "id_municipio")
    private Municipio municipio;

    public Productoffer(){

    }

    public Productoffer(String productName, Float productPrice, String productPicture, Integer idMunicipio, Integer productStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPicture = productPicture;
        this.idMunicipio = idMunicipio;
        this.productStock = productStock;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }
}
