package br.ufac.edgeneoapi.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "aluno")
    @JsonIgnore
    private List<TesteAluno> testeAlunos;
    
    @OneToMany(mappedBy = "aluno")
    private List<AlunoDisciplinas> alunoDisciplinas;

    // @Column(nullable = false)
    private String sobrenome;

    // @Column(nullable = false)
    private Boolean possuiDeficiencia;

    // @Column(nullable = false)
    private String deficiencias;

    // @Column(nullable = false)
    private String cep;

    // @Column(nullable = false)
    private String rua;

    // @Column(nullable = false)
    private String bairro;

    // @Column(nullable = false)
    private String cidade;

    // @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String estadoCivil;

    @Column(nullable = false)
    private String genero;

    @Temporal(TemporalType.DATE)
    private Date dtNascimento;

    @Column(nullable = false)
    private String naturalidade;

    // @Column(nullable = true) pode ser nulo
    private String etnia;

    // @Column(nullable = false)
    private String bolsas;

    // @Temporal(TemporalType.DATE)
    private Date peridoBolsainicio;

    // @Temporal(TemporalType.DATE)
    private Date peridoBolsafim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<TesteAluno> getTesteAlunos() {
        return testeAlunos;
    }

    public void setTesteAlunos(List<TesteAluno> testeAlunos) {
        this.testeAlunos = testeAlunos;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Boolean getPossuiDeficiencia() {
        return possuiDeficiencia;
    }

    public void setPossuiDeficiencia(Boolean possuiDeficiencia) {
        this.possuiDeficiencia = possuiDeficiencia;
    }

    public String getDeficiencias() {
        return deficiencias;
    }

    public void setDeficiencias(String deficiencias) {
        this.deficiencias = deficiencias;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getBolsas() {
        return bolsas;
    }

    public void setBolsas(String bolsas) {
        this.bolsas = bolsas;
    }

    public Date getPeridoBolsainicio() {
        return peridoBolsainicio;
    }

    public void setPeridoBolsainicio(Date peridoBolsainicio) {
        this.peridoBolsainicio = peridoBolsainicio;
    }

    public Date getPeridoBolsafim() {
        return peridoBolsafim;
    }

    public void setPeridoBolsafim(Date peridoBolsaFim) {
        this.peridoBolsafim = peridoBolsaFim;
    }

    public List<AlunoDisciplinas> getAlunoDisciplinas() {
        return alunoDisciplinas;
    }

    public void setAlunoDisciplinas(List<AlunoDisciplinas> alunoDisciplinas) {
        this.alunoDisciplinas = alunoDisciplinas;
    }
}
