package com.melit.supermercado.service.mapper;

import com.melit.supermercado.domain.Empresa;
import com.melit.supermercado.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoId(Empresa empresa);
}
