package com.melit.supermercado.service.dto;

import com.melit.supermercado.domain.enumeration.TipoPago;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melit.supermercado.domain.Venta} entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    @Max(value = 99999999)
    private Integer numeroFactura;

    private LocalDate fecha;

    private Double total;

    @NotNull
    private TipoPago tipoPago;

    private ClienteDTO cliente;

    private EmpleadoDTO empleado;

    private CajaDTO caja;

    private EmpresaDTO empresa;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public CajaDTO getCaja() {
        return caja;
    }

    public void setCaja(CajaDTO caja) {
        this.caja = caja;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", numeroFactura=" + getNumeroFactura() +
            ", fecha='" + getFecha() + "'" +
            ", total=" + getTotal() +
            ", tipoPago='" + getTipoPago() + "'" +
            ", cliente=" + getCliente() +
            ", empleado=" + getEmpleado() +
            ", caja=" + getCaja() +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
