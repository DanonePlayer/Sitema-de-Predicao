package br.ufac.edgeneoapi.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false )
    private Curso curso;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    private List<AlunoDisciplinas> alunoDisciplinas;

    @Column(nullable = false)
    private String disciplinaNome;

    @Column(nullable = false)
    private String disciplinaCodigo;

    @Column()
    private Integer disciplinaPeriodo;

    @Column(nullable = false)
    private Integer disciplinaCreditos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }

    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
    }

    public String getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public void setDisciplinaCodigo(String disciplinaCodigo) {
        this.disciplinaCodigo = disciplinaCodigo;
    }

    public Integer getDisciplinaPeriodo() {
        return disciplinaPeriodo;
    }

    public void setDisciplinaPeriodo(Integer disciplinaPeriodo) {
        this.disciplinaPeriodo = disciplinaPeriodo;
    }

    public Integer getDisciplinaCreditos() {
        return disciplinaCreditos;
    }

    public void setDisciplinaCreditos(Integer disciplinaCreditos) {
        this.disciplinaCreditos = disciplinaCreditos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<AlunoDisciplinas> getAlunoDisciplinas() {
        return alunoDisciplinas;
    }

    public void setAlunoDisciplinas(List<AlunoDisciplinas> alunoDisciplinas) {
        this.alunoDisciplinas = alunoDisciplinas;
    }
}