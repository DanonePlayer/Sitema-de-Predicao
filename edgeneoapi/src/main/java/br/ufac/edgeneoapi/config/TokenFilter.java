package br.ufac.edgeneoapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private final TokenService tokenService;
    private final PerfilUsuarioService perfilUsuarioService;

    public TokenFilter(TokenService tokenService, PerfilUsuarioService perfilUsuarioService) {
        this.tokenService = tokenService;
        this.perfilUsuarioService = perfilUsuarioService;
    }

    // Extrai o token JWT do cabeçalho Authorization
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.info("Valor do cabeçalho Authorization recebido: {}", authHeader);

        // Verifica se o cabeçalho está presente e no formato correto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Nenhum cabeçalho Authorization encontrado ou formato incorreto.");
            return null;
        }

        String token = authHeader.replace("Bearer ", "");
        logger.info("Token extraído: {}", token);  // Log do token extraído
        return token;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o token do cabeçalho Authorization
        String token = recoverToken(request);
        logger.info("Token encontrado na requisição: {}", token);

        if (token != null) {
            try {
                // Valida o token e obtém o email do usuário
                String email = tokenService.validateToken(token);
                logger.info("Email extraído do token: {}", email);

                // Carrega o usuário pelo email
                UserDetails usuario = perfilUsuarioService.loadUserByUsername(email);
                if (usuario != null) {
                    logger.info("Usuário encontrado e autenticado: {}", usuario.getUsername());

                    // Cria o objeto de autenticação e insere no contexto de segurança
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.info("Autenticação inserida no contexto de segurança.");
                } else {
                    logger.error("Usuário não encontrado para o email: {}", email);
                }
            } catch (Exception e) {
                logger.error("Erro ao validar o token: {}", e.getMessage());
            }
        } else {
            logger.warn("Nenhum token presente na requisição.");
        }

        // Continua a execução do próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }
}
