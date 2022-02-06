package com.melit.supermercado.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melit.supermercado.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VentaDetalleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDetalleDTO.class);
        VentaDetalleDTO ventaDetalleDTO1 = new VentaDetalleDTO();
        ventaDetalleDTO1.setId(1L);
        VentaDetalleDTO ventaDetalleDTO2 = new VentaDetalleDTO();
        assertThat(ventaDetalleDTO1).isNotEqualTo(ventaDetalleDTO2);
        ventaDetalleDTO2.setId(ventaDetalleDTO1.getId());
        assertThat(ventaDetalleDTO1).isEqualTo(ventaDetalleDTO2);
        ventaDetalleDTO2.setId(2L);
        assertThat(ventaDetalleDTO1).isNotEqualTo(ventaDetalleDTO2);
        ventaDetalleDTO1.setId(null);
        assertThat(ventaDetalleDTO1).isNotEqualTo(ventaDetalleDTO2);
    }
}
