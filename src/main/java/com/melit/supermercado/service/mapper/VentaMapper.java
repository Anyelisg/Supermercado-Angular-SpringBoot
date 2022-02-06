package com.melit.supermercado.service.mapper;

import com.melit.supermercado.domain.Venta;
import com.melit.supermercado.service.dto.VentaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClienteMapper.class, EmpleadoMapper.class, CajaMapper.class, EmpresaMapper.class })
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "id")
    @Mapping(target = "empleado", source = "empleado", qualifiedByName = "id")
    @Mapping(target = "caja", source = "caja", qualifiedByName = "id")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    VentaDTO toDto(Venta s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoId(Venta venta);
}
