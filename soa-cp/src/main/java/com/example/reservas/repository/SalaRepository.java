package com.example.reservas.repository;

import com.example.reservas.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de persistência com a entidade Sala.
 */
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}
