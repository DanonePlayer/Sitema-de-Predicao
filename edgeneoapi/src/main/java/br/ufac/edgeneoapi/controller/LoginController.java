package br.ufac.edgeneoapi.controller;

import br.ufac.edgeneoapi.config.PerfilUsuario;
import br.ufac.edgeneoapi.config.TokenService;
// import br.ufac.edgeneoapi.dto.LoginDto;
import br.ufac.edgeneoapi.model.Usuario;
import br.ufac.edgeneoapi.service.UsuarioService;

import java.time.LocalDate;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Base64.Decoder;

// import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;



@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class LoginController {

    // private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, UsuarioService usuarioService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        // Autentica com email e senha
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha());
        Authentication auth = authenticationManager.authenticate(loginToken);
        
        // Obtém o perfil do usuário autenticado
        PerfilUsuario principal = (PerfilUsuario) auth.getPrincipal();
        Usuario usuarioAutenticado = usuarioService.findByEmail(principal.getEmail());

        // Verifica se o email do usuário está confirmado
        if (!usuarioAutenticado.getEmailConfirmado()) {
            return ResponseEntity.status(403).body("Email não confirmado. Verifique seu e-mail.");
        }

        // Gera o token JWT
        String token = tokenService.generateToken(usuarioAutenticado);

        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");
        DecodedJWT tokenDecodificado = JWT.decode(token);
        Claim claimDataLimite = tokenDecodificado.getClaim("dataLimiteRenovacao");
        LocalDate dataLimite = LocalDate.parse(claimDataLimite.asString());
        LocalDate hoje = LocalDate.now();
        if (hoje.isAfter(dataLimite)) {
            return ResponseEntity.badRequest().build();
        }

        String email = tokenDecodificado.getSubject();
        Usuario usuario = usuarioService.findByEmail(email);
        String tokenNovo = tokenService.generateToken(usuario);

        return ResponseEntity.ok(tokenNovo);
    }
}
