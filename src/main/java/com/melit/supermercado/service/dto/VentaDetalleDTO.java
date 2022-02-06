package com.melit.supermercado.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melit.supermercado.domain.VentaDetalle} entity.
 */
public class VentaDetalleDTO implements Serializable {

    private Long id;

    @NotNull
    @Max(value = 99999999)
    private Integer numeroFactura;

    @NotNull
    private String nombreProducto;

    @NotNull
    private Double precioProducto;

    @NotNull
    private Integer cantidadProducto;

    private Double totalProducto;

    private VentaDTO venta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Double getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(Double totalProducto) {
        this.totalProducto = totalProducto;
    }

    public VentaDTO getVenta() {
        return venta;
    }

    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDetalleDTO)) {
            return false;
        }

        VentaDetalleDTO ventaDetalleDTO = (VentaDetalleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDetalleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDetalleDTO{" +
            "id=" + getId() +
            ", numeroFactura=" + getNumeroFactura() +
            ", nombreProducto='" + getNombreProducto() + "'" +
            ", precioProducto=" + getPrecioProducto() +
            ", cantidadProducto=" + getCantidadProducto() +
            ", totalProducto=" + getTotalProducto() +
            ", venta=" + getVenta() +
            "}";
    }
}
