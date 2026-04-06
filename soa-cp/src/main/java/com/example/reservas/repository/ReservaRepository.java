package com.example.reservas.repository;

import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência com a entidade Reserva.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Retorna todas as reservas ativas de uma sala em uma determinada data.
     *
     * @param salaId ID da sala
     * @param data   data da reserva
     * @param status status da reserva (ATIVA ou CANCELADA)
     * @return lista de reservas
     */
    List<Reserva> findBySalaIdAndDataAndStatus(Long salaId, LocalDate data, StatusReserva status);
}
