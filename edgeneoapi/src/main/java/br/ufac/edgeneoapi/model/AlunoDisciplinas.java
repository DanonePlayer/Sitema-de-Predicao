package br.ufac.edgeneoapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "aluno_disciplina")
public class AlunoDisciplinas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private Double nota;

    // @Temporal(TemporalType.DATE)
    private String anoIngresso;

    private String periodoAtual;

    // @Column(nullable = false)
    private String formaIngresso;

    private Integer TotalFaltas;

    private String Situacao;


    public String getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(String anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public String getPeriodoAtual() {
        return periodoAtual;
    }

    public void setPeriodoAtual(String periodoAtual) {
        this.periodoAtual = periodoAtual;
    }

    public String getFormadeIngresso() {
        return formaIngresso;
    }

    public void setFormadeIngresso(String formadeIngresso) {
        this.formaIngresso = formadeIngresso;
    }

    public AlunoDisciplinas() {
    }

    public AlunoDisciplinas(Aluno aluno, Disciplina disciplina, Double nota, String anoIngresso, String formaIngresso, String periodoAtual, Integer totalFaltas, String situacao) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.anoIngresso = anoIngresso;
        this.formaIngresso = formaIngresso;
        this.periodoAtual = periodoAtual;
        this.TotalFaltas = totalFaltas;
        this.Situacao = situacao;
    }
    
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormaIngresso() {
        return formaIngresso;
    }

    public void setFormaIngresso(String formaIngresso) {
        this.formaIngresso = formaIngresso;
    }

    public Integer getTotalFaltas() {
        return TotalFaltas;
    }

    public void setTotalFaltas(Integer totalFaltas) {
        TotalFaltas = totalFaltas;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }


}
