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
public class Treinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;
    
    @ManyToOne
    @JoinColumn(name = "dados_brutos_id", nullable = false)
    private DadosBrutos dadosBrutos;
    
    @ManyToOne
    @JoinColumn(name = "dados_processados_id", nullable = false)
    private DadosProcessados dadosProcessados;
    
    @Column(nullable = false)
    private String algoritmoUtilizado;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dataHora;
    
    @Column(nullable = false)
    private Float acuracia;
    
    @Column(nullable = false)
    private Float precisao;
    
    @Column(nullable = false)
    private Float tempoExecucao;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public DadosBrutos getDadosBrutos() {
        return dadosBrutos;
    }

    public void setDadosBrutos(DadosBrutos dadosBrutos) {
        this.dadosBrutos = dadosBrutos;
    }

    public DadosProcessados getDadosProcessados() {
        return dadosProcessados;
    }

    public void setDadosProcessados(DadosProcessados dadosProcessados) {
        this.dadosProcessados = dadosProcessados;
    }

    public String getAlgoritmoUtilizado() {
        return algoritmoUtilizado;
    }

    public void setAlgoritmoUtilizado(String algoritmoUtilizado) {
        this.algoritmoUtilizado = algoritmoUtilizado;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Float getAcuracia() {
        return acuracia;
    }

    public void setAcuracia(Float acuracia) {
        this.acuracia = acuracia;
    }

    public Float getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Float precisao) {
        this.precisao = precisao;
    }

    public Float getTempoExecucao() {
        return tempoExecucao;
    }

    public void setTempoExecucao(Float tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }
}