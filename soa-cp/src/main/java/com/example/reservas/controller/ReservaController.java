package com.example.reservas.controller;

import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.Sala;
import com.example.reservas.service.ReservaService;
import com.example.reservas.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a reservas.
 */
@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Gerenciamento de reservas de salas")
public class ReservaController {

    private final ReservaService reservaService;
    private final SalaService salaService;

    public ReservaController(ReservaService reservaService, SalaService salaService) {
        this.reservaService = reservaService;
        this.salaService = salaService;
    }

    @Operation(summary = "Criar reserva", description = "Cria uma nova reserva de sala. Requer autenticação.")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Reserva> criar(@Valid @RequestBody ReservaRequest request) {
        // Carrega a sala antes de montar a reserva
        Sala sala = salaService.buscarPorId(request.getSalaId());
        Reserva reserva = new Reserva(
                sala,
                request.getNomeSolicitante(),
                request.getEmail(),
                request.getData(),
                request.getHoraInicio(),
                request.getHoraFim(),
                request.getFinalidade()
        );
        Reserva criada = reservaService.criarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Listar reservas", description = "Retorna todas as reservas registradas")
    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @Operation(summary = "Buscar reserva por id", description = "Retorna os dados de uma reserva pelo seu identificador")
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva existente. Requer autenticação.")
    @PutMapping("/{id}/cancelar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        Reserva cancelada = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(cancelada);
    }
}
