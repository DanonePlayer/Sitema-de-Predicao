package br.ufac.edgeneoapi.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import br.ufac.edgeneoapi.enums.EPerfilUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Logs> logs;

    @Column(nullable = false)
    private String nome;
    
    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private Boolean emailConfirmado = false;

    @Column(unique = true)
    private String confirmacaoEmailToken;

    @Column(nullable = true)
    private Timestamp confirmacaoEmailExpiracao;
    
    @Column(nullable = false)
    private String senha;
    
    @Enumerated(EnumType.STRING)
    private EPerfilUsuario tipo;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dataCadastro;
    
    @Column(nullable = true)
    private String status;
    
    @Column(nullable = true)
    private Timestamp ultimoLogin;

    @Column(nullable = true)
    private Boolean cadastroPessoalCompleto;

    @Column(nullable = true)
    private Boolean cadastroHistoricoCompleto;

    @Column(nullable = true)
    private Boolean portariaAprovada;

    @Column(nullable = true)
    private Boolean cadastroComGoogle;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EPerfilUsuario getTipo() {
        return tipo;
    }

    public void setTipo(EPerfilUsuario tipo) {
        this.tipo = tipo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Timestamp ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public Boolean getCadastroPessoalCompleto() {
        return cadastroPessoalCompleto;
    }

    public void setCadastroPessoalCompleto(Boolean cadastroPessoalCompleto) {
        this.cadastroPessoalCompleto = cadastroPessoalCompleto;
    }

    public Boolean getCadastroHistoricoCompleto() {
        return cadastroHistoricoCompleto;
    }

    public void setCadastroHistoricoCompleto(Boolean cadastroHistoricoCompleto) {
        this.cadastroHistoricoCompleto = cadastroHistoricoCompleto;
    }

    public Boolean getPortariaAprovada() {
        return portariaAprovada;
    }

    public void setPortariaAprovada(Boolean portariaAprovada) {
        this.portariaAprovada = portariaAprovada;
    }

    public String getConfirmacaoEmailToken() {
        return confirmacaoEmailToken;
    }

    public void setConfirmacaoEmailToken(String confirmacaoEmailToken) {
        this.confirmacaoEmailToken = confirmacaoEmailToken;
    }

    public Timestamp getConfirmacaoEmailExpiracao() {
        return confirmacaoEmailExpiracao;
    }

    public void setConfirmacaoEmailExpiracao(Timestamp confirmacaoEmailExpiracao) {
        this.confirmacaoEmailExpiracao = confirmacaoEmailExpiracao;
    }

    public Boolean getEmailConfirmado() {
        return emailConfirmado;
    }

    public void setEmailConfirmado(Boolean emailConfirmado) {
        this.emailConfirmado = emailConfirmado;
    }

    public Boolean getCadastroComGoogle() {
        return cadastroComGoogle;
    }

    public void setCadastroComGoogle(Boolean cadastroComGoogle) {
        this.cadastroComGoogle = cadastroComGoogle;
    }
}