package br.ufac.edgeneoapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<Disciplina> disciplinas;

    @Column(nullable = false)
    private String cursoNome;

    @Column(nullable = false)
    private String cursoModalidade;

    @Column(nullable = false)
    private String cursoTurno;

    @Column(nullable = false, unique = true)
    private String codCurso;

    public Curso() {
    }

    public Curso(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getCursoModalidade() {
        return cursoModalidade;
    }

    public void setCursoModalidade(String cursoModalidade) {
        this.cursoModalidade = cursoModalidade;
    }

    public String getCursoTurno() {
        return cursoTurno;
    }

    public void setCursoTurno(String cursoTurno) {
        this.cursoTurno = cursoTurno;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }
}