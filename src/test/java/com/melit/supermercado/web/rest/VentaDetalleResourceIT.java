package com.melit.supermercado.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.melit.supermercado.IntegrationTest;
import com.melit.supermercado.domain.VentaDetalle;
import com.melit.supermercado.repository.VentaDetalleRepository;
import com.melit.supermercado.service.dto.VentaDetalleDTO;
import com.melit.supermercado.service.mapper.VentaDetalleMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VentaDetalleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VentaDetalleResourceIT {

    private static final Integer DEFAULT_NUMERO_FACTURA = 99999999;
    private static final Integer UPDATED_NUMERO_FACTURA = 99999998;

    private static final String DEFAULT_NOMBRE_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PRODUCTO = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_PRODUCTO = 1D;
    private static final Double UPDATED_PRECIO_PRODUCTO = 2D;

    private static final Integer DEFAULT_CANTIDAD_PRODUCTO = 1;
    private static final Integer UPDATED_CANTIDAD_PRODUCTO = 2;

    private static final Double DEFAULT_TOTAL_PRODUCTO = 1D;
    private static final Double UPDATED_TOTAL_PRODUCTO = 2D;

    private static final String ENTITY_API_URL = "/api/venta-detalles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VentaDetalleRepository ventaDetalleRepository;

    @Autowired
    private VentaDetalleMapper ventaDetalleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentaDetalleMockMvc;

    private VentaDetalle ventaDetalle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaDetalle createEntity(EntityManager em) {
        VentaDetalle ventaDetalle = new VentaDetalle()
            .numeroFactura(DEFAULT_NUMERO_FACTURA)
            .nombreProducto(DEFAULT_NOMBRE_PRODUCTO)
            .precioProducto(DEFAULT_PRECIO_PRODUCTO)
            .cantidadProducto(DEFAULT_CANTIDAD_PRODUCTO)
            .totalProducto(DEFAULT_TOTAL_PRODUCTO);
        return ventaDetalle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaDetalle createUpdatedEntity(EntityManager em) {
        VentaDetalle ventaDetalle = new VentaDetalle()
            .numeroFactura(UPDATED_NUMERO_FACTURA)
            .nombreProducto(UPDATED_NOMBRE_PRODUCTO)
            .precioProducto(UPDATED_PRECIO_PRODUCTO)
            .cantidadProducto(UPDATED_CANTIDAD_PRODUCTO)
            .totalProducto(UPDATED_TOTAL_PRODUCTO);
        return ventaDetalle;
    }

    @BeforeEach
    public void initTest() {
        ventaDetalle = createEntity(em);
    }

    @Test
    @Transactional
    void createVentaDetalle() throws Exception {
        int databaseSizeBeforeCreate = ventaDetalleRepository.findAll().size();
        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);
        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getNumeroFactura()).isEqualTo(DEFAULT_NUMERO_FACTURA);
        assertThat(testVentaDetalle.getNombreProducto()).isEqualTo(DEFAULT_NOMBRE_PRODUCTO);
        assertThat(testVentaDetalle.getPrecioProducto()).isEqualTo(DEFAULT_PRECIO_PRODUCTO);
        assertThat(testVentaDetalle.getCantidadProducto()).isEqualTo(DEFAULT_CANTIDAD_PRODUCTO);
        assertThat(testVentaDetalle.getTotalProducto()).isEqualTo(DEFAULT_TOTAL_PRODUCTO);
    }

    @Test
    @Transactional
    void createVentaDetalleWithExistingId() throws Exception {
        // Create the VentaDetalle with an existing ID
        ventaDetalle.setId(1L);
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        int databaseSizeBeforeCreate = ventaDetalleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroFacturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaDetalleRepository.findAll().size();
        // set the field null
        ventaDetalle.setNumeroFactura(null);

        // Create the VentaDetalle, which fails.
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaDetalleRepository.findAll().size();
        // set the field null
        ventaDetalle.setNombreProducto(null);

        // Create the VentaDetalle, which fails.
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaDetalleRepository.findAll().size();
        // set the field null
        ventaDetalle.setPrecioProducto(null);

        // Create the VentaDetalle, which fails.
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCantidadProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaDetalleRepository.findAll().size();
        // set the field null
        ventaDetalle.setCantidadProducto(null);

        // Create the VentaDetalle, which fails.
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        restVentaDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVentaDetalles() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        // Get all the ventaDetalleList
        restVentaDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventaDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFactura").value(hasItem(DEFAULT_NUMERO_FACTURA)))
            .andExpect(jsonPath("$.[*].nombreProducto").value(hasItem(DEFAULT_NOMBRE_PRODUCTO)))
            .andExpect(jsonPath("$.[*].precioProducto").value(hasItem(DEFAULT_PRECIO_PRODUCTO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidadProducto").value(hasItem(DEFAULT_CANTIDAD_PRODUCTO)))
            .andExpect(jsonPath("$.[*].totalProducto").value(hasItem(DEFAULT_TOTAL_PRODUCTO.doubleValue())));
    }

    @Test
    @Transactional
    void getVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        // Get the ventaDetalle
        restVentaDetalleMockMvc
            .perform(get(ENTITY_API_URL_ID, ventaDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventaDetalle.getId().intValue()))
            .andExpect(jsonPath("$.numeroFactura").value(DEFAULT_NUMERO_FACTURA))
            .andExpect(jsonPath("$.nombreProducto").value(DEFAULT_NOMBRE_PRODUCTO))
            .andExpect(jsonPath("$.precioProducto").value(DEFAULT_PRECIO_PRODUCTO.doubleValue()))
            .andExpect(jsonPath("$.cantidadProducto").value(DEFAULT_CANTIDAD_PRODUCTO))
            .andExpect(jsonPath("$.totalProducto").value(DEFAULT_TOTAL_PRODUCTO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingVentaDetalle() throws Exception {
        // Get the ventaDetalle
        restVentaDetalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle
        VentaDetalle updatedVentaDetalle = ventaDetalleRepository.findById(ventaDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedVentaDetalle are not directly saved in db
        em.detach(updatedVentaDetalle);
        updatedVentaDetalle
            .numeroFactura(UPDATED_NUMERO_FACTURA)
            .nombreProducto(UPDATED_NOMBRE_PRODUCTO)
            .precioProducto(UPDATED_PRECIO_PRODUCTO)
            .cantidadProducto(UPDATED_CANTIDAD_PRODUCTO)
            .totalProducto(UPDATED_TOTAL_PRODUCTO);
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(updatedVentaDetalle);

        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ventaDetalleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getNumeroFactura()).isEqualTo(UPDATED_NUMERO_FACTURA);
        assertThat(testVentaDetalle.getNombreProducto()).isEqualTo(UPDATED_NOMBRE_PRODUCTO);
        assertThat(testVentaDetalle.getPrecioProducto()).isEqualTo(UPDATED_PRECIO_PRODUCTO);
        assertThat(testVentaDetalle.getCantidadProducto()).isEqualTo(UPDATED_CANTIDAD_PRODUCTO);
        assertThat(testVentaDetalle.getTotalProducto()).isEqualTo(UPDATED_TOTAL_PRODUCTO);
    }

    @Test
    @Transactional
    void putNonExistingVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ventaDetalleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVentaDetalleWithPatch() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle using partial update
        VentaDetalle partialUpdatedVentaDetalle = new VentaDetalle();
        partialUpdatedVentaDetalle.setId(ventaDetalle.getId());

        partialUpdatedVentaDetalle
            .numeroFactura(UPDATED_NUMERO_FACTURA)
            .precioProducto(UPDATED_PRECIO_PRODUCTO)
            .cantidadProducto(UPDATED_CANTIDAD_PRODUCTO)
            .totalProducto(UPDATED_TOTAL_PRODUCTO);

        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaDetalle))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getNumeroFactura()).isEqualTo(UPDATED_NUMERO_FACTURA);
        assertThat(testVentaDetalle.getNombreProducto()).isEqualTo(DEFAULT_NOMBRE_PRODUCTO);
        assertThat(testVentaDetalle.getPrecioProducto()).isEqualTo(UPDATED_PRECIO_PRODUCTO);
        assertThat(testVentaDetalle.getCantidadProducto()).isEqualTo(UPDATED_CANTIDAD_PRODUCTO);
        assertThat(testVentaDetalle.getTotalProducto()).isEqualTo(UPDATED_TOTAL_PRODUCTO);
    }

    @Test
    @Transactional
    void fullUpdateVentaDetalleWithPatch() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle using partial update
        VentaDetalle partialUpdatedVentaDetalle = new VentaDetalle();
        partialUpdatedVentaDetalle.setId(ventaDetalle.getId());

        partialUpdatedVentaDetalle
            .numeroFactura(UPDATED_NUMERO_FACTURA)
            .nombreProducto(UPDATED_NOMBRE_PRODUCTO)
            .precioProducto(UPDATED_PRECIO_PRODUCTO)
            .cantidadProducto(UPDATED_CANTIDAD_PRODUCTO)
            .totalProducto(UPDATED_TOTAL_PRODUCTO);

        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaDetalle))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getNumeroFactura()).isEqualTo(UPDATED_NUMERO_FACTURA);
        assertThat(testVentaDetalle.getNombreProducto()).isEqualTo(UPDATED_NOMBRE_PRODUCTO);
        assertThat(testVentaDetalle.getPrecioProducto()).isEqualTo(UPDATED_PRECIO_PRODUCTO);
        assertThat(testVentaDetalle.getCantidadProducto()).isEqualTo(UPDATED_CANTIDAD_PRODUCTO);
        assertThat(testVentaDetalle.getTotalProducto()).isEqualTo(UPDATED_TOTAL_PRODUCTO);
    }

    @Test
    @Transactional
    void patchNonExistingVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ventaDetalleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // Create the VentaDetalle
        VentaDetalleDTO ventaDetalleDTO = ventaDetalleMapper.toDto(ventaDetalle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeDelete = ventaDetalleRepository.findAll().size();

        // Delete the ventaDetalle
        restVentaDetalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, ventaDetalle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
