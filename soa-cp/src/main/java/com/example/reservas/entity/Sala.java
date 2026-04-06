package com.example.reservas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que representa uma sala de reunião.
 */
@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da sala. Não pode ser vazio.
     */
    @NotBlank(message = "Nome da sala é obrigatório")
    private String nome;

    /**
     * Capacidade máxima de pessoas na sala. Deve ser um valor positivo.
     */
    @NotNull(message = "Capacidade da sala é obrigatória")
    @Min(value = 1, message = "Capacidade deve ser maior que zero")
    private Integer capacidade;

    /**
     * Localização da sala, por exemplo, andar e número. Não pode ser vazia.
     */
    @NotBlank(message = "Localização da sala é obrigatória")
    private String localizacao;

    /**
     * Status da sala. Por padrão a sala é criada como ativa.
     */
    @Enumerated(EnumType.STRING)
    private StatusSala status = StatusSala.ATIVA;

    public Sala() {
    }

    public Sala(String nome, Integer capacidade, String localizacao, StatusSala status) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.status = status != null ? status : StatusSala.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public StatusSala getStatus() {
        return status;
    }

    public void setStatus(StatusSala status) {
        this.status = status;
    }
}
