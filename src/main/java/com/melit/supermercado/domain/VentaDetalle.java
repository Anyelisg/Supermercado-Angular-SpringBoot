package com.melit.supermercado.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VentaDetalle.
 */
@Entity
@Table(name = "venta_detalle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VentaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Max(value = 99999999)
    @Column(name = "numero_factura", nullable = false)
    private Integer numeroFactura;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "precio_producto", nullable = false)
    private Double precioProducto;

    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "total_producto")
    private Double totalProducto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productos", "ventadetalles", "cliente", "empleado", "caja", "empresa" }, allowSetters = true)
    private Venta venta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VentaDetalle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFactura() {
        return this.numeroFactura;
    }

    public VentaDetalle numeroFactura(Integer numeroFactura) {
        this.setNumeroFactura(numeroFactura);
        return this;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNombreProducto() {
        return this.nombreProducto;
    }

    public VentaDetalle nombreProducto(String nombreProducto) {
        this.setNombreProducto(nombreProducto);
        return this;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return this.precioProducto;
    }

    public VentaDetalle precioProducto(Double precioProducto) {
        this.setPrecioProducto(precioProducto);
        return this;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Integer getCantidadProducto() {
        return this.cantidadProducto;
    }

    public VentaDetalle cantidadProducto(Integer cantidadProducto) {
        this.setCantidadProducto(cantidadProducto);
        return this;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Double getTotalProducto() {
        return this.totalProducto;
    }

    public VentaDetalle totalProducto(Double totalProducto) {
        this.setTotalProducto(totalProducto);
        return this;
    }

    public void setTotalProducto(Double totalProducto) {
        this.totalProducto = totalProducto;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public VentaDetalle venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDetalle)) {
            return false;
        }
        return id != null && id.equals(((VentaDetalle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDetalle{" +
            "id=" + getId() +
            ", numeroFactura=" + getNumeroFactura() +
            ", nombreProducto='" + getNombreProducto() + "'" +
            ", precioProducto=" + getPrecioProducto() +
            ", cantidadProducto=" + getCantidadProducto() +
            ", totalProducto=" + getTotalProducto() +
            "}";
    }
}
