package com.melit.supermercado.repository;

import com.melit.supermercado.domain.VentaDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VentaDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    @Query("SELECT MAX(c) FROM VentaDetalle c ")
    Page<VentaDetalle> searchByNumeroFactura(@Param("numeroFactura") Integer numeroFactura, Pageable pageable);

    /* BUSQUEDA POR NUMERO DE FACTURA */
    @Query("SELECT v FROM VentaDetalle v WHERE v.numeroFactura = :numeroFactura")
    Page<VentaDetalle> getByNumeroFactura(@Param("numeroFactura") Integer numeroFactura, Pageable pageable);
}
