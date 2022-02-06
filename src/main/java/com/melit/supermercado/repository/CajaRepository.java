package com.melit.supermercado.repository;

import com.melit.supermercado.domain.Caja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Caja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {}
