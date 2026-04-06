package com.example.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO utilizado para criar uma nova reserva a partir da requisição HTTP.
 */
public record ReservaRequest(
        @NotNull(message = "ID da sala é obrigatório")
        Long salaId,
        @NotBlank(message = "Nome do solicitante é obrigatório")
        String nomeSolicitante,
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotNull(message = "Data é obrigatória")
        LocalDate data,
        @NotNull(message = "Hora de início é obrigatória")
        LocalTime horaInicio,
        @NotNull(message = "Hora de fim é obrigatória")
        LocalTime horaFim,
        @NotBlank(message = "Finalidade é obrigatória")
        String finalidade
) 