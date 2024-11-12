package br.ufac.edgeneoapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class TesteAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "treinamento_id", nullable = false)
    private Treinamento treinamento;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String desempenhoDisciplinas;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String probabilidadeAprovacaoDisciplinas;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String probabilidadeReprovacaoDisciplinas;

    private LocalDateTime testeDataPrevisao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Treinamento getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Treinamento treinamento) {
        this.treinamento = treinamento;
    }

    public String getDesempenhoDisciplinas() {
        return desempenhoDisciplinas;
    }

    public void setDesempenhoDisciplinas(String desempenhoDisciplinas) {
        this.desempenhoDisciplinas = desempenhoDisciplinas;
    }

    public String getProbabilidadeAprovacaoDisciplinas() {
        return probabilidadeAprovacaoDisciplinas;
    }

    public void setProbabilidadeAprovacaoDisciplinas(String probabilidadeAprovacaoDisciplinas) {
        this.probabilidadeAprovacaoDisciplinas = probabilidadeAprovacaoDisciplinas;
    }

    public String getProbabilidadeReprovacaoDisciplinas() {
        return probabilidadeReprovacaoDisciplinas;
    }

    public void setProbabilidadeReprovacaoDisciplinas(String probabilidadeReprovacaoDisciplinas) {
        this.probabilidadeReprovacaoDisciplinas = probabilidadeReprovacaoDisciplinas;
    }

    public LocalDateTime getTesteDataPrevisao() {
        return testeDataPrevisao;
    }

    public void setTesteDataPrevisao(LocalDateTime testeDataPrevisao) {
        this.testeDataPrevisao = testeDataPrevisao;
    }

    
}