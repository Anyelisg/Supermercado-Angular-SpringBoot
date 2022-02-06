package com.melit.supermercado.web.rest;

import com.melit.supermercado.repository.VentaDetalleRepository;
import com.melit.supermercado.service.VentaDetalleService;
import com.melit.supermercado.service.dto.VentaDetalleDTO;
import com.melit.supermercado.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.melit.supermercado.domain.VentaDetalle}.
 */
@RestController
@RequestMapping("/api")
public class VentaDetalleResource {

    private final Logger log = LoggerFactory.getLogger(VentaDetalleResource.class);

    private static final String ENTITY_NAME = "ventaDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentaDetalleService ventaDetalleService;

    private final VentaDetalleRepository ventaDetalleRepository;

    public VentaDetalleResource(VentaDetalleService ventaDetalleService, VentaDetalleRepository ventaDetalleRepository) {
        this.ventaDetalleService = ventaDetalleService;
        this.ventaDetalleRepository = ventaDetalleRepository;
    }

    /**
     * {@code POST  /venta-detalles} : Create a new ventaDetalle.
     *
     * @param ventaDetalleDTO the ventaDetalleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventaDetalleDTO, or with status {@code 400 (Bad Request)} if the ventaDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venta-detalles")
    public ResponseEntity<VentaDetalleDTO> createVentaDetalle(@Valid @RequestBody VentaDetalleDTO ventaDetalleDTO)
        throws URISyntaxException {
        log.debug("REST request to save VentaDetalle : {}", ventaDetalleDTO);
        if (ventaDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new ventaDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaDetalleDTO result = ventaDetalleService.save(ventaDetalleDTO);
        return ResponseEntity
            .created(new URI("/api/venta-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /venta/new} : Create a new ventaDetalle en Venta
     *
     * @param ventaDetalleDTO the ventaDetalleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventaDetalleDTO, or with status {@code 400 (Bad Request)} if the ventaDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venta/new")
    public ResponseEntity<VentaDetalleDTO> createVentaDetalleInVenta(@Valid @RequestBody VentaDetalleDTO ventaDetalleDTO)
        throws URISyntaxException {
        log.debug("REST request to save VentaDetalle : {}", ventaDetalleDTO);
        if (ventaDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new ventaDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaDetalleDTO result = ventaDetalleService.save(ventaDetalleDTO);
        return ResponseEntity
            .created(new URI("/api/venta/new/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venta-detalles/:id} : Updates an existing ventaDetalle.
     *
     * @param id the id of the ventaDetalleDTO to save.
     * @param ventaDetalleDTO the ventaDetalleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaDetalleDTO,
     * or with status {@code 400 (Bad Request)} if the ventaDetalleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventaDetalleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venta-detalles/{id}")
    public ResponseEntity<VentaDetalleDTO> updateVentaDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VentaDetalleDTO ventaDetalleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VentaDetalle : {}, {}", id, ventaDetalleDTO);
        if (ventaDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaDetalleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VentaDetalleDTO result = ventaDetalleService.save(ventaDetalleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /venta-detalles/:id} : Partial updates given fields of an existing ventaDetalle, field will ignore if it is null
     *
     * @param id the id of the ventaDetalleDTO to save.
     * @param ventaDetalleDTO the ventaDetalleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaDetalleDTO,
     * or with status {@code 400 (Bad Request)} if the ventaDetalleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ventaDetalleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ventaDetalleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/venta-detalles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VentaDetalleDTO> partialUpdateVentaDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VentaDetalleDTO ventaDetalleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VentaDetalle partially : {}, {}", id, ventaDetalleDTO);
        if (ventaDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaDetalleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VentaDetalleDTO> result = ventaDetalleService.partialUpdate(ventaDetalleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaDetalleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /venta-detalles} : get all the ventaDetalles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventaDetalles in body.
     */
    @GetMapping("/venta-detalles")
    public ResponseEntity<List<VentaDetalleDTO>> getAllVentaDetalles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of VentaDetalles");
        Page<VentaDetalleDTO> page = ventaDetalleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /venta/new/{numeroFactura} : get all the ventaDetalles by numeroFactura
     *
     * @param modelo the pagination information.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coches in body.
     */
    @GetMapping("/venta-detalles/by-numerofactura/{numeroFactura}")
    public ResponseEntity<List<VentaDetalleDTO>> getAllVentaDetallesByFactura(@PathVariable Integer numeroFactura, Pageable pageable) {
        log.debug("REST request to get a page of Coches");
        Page<VentaDetalleDTO> page = ventaDetalleService.getByNumeroFactura(numeroFactura, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /venta-detalles/by-factura/{numeroFactura} : get all the ventaDetalles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventaDetalles in body.
     */
    @GetMapping("/venta-detalles/by-factura/{numeroFactura}")
    public ResponseEntity<List<VentaDetalleDTO>> getAllVentaDetallesByNumeroFactura(
        @PathVariable Integer numeroFactura,
        Pageable pageable
    ) {
        log.debug("REST request to get a page of VentaDetalles");
        Page<VentaDetalleDTO> page = ventaDetalleService.byNumeroFactura(numeroFactura, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /venta-detalles/:id} : get the "id" ventaDetalle.
     *
     * @param id the id of the ventaDetalleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventaDetalleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venta-detalles/{id}")
    public ResponseEntity<VentaDetalleDTO> getVentaDetalle(@PathVariable Long id) {
        log.debug("REST request to get VentaDetalle : {}", id);
        Optional<VentaDetalleDTO> ventaDetalleDTO = ventaDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ventaDetalleDTO);
    }

    /**
     * {@code DELETE  /venta-detalles/:id} : delete the "id" ventaDetalle.
     *
     * @param id the id of the ventaDetalleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venta-detalles/{id}")
    public ResponseEntity<Void> deleteVentaDetalle(@PathVariable Long id) {
        log.debug("REST request to delete VentaDetalle : {}", id);
        ventaDetalleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
