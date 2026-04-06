package com.example.reservas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidade que representa uma reserva de sala.
 */
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sala associada à reserva. Obrigatório.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    /**
     * Nome do solicitante que está reservando a sala.
     */
    @NotBlank(message = "Nome do solicitante é obrigatório")
    private String nomeSolicitante;

    /**
     * E-mail de contato do solicitante.
     */
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    /**
     * Data em que a reserva ocorrerá.
     */
    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    /**
     * Hora de início da reserva.
     */
    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime horaInicio;

    /**
     * Hora de término da reserva.
     */
    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime horaFim;

    /**
     * Finalidade da reserva, por exemplo reunião de projeto, treinamento, etc.
     */
    @NotBlank(message = "Finalidade é obrigatória")
    private String finalidade;

    /**
     * Status atual da reserva. Por padrão, ao ser criada é ativa.
     */
    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.ATIVA;

    public Reserva() {
    }

    public Reserva(Sala sala, String nomeSolicitante, String email,
                   LocalDate data, LocalTime horaInicio, LocalTime horaFim,
                   String finalidade) {
        this.sala = sala;
        this.nomeSolicitante = nomeSolicitante;
        this.email = email;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.finalidade = finalidade;
        this.status = StatusReserva.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }
}
