package com.melit.supermercado.repository;

import com.melit.supermercado.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    /* BUSCAR PRODUCTO POR CODIGO */
    @Query("SELECT p FROM Producto p WHERE p.codigo = :codigo")
    Page<Producto> productoByCodigo(@Param("codigo") String codigo, Pageable pageable);
}
