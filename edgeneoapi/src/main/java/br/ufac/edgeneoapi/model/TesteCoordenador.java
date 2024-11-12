package br.ufac.edgeneoapi.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class TesteCoordenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coordenador_id", nullable = false)
    private Coordenador coordenador;
    
    @ManyToOne
    @JoinColumn(name = "treinamento_id", nullable = false)
    private Treinamento treinamento;
    
    @Column(nullable = false)
    private String desempenhoDisciplinasPorPeriodo;
    
    @Column(nullable = false)
    private String desempenhoTurmas;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dataPrevisao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    public Treinamento getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Treinamento treinamento) {
        this.treinamento = treinamento;
    }

    public String getDesempenhoDisciplinasPorPeriodo() {
        return desempenhoDisciplinasPorPeriodo;
    }

    public void setDesempenhoDisciplinasPorPeriodo(String desempenhoDisciplinasPorPeriodo) {
        this.desempenhoDisciplinasPorPeriodo = desempenhoDisciplinasPorPeriodo;
    }

    public String getDesempenhoTurmas() {
        return desempenhoTurmas;
    }

    public void setDesempenhoTurmas(String desempenhoTurmas) {
        this.desempenhoTurmas = desempenhoTurmas;
    }

    public Timestamp getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(Timestamp dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }
    
    
}