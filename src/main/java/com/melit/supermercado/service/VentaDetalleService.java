package com.melit.supermercado.service;

import com.melit.supermercado.service.dto.VentaDetalleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.melit.supermercado.domain.VentaDetalle}.
 */
public interface VentaDetalleService {
    /**
     * Save a ventaDetalle.
     *
     * @param ventaDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    VentaDetalleDTO save(VentaDetalleDTO ventaDetalleDTO);

    /**
     * Partially updates a ventaDetalle.
     *
     * @param ventaDetalleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VentaDetalleDTO> partialUpdate(VentaDetalleDTO ventaDetalleDTO);

    /**
     * Get all the ventaDetalles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VentaDetalleDTO> findAll(Pageable pageable);

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VentaDetalleDTO> byNumeroFactura(Integer numeroFactura, Pageable pageable);

    /**
     * Get all the ventas by numeroFactura
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VentaDetalleDTO> getByNumeroFactura(Integer numeroFactura, Pageable pageable);

    /**
     * Get the "id" ventaDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VentaDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" ventaDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
