package br.ufac.edgeneoapi.controller;

import br.ufac.edgeneoapi.config.TokenService;
import br.ufac.edgeneoapi.dto.UsuarioDto;
import br.ufac.edgeneoapi.enums.EPerfilUsuario;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.UsuarioMapper;
import br.ufac.edgeneoapi.model.Coordenador;
import br.ufac.edgeneoapi.model.Usuario;
import br.ufac.edgeneoapi.repository.CoordenadorRepository;
import br.ufac.edgeneoapi.repository.CursoRepository;
import br.ufac.edgeneoapi.repository.UsuarioRepository;
import br.ufac.edgeneoapi.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/usuario")
public class UsuarioController implements IController<UsuarioDto> {

    private final UsuarioService servico;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final UsuarioMapper usuarioMapper;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService servico, UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, CursoRepository cursoRepository, CoordenadorRepository coordenadorRepository, TokenService tokenService) {
        this.servico = servico;
        this.usuarioRepository = usuarioRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.usuarioMapper = usuarioMapper;
        this.cursoRepository = cursoRepository;
        this.tokenService = tokenService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<UsuarioDto>> get(@RequestParam(required = false) String termoBusca) {
        List<UsuarioDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> get(@PathVariable Long id) {
        UsuarioDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody UsuarioDto usuarioDto) {
        try {
            // Converte o DTO para a entidade `Usuario` antes de salvar
            Usuario usuario = usuarioMapper.toUsuario(usuarioDto);  // Certifique-se de que o `usuarioMapper` está mapeando corretamente o DTO para a entidade
            
            // Valida e configura os dados adicionais do `Usuario`
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setSenha(passwordEncoder.encode(usuarioDto.senha()));  // Criptografa a senha antes de salvar

            // Salva o `Usuario` no banco de dados
            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            // Envia e-mail de confirmação
            servico.enviarEmailConfirmacao(usuarioSalvo);

            // Se o tipo de usuário for `COORDENADOR`, cria a entidade `Coordenador` associada
            if (usuarioSalvo.getTipo() == EPerfilUsuario.COORDENADOR) {
                Coordenador coordenador = new Coordenador();
                coordenador.setUsuario(usuarioSalvo);  // Associa o `Usuario` ao `Coordenador`
                
                // Configura as informações do `Coordenador` a partir do `UsuarioDto`
                coordenador.setCurso(cursoRepository.findById(usuarioDto.coordenador().curso_id())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + usuarioDto.coordenador().curso_id())));
                coordenador.setPortaria(usuarioDto.coordenador().portaria());
                coordenador.setStatus(usuarioDto.coordenador().status());

                // Salva a entidade `Coordenador` no banco de dados
                Coordenador coordenadorSalvo = coordenadorRepository.save(coordenador);

                // Retorna o ID do `Usuario` e do `Coordenador` para o frontend
                Map<String, Object> response = new HashMap<>();
                response.put("usuarioId", usuarioSalvo.getId());
                response.put("coordenadorId", coordenadorSalvo.getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

            // Retorna o ID do usuário criado para outros tipos de usuário (não coordenador)
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo.getId());
        } catch (Exception ex) {
            // Captura e retorna a mensagem de erro com mais detalhes
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao tentar salvar o usuário: " + ex.getMessage());
        }
    }
    @Override
    @PutMapping("/")
    public ResponseEntity<UsuarioDto> update(@RequestBody UsuarioDto objeto) {
        UsuarioDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable Long id, @RequestBody UsuarioDto objeto) {
        // Verifique se o usuário existe antes de tentar atualizar
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id);
        }
        
        // Realize a atualização do usuário
        UsuarioDto registroAtualizado = servico.update(id, objeto); // Certifique-se de que o serviço lida com a atualização

        return ResponseEntity.ok(registroAtualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDto> getUsuarioLogado(@RequestParam String email) {
        try {
            Usuario usuario = servico.findByEmail(email);
            UsuarioDto usuarioDto = servico.toUsuarioDto(usuario);
            return ResponseEntity.ok(usuarioDto);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @GetMapping("/cadastro-pessoal-completo/{id}")
    public ResponseEntity<Boolean> isCadastroPessoalCompleto(@PathVariable Long id) {
        try {
            boolean completo = servico.isCadastroPessoalCompleto(id);
            return ResponseEntity.ok(completo);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @GetMapping("/cadastro-historico-completo/{id}")
    public ResponseEntity<Boolean> isCadastroHistoricoCompleto(@PathVariable Long id) {
        try {
            boolean completo = servico.isCadastroHistoricoCompleto(id);
            return ResponseEntity.ok(completo);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");  // Extrai o token do cabeçalho
        tokenService.blacklistToken(token);  // Adiciona o token à blacklist
        System.out.println("Token adicionado à blacklist: " + token);
        return ResponseEntity.ok("Logout realizado com sucesso.");
        
    }

    @GetMapping("/confirmacao-email")
    public ResponseEntity<String> confirmarEmail(@RequestParam("token") String token) {
        try {
            boolean isConfirmed = servico.confirmarEmail(token);
            if (isConfirmed) {
                return ResponseEntity.ok("E-mail confirmado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ocorreu um erro ao confirmar o e-mail. Tente novamente.");
        }
    }    
}
