package com.melit.supermercado.service.mapper;

import com.melit.supermercado.domain.Empleado;
import com.melit.supermercado.service.dto.EmpleadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpleadoDTO toDtoId(Empleado empleado);
}
