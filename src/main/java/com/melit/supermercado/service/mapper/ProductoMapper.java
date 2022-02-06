package com.melit.supermercado.service.mapper;

import com.melit.supermercado.domain.Producto;
import com.melit.supermercado.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = { VentaMapper.class })
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "venta", source = "venta", qualifiedByName = "id")
    ProductoDTO toDto(Producto s);
}
