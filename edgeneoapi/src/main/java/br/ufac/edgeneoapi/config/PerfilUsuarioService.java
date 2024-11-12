package br.ufac.edgeneoapi.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import br.ufac.edgeneoapi.enums.EPerfilUsuario;
import br.ufac.edgeneoapi.model.Usuario;
import br.ufac.edgeneoapi.repository.UsuarioRepository;
import br.ufac.edgeneoapi.service.UsuarioService;

import java.sql.Timestamp;
import java.util.Collections;
@Service
public class PerfilUsuarioService extends DefaultOAuth2UserService  implements UserDetailsService {

    private final UsuarioService servico;
    private final UsuarioRepository usuarioRepository;

    public PerfilUsuarioService(UsuarioService servico, UsuarioRepository usuarioRepository) {
        this.servico = servico;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = servico.findByEmail(email);  // Ajuste aqui para buscar pelo email
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }
        return new PerfilUsuario(usuario);  // Retorna o UserDetails personalizado
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        // Salva o usuário no banco caso ainda não exista
        Usuario usuario = usuarioRepository.findByEmail(email).orElseGet(() -> {
            Usuario newUser = new Usuario();
            newUser.setEmail(email);
            newUser.setNome(oAuth2User.getAttribute("name"));
            newUser.setEmailConfirmado(true);
            newUser.setCadastroComGoogle(true);
            // newUser.setSenha("Cadastro com OAuth2");
            // newUser.setTipo(EPerfilUsuario.ALUNO);
            // newUser.setStatus("ATIVO");
            // newUser.setPortariaAprovada(false);
            // newUser.setCadastroPessoalCompleto(false);
            // newUser.setCadastroHistoricoCompleto(false);
            // newUser.setUltimoLogin(new Timestamp(System.currentTimeMillis()));
            // newUser.setConfirmacaoEmailExpiracao( new Timestamp(System.currentTimeMillis()));
            return usuarioRepository.save(newUser);
        });

    
        return new DefaultOAuth2User(
            Collections.singleton(new OAuth2UserAuthority(oAuth2User.getAttributes())),
            oAuth2User.getAttributes(),
                "email"
        );
    }
}
    