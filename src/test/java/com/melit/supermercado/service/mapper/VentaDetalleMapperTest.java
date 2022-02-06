package com.melit.supermercado.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VentaDetalleMapperTest {

    private VentaDetalleMapper ventaDetalleMapper;

    @BeforeEach
    public void setUp() {
        ventaDetalleMapper = new VentaDetalleMapperImpl();
    }
}
