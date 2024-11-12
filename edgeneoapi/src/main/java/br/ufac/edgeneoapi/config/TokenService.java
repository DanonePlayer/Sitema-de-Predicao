package br.ufac.edgeneoapi.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.ufac.edgeneoapi.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private final InMemoryTokenBlacklist tokenBlacklist;

    public TokenService(InMemoryTokenBlacklist tokenBlacklist) {
        this.tokenBlacklist = tokenBlacklist;  // Injetando a dependência corretamente
    }

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    // Gera a data de expiração do token
    private Instant generateExpirationDate() {
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(60);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zoneDateTime = dateTime.atZone(zoneId);
        return zoneDateTime.toInstant();
    }

    // Gera um token JWT para o usuário fornecido
    public String generateToken(Usuario usuario) {
        Algorithm alg = Algorithm.HMAC256(secret);
        String token = JWT.create()
                          .withIssuer("EDGENEO")
                          .withSubject(usuario.getEmail())
                          .withClaim("id", usuario.getId())
                          .withClaim("nome", usuario.getNome())
                          .withClaim("email", usuario.getEmail())
                          .withClaim("tipo", usuario.getTipo().name())
                          .withClaim("dataLimiteRenovacao", LocalDate.now().toString())
                          .withExpiresAt(generateExpirationDate())
                          .sign(alg);
        logger.info("Token gerado para o usuário {}: {}", usuario.getEmail(), token);
        return token;
    }

    // Valida o token JWT fornecido e retorna o email do usuário
    public String validateToken(String token) {
        if (tokenBlacklist.isTokenBlacklisted(token)) {
            logger.warn("Token inválido (blacklisted): {}", token);
            throw new RuntimeException("Token inválido.");
        }
        try {
            logger.info("Validando token: {}", token);
            Algorithm alg = Algorithm.HMAC256(secret);
            DecodedJWT decodedToken = JWT.require(alg)
                                         .withIssuer("EDGENEO")
                                         .build()
                                         .verify(token);
            logger.info("Token válido. Email extraído: {}", decodedToken.getSubject());
            return decodedToken.getSubject();
        } catch (Exception e) {
            logger.error("Erro ao validar o token: {}", e.getMessage());
            throw new RuntimeException("Erro ao validar o token: " + e.getMessage());
        }
    }

    // Método opcional: extrai o token da requisição HTTP (Authorization Header)
    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove o prefixo "Bearer " para obter o token
        }
        return null;
    }

    // Adiciona o token na blacklist com a data de expiração
    public void blacklistToken(String token) {
        // Aqui você define a data de expiração do token que será adicionada à blacklist
        Instant expiration = Instant.now().plusSeconds(3600); // Exemplo de 1 hora para expiração
        tokenBlacklist.blacklistToken(token, expiration);
        logger.info("Token adicionado à blacklist: {}", token);
    }
}
