package br.ufac.edgeneoapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PerfilUsuarioService perfilUsuarioService;
    private final TokenFilter tokenFilter;


    public SecurityConfig(PerfilUsuarioService perfilUsuarioService, TokenFilter tokenFilter) {
        this.perfilUsuarioService = perfilUsuarioService;
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    UserDetailsService udService() {
        return perfilUsuarioService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(udService());  // Define o service para buscar o usuário por email
        authProvider.setPasswordEncoder(passwordEncoder());  // Define o encoder utilizado (BCrypt)
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Definindo configurações gerais de segurança
        // http.httpBasic(withDefaults()); // Ativa autenticação básica para compatibilidade
        http.cors(withDefaults());  // Habilita CORS com configuração padrão
        http.csrf(csrf -> csrf.disable());  // Desabilita proteção contra CSRF
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configuração da política de sessão para uso stateless   
        http.authorizeHttpRequests(authorize -> authorize // Autorização baseada em tipo de usuário
                .requestMatchers(HttpMethod.POST, "/login").permitAll()  // Permite acesso ao endpoint de login
                .requestMatchers(HttpMethod.POST, "/usuario/").permitAll()  // Permite acesso ao endpoint de inserir usuario
                .requestMatchers(HttpMethod.GET, "/usuario/confirmacao-email").permitAll()
                .requestMatchers(HttpMethod.POST, "/coordenador/{id}/upload-portaria").permitAll() // Permite acesso ao endpoint de coordenador
                .requestMatchers(HttpMethod.GET, "/usuario/cadastro-pessoal-completo/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/curso/").permitAll()
                .requestMatchers(HttpMethod.GET, "/treinamento/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/treinamento/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/nti-api/dados-alunos").permitAll()
                .requestMatchers("/oauth2/**", "/login/oauth2/**", "/oauth2/authorization/google").permitAll() // Permite o login com OAuth2
                // .requestMatchers("/admin/**").hasAuthority("ADMINISTRADOR")  // Restrição de acesso para ADMINISTRADOR
                // .requestMatchers("/aluno/**").hasAuthority("ALUNO")  // Restrição de acesso para ALUNO  
                .anyRequest().authenticated());  // Qualquer outra requisição deve ser autenticada
        // Configuração para autenticação OAuth2
            http.oauth2Login(oauth2 -> oauth2
            .loginPage("/oauth2/authorization/google")
            .defaultSuccessUrl("http://localhost:4200/cadastro", true)
            .userInfoEndpoint(userInfo -> userInfo.userService(perfilUsuarioService))
        );
            // Adiciona o filtro de autenticação JWT antes do filtro padrão do Spring Security    
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

