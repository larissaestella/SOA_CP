package com.example.reservas.controller;

import com.example.reservas.entity.Sala;
import com.example.reservas.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a salas.
 */
@RestController
@RequestMapping("/salas")
@Tag(name = "Salas", description = "Gerenciamento de salas de reunião")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @Operation(summary = "Cadastrar nova sala", description = "Cria uma nova sala de reunião")
    @PostMapping
    public ResponseEntity<Sala> criar(@Valid @RequestBody Sala sala) {
        Sala criada = salaService.criarSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Listar salas", description = "Retorna todas as salas cadastradas")
    @GetMapping
    public ResponseEntity<List<Sala>> listar() {
        return ResponseEntity.ok(salaService.listarSalas());
    }

    @Operation(summary = "Buscar sala por id", description = "Retorna os dados da sala pelo seu identificador")
    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.buscarPorId(id));
    }
}
