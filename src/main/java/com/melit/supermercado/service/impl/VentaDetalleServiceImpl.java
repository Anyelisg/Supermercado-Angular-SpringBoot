package com.melit.supermercado.service.impl;

import com.melit.supermercado.domain.VentaDetalle;
import com.melit.supermercado.repository.VentaDetalleRepository;
import com.melit.supermercado.service.VentaDetalleService;
import com.melit.supermercado.service.dto.VentaDetalleDTO;
import com.melit.supermercado.service.mapper.VentaDetalleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VentaDetalle}.
 */
@Service
@Transactional
public class VentaDetalleServiceImpl implements VentaDetalleService {

    private final Logger log = LoggerFactory.getLogger(VentaDetalleServiceImpl.class);

    private final VentaDetalleRepository ventaDetalleRepository;

    private final VentaDetalleMapper ventaDetalleMapper;

    public VentaDetalleServiceImpl(VentaDetalleRepository ventaDetalleRepository, VentaDetalleMapper ventaDetalleMapper) {
        this.ventaDetalleRepository = ventaDetalleRepository;
        this.ventaDetalleMapper = ventaDetalleMapper;
    }

    @Override
    public VentaDetalleDTO save(VentaDetalleDTO ventaDetalleDTO) {
        log.debug("Request to save VentaDetalle : {}", ventaDetalleDTO);
        VentaDetalle ventaDetalle = ventaDetalleMapper.toEntity(ventaDetalleDTO);

        if (null != ventaDetalle.getCantidadProducto() && null != ventaDetalle.getPrecioProducto()) {
            ventaDetalle.setTotalProducto(ventaDetalle.getPrecioProducto() * ventaDetalle.getCantidadProducto());
        }

        ventaDetalle = ventaDetalleRepository.save(ventaDetalle);
        return ventaDetalleMapper.toDto(ventaDetalle);
    }

    @Override
    public Optional<VentaDetalleDTO> partialUpdate(VentaDetalleDTO ventaDetalleDTO) {
        log.debug("Request to partially update VentaDetalle : {}", ventaDetalleDTO);

        return ventaDetalleRepository
            .findById(ventaDetalleDTO.getId())
            .map(existingVentaDetalle -> {
                ventaDetalleMapper.partialUpdate(existingVentaDetalle, ventaDetalleDTO);

                return existingVentaDetalle;
            })
            .map(ventaDetalleRepository::save)
            .map(ventaDetalleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaDetalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VentaDetalles");
        return ventaDetalleRepository.findAll(pageable).map(ventaDetalleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaDetalleDTO> byNumeroFactura(Integer numeroFactura, Pageable pageable) {
        log.debug("Request to get all VentaDetalles");
        return ventaDetalleRepository.searchByNumeroFactura(numeroFactura, pageable).map(ventaDetalleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaDetalleDTO> getByNumeroFactura(Integer numeroFactura, Pageable pageable) {
        log.debug("Request to get all VentaDetalles");
        return ventaDetalleRepository.getByNumeroFactura(numeroFactura, pageable).map(ventaDetalleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDetalleDTO> findOne(Long id) {
        log.debug("Request to get VentaDetalle : {}", id);
        return ventaDetalleRepository.findById(id).map(ventaDetalleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VentaDetalle : {}", id);
        ventaDetalleRepository.deleteById(id);
    }
}
