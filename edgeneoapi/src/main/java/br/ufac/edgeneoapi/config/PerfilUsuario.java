package br.ufac.edgeneoapi.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufac.edgeneoapi.model.Usuario;

public class PerfilUsuario implements UserDetails {

    private final Usuario usuario;

    public PerfilUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(usuario.getTipo().name());
        return Arrays.asList(auth);
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getEmail() {
        return usuario.getEmail();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Implementar depois se necessário
    // @Override
    // public boolean isEnabled() {
    //     return usuario.isAtivo();
    // }
    
}