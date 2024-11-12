package br.ufac.edgeneoapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class DadosProcessados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso Curso;

    @OneToOne
    @JoinColumn(name = "dados_brutos_id", nullable = false)
    private DadosBrutos dadosBrutos;

    @Column(nullable = false)
    private Integer idadeInicioCurso;

    @Column(nullable = false)
    private Integer duracaoCurso;

    @Column(nullable = false)
    private Float periodoPorcentagem;

    @Column(nullable = false)
    private Float percentualFrequenciaPeriodo;

    @Column(nullable = false)
    private Float historicoRecenteCr;

    @Column(nullable = false)
    private Float historicoRecentePercentualFrequencia;

    @Column(nullable = false)
    private Float historicoRecenteMedia;

    @Column(nullable = false)
    private Integer historicoRecentesAprovadasQuantidade;

    @Column(nullable = false)
    private Float historicoRecenteAprovadasMedia;

    @Column(nullable = false)
    private Integer historicoRecenteReprovadasQuantidade;

    @Column(nullable = false)
    private Float historicoRecenteReprovadasMedia;

    @Column(nullable = false)
    private Integer historicoRecenteCursadas;

    @Column(nullable = false)
    private String historicoRecenteClasseRendimento;
    
    @Column(nullable = false)
    private String historicoRecenteClasseDesempenho;

    @Column(nullable = false)
    private String historicoRecenteClasseDisciplinasCursadas;

    @Column(nullable = false)
    private Float historicoGeralCr;

    @Column(nullable = false)
    private Float historicoGeralPercentualFrequencia;

    @Column(nullable = false)
    private Float historicoGeralMedia;

    @Column(nullable = false)
    private Integer historicoGeralAprovadasQuantidade;

    @Column(nullable = false)
    private Float historicoGeralAprovadasMedia;

    @Column(nullable = false)
    private Integer historicoGeralReprovadasQuantidade;

    @Column(nullable = false)
    private Float historicoGeralReprovadasMedia;

    @Column(nullable = false)
    private Integer historicoGeralCursadas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return Curso;
    }

    public void setCurso(Curso curso) {
        Curso = curso;
    }

    public DadosBrutos getDadosBrutos() {
        return dadosBrutos;
    }

    public void setDadosBrutos(DadosBrutos dadosBrutos) {
        this.dadosBrutos = dadosBrutos;
    }

    public Integer getIdadeInicioCurso() {
        return idadeInicioCurso;
    }

    public void setIdadeInicioCurso(Integer idadeInicioCurso) {
        this.idadeInicioCurso = idadeInicioCurso;
    }

    public Integer getDuracaoCurso() {
        return duracaoCurso;
    }

    public void setDuracaoCurso(Integer duracaoCurso) {
        this.duracaoCurso = duracaoCurso;
    }

    public Float getPeriodoPorcentagem() {
        return periodoPorcentagem;
    }

    public void setPeriodoPorcentagem(Float periodoPorcentagem) {
        this.periodoPorcentagem = periodoPorcentagem;
    }

    public Float getPercentualFrequenciaPeriodo() {
        return percentualFrequenciaPeriodo;
    }

    public void setPercentualFrequenciaPeriodo(Float percentualFrequenciaPeriodo) {
        this.percentualFrequenciaPeriodo = percentualFrequenciaPeriodo;
    }

    public Float getHistoricoRecenteCr() {
        return historicoRecenteCr;
    }

    public void setHistoricoRecenteCr(Float historicoRecenteCr) {
        this.historicoRecenteCr = historicoRecenteCr;
    }

    public Float getHistoricoRecentePercentualFrequencia() {
        return historicoRecentePercentualFrequencia;
    }

    public void setHistoricoRecentePercentualFrequencia(Float historicoRecentePercentualFrequencia) {
        this.historicoRecentePercentualFrequencia = historicoRecentePercentualFrequencia;
    }

    public Float getHistoricoRecenteMedia() {
        return historicoRecenteMedia;
    }

    public void setHistoricoRecenteMedia(Float historicoRecenteMedia) {
        this.historicoRecenteMedia = historicoRecenteMedia;
    }

    public Integer getHistoricoRecentesAprovadasQuantidade() {
        return historicoRecentesAprovadasQuantidade;
    }

    public void setHistoricoRecentesAprovadasQuantidade(Integer historicoRecentesAprovadasQuantidade) {
        this.historicoRecentesAprovadasQuantidade = historicoRecentesAprovadasQuantidade;
    }

    public Float getHistoricoRecenteAprovadasMedia() {
        return historicoRecenteAprovadasMedia;
    }

    public void setHistoricoRecenteAprovadasMedia(Float historicoRecenteAprovadasMedia) {
        this.historicoRecenteAprovadasMedia = historicoRecenteAprovadasMedia;
    }

    public Integer getHistoricoRecenteReprovadasQuantidade() {
        return historicoRecenteReprovadasQuantidade;
    }

    public void setHistoricoRecenteReprovadasQuantidade(Integer historicoRecenteReprovadasQuantidade) {
        this.historicoRecenteReprovadasQuantidade = historicoRecenteReprovadasQuantidade;
    }

    public Float getHistoricoRecenteReprovadasMedia() {
        return historicoRecenteReprovadasMedia;
    }

    public void setHistoricoRecenteReprovadasMedia(Float historicoRecenteReprovadasMedia) {
        this.historicoRecenteReprovadasMedia = historicoRecenteReprovadasMedia;
    }

    public Integer getHistoricoRecenteCursadas() {
        return historicoRecenteCursadas;
    }

    public void setHistoricoRecenteCursadas(Integer historicoRecenteCursadas) {
        this.historicoRecenteCursadas = historicoRecenteCursadas;
    }

    public String getHistoricoRecenteClasseRendimento() {
        return historicoRecenteClasseRendimento;
    }

    public void setHistoricoRecenteClasseRendimento(String historicoRecenteClasseRendimento) {
        this.historicoRecenteClasseRendimento = historicoRecenteClasseRendimento;
    }

    public String getHistoricoRecenteClasseDesempenho() {
        return historicoRecenteClasseDesempenho;
    }

    public void setHistoricoRecenteClasseDesempenho(String historicoRecenteClasseDesempenho) {
        this.historicoRecenteClasseDesempenho = historicoRecenteClasseDesempenho;
    }

    public String getHistoricoRecenteClasseDisciplinasCursadas() {
        return historicoRecenteClasseDisciplinasCursadas;
    }

    public void setHistoricoRecenteClasseDisciplinasCursadas(String historicoRecenteClasseDisciplinasCursadas) {
        this.historicoRecenteClasseDisciplinasCursadas = historicoRecenteClasseDisciplinasCursadas;
    }

    public Float getHistoricoGeralCr() {
        return historicoGeralCr;
    }

    public void setHistoricoGeralCr(Float historicoGeralCr) {
        this.historicoGeralCr = historicoGeralCr;
    }

    public Float getHistoricoGeralPercentualFrequencia() {
        return historicoGeralPercentualFrequencia;
    }

    public void setHistoricoGeralPercentualFrequencia(Float historicoGeralPercentualFrequencia) {
        this.historicoGeralPercentualFrequencia = historicoGeralPercentualFrequencia;
    }

    public Float getHistoricoGeralMedia() {
        return historicoGeralMedia;
    }

    public void setHistoricoGeralMedia(Float historicoGeralMedia) {
        this.historicoGeralMedia = historicoGeralMedia;
    }

    public Integer getHistoricoGeralAprovadasQuantidade() {
        return historicoGeralAprovadasQuantidade;
    }

    public void setHistoricoGeralAprovadasQuantidade(Integer historicoGeralAprovadasQuantidade) {
        this.historicoGeralAprovadasQuantidade = historicoGeralAprovadasQuantidade;
    }

    public Float getHistoricoGeralAprovadasMedia() {
        return historicoGeralAprovadasMedia;
    }

    public void setHistoricoGeralAprovadasMedia(Float historicoGeralAprovadasMedia) {
        this.historicoGeralAprovadasMedia = historicoGeralAprovadasMedia;
    }

    public Integer getHistoricoGeralReprovadasQuantidade() {
        return historicoGeralReprovadasQuantidade;
    }

    public void setHistoricoGeralReprovadasQuantidade(Integer historicoGeralReprovadasQuantidade) {
        this.historicoGeralReprovadasQuantidade = historicoGeralReprovadasQuantidade;
    }

    public Float getHistoricoGeralReprovadasMedia() {
        return historicoGeralReprovadasMedia;
    }

    public void setHistoricoGeralReprovadasMedia(Float historicoGeralReprovadasMedia) {
        this.historicoGeralReprovadasMedia = historicoGeralReprovadasMedia;
    }

    public Integer getHistoricoGeralCursadas() {
        return historicoGeralCursadas;
    }

    public void setHistoricoGeralCursadas(Integer historicoGeralCursadas) {
        this.historicoGeralCursadas = historicoGeralCursadas;
    }

    
    
}
