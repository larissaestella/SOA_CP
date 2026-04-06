package com.example.reservas.service;

import com.example.reservas.entity.Sala;
import com.example.reservas.exception.ResourceNotFoundException;
import com.example.reservas.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço responsável por encapsular a lógica de negócios relacionada às salas.
 */
@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    /**
     * Cria uma nova sala de reunião.
     *
     * @param sala entidade contendo os dados da sala
     * @return sala persistida
     */
    public Sala criarSala(Sala sala) {
        return salaRepository.save(sala);
    }

    /**
     * Lista todas as salas cadastradas.
     *
     * @return lista de salas
     */
    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    /**
     * Retorna uma sala pelo seu identificador.
     *
     * @param id identificador da sala
     * @return sala encontrada
     * @throws ResourceNotFoundException quando a sala não é encontrada
     */
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada com id " + id));
    }
}
