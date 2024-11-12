package br.ufac.edgeneoapi.model;

import jakarta.persistence.*;

@Entity
public class DadosBrutos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String codCurso;

    @Column(nullable = false)
    private String nomeCurso;

    @Column(nullable = false)
    private Integer anoInicioPesquisa;

    @Column(nullable = false)
    private String status;

    private String localArquivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getAnoInicioPesquisa() {
        return anoInicioPesquisa;
    }

    public void setAnoInicioPesquisa(Integer anoInicioPesquisa) {
        this.anoInicioPesquisa = anoInicioPesquisa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalArquivo() {
        return localArquivo;
    }

    public void setLocalArquivo(String localArquivo) {
        this.localArquivo = localArquivo;
    }

    
}
