package br.ufac.edgeneoapi.config;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenBlacklist {

    private final Map<String, Instant> blacklist = new ConcurrentHashMap<>();

    // Adiciona o token à blacklist com a data de expiração
    public void blacklistToken(String token, Instant expiration) {
        blacklist.put(token, expiration);
    }

    // Verifica se o token está na blacklist e se já expirou
    public boolean isTokenBlacklisted(String token) {
        Instant expiration = blacklist.get(token);
        if (expiration == null) {
            return false;  // Não está na blacklist
        }

        // Verifica se o token já expirou
        if (Instant.now().isAfter(expiration)) {
            blacklist.remove(token);  // Remove o token expirado da blacklist
            return false;  // Expirado, não precisa ser considerado mais
        }

        return true;  // Está na blacklist e ainda é válido
    }
}
