package com.melit.supermercado.service.mapper;

import com.melit.supermercado.domain.VentaDetalle;
import com.melit.supermercado.service.dto.VentaDetalleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VentaDetalle} and its DTO {@link VentaDetalleDTO}.
 */
@Mapper(componentModel = "spring", uses = { VentaMapper.class })
public interface VentaDetalleMapper extends EntityMapper<VentaDetalleDTO, VentaDetalle> {
    @Mapping(target = "venta", source = "venta", qualifiedByName = "id")
    VentaDetalleDTO toDto(VentaDetalle s);
}
