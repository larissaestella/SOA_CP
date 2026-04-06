package com.example.reservas.service;

import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.Sala;
import com.example.reservas.entity.StatusReserva;
import com.example.reservas.entity.StatusSala;
import com.example.reservas.exception.ConflictException;
import com.example.reservas.exception.InvalidDataException;
import com.example.reservas.exception.ResourceNotFoundException;
import com.example.reservas.repository.ReservaRepository;
import com.example.reservas.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

/**
 * Camada de serviço responsável pela lógica de reservas de salas.
 */
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository, SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
    }

    /**
     * Cria uma nova reserva de sala.
     *
     * @param reserva entidade com dados da reserva. A sala associada deve ter sido previamente setada.
     * @return reserva persistida
     */
    @Transactional
    public Reserva criarReserva(Reserva reserva) {
        // Validações de integridade
        Sala sala = salaRepository.findById(reserva.getSala().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada com id " + reserva.getSala().getId()));

        // Verifica se a sala está ativa
        if (sala.getStatus() == StatusSala.INATIVA) {
            throw new InvalidDataException("Não é permitido reservar uma sala inativa");
        }

        // Verifica se horário final é maior que o inicial
        LocalTime inicio = reserva.getHoraInicio();
        LocalTime fim = reserva.getHoraFim();
        if (!fim.isAfter(inicio)) {
            throw new InvalidDataException("Horário de término deve ser posterior ao horário de início");
        }

        // Verifica conflito com reservas existentes ativas na mesma sala e data
        List<Reserva> reservasDoDia = reservaRepository.findBySalaIdAndDataAndStatus(
                sala.getId(), reserva.getData(), StatusReserva.ATIVA);
        for (Reserva existente : reservasDoDia) {
            // Se houver interseção entre os intervalos, lança exceção de conflito
            if (intervalosConflitam(inicio, fim, existente.getHoraInicio(), existente.getHoraFim())) {
                throw new ConflictException("Já existe reserva para esta sala no horário informado");
            }
        }

        // Atribui a sala carregada para garantir que está anexada ao contexto de persistência
        reserva.setSala(sala);
        reserva.setStatus(StatusReserva.ATIVA);
        return reservaRepository.save(reserva);
    }

    /**
     * Verifica se dois intervalos de tempo conflitam.
     *
     * @param inicioNovo início do novo intervalo
     * @param fimNovo    fim do novo intervalo
     * @param inicioExistente início do intervalo existente
     * @param fimExistente    fim do intervalo existente
     * @return true se houver sobreposição, false caso contrário
     */
    private boolean intervalosConflitam(LocalTime inicioNovo, LocalTime fimNovo,
                                        LocalTime inicioExistente, LocalTime fimExistente) {
        return inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);
    }

    /**
     * Lista todas as reservas registradas.
     *
     * @return lista de reservas
     */
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Busca uma reserva pelo seu identificador.
     *
     * @param id identificador da reserva
     * @return reserva encontrada
     * @throws ResourceNotFoundException quando a reserva não existe
     */
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com id " + id));
    }

    /**
     * Cancela uma reserva existente. Reservas canceladas não bloqueiam mais o horário.
     *
     * @param id identificador da reserva a ser cancelada
     * @return reserva atualizada com status CANCELADA
     */
    @Transactional
    public Reserva cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com id " + id));
        // Apenas modifica se ainda estiver ativa
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            return reserva; // idempotente
        }
        reserva.setStatus(StatusReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }
}
