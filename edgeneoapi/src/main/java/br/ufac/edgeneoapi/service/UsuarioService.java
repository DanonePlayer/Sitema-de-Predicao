package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.UsuarioDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.UsuarioMapper;
import br.ufac.edgeneoapi.model.Administrador;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.model.Coordenador;
import br.ufac.edgeneoapi.model.Usuario;
import br.ufac.edgeneoapi.repository.AdministradorRepository;
import br.ufac.edgeneoapi.repository.AlunoRepository;
import br.ufac.edgeneoapi.repository.CoordenadorRepository;
import br.ufac.edgeneoapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IService<UsuarioDto>, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<UsuarioDto> getAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toUsuarioDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto getById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
        return usuarioMapper.toUsuarioDto(usuario);
    }
    // criptografa a senha do usuário
    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) {
        validarEmailUnico(usuarioDto.email());
        Usuario usuario = usuarioMapper.toUsuario(usuarioDto);
        // Verifica se é uma atualização ou uma criação de novo usuário
        if (usuario.getSenha() == null || usuarioDto.senha().isBlank()) {
            Long id = usuario.getId();
            UsuarioDto usuDto = getById(id);
            if (usuDto != null) {
                usuario.setSenha(usuario.getSenha());
            }
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Salva o usuário na tabela específica com base no tipo
        switch (usuarioDto.tipo()) {
            case ALUNO:
                Aluno aluno = new Aluno();
                aluno.setUsuario(savedUsuario);
                alunoRepository.save(aluno);
                break;

            case COORDENADOR:
                Coordenador coordenador = new Coordenador();
                coordenador.setUsuario(savedUsuario);
                coordenadorRepository.save(coordenador);
                break;

            case ADMINISTRADOR:
                Administrador administrador = new Administrador();
                administrador.setUsuario(savedUsuario);
                administradorRepository.save(administrador);
                break;

            default:
                throw new IllegalArgumentException("Tipo de usuário inválido: " + usuarioDto.tipo());
        }

        return usuarioMapper.toUsuarioDto(savedUsuario);
    }

    @Override
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
        usuarioRepository.delete(usuario);
    }

    public void validarEmailUnico(String email) throws RecursoNaoEncontradoException {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RecursoNaoEncontradoException("O email já está em uso.");
        }
    }

    public boolean isCadastroPessoalCompleto(Long id) {
        Usuario usuario = findById(id);
        return usuario.getCadastroPessoalCompleto();
    }

    public boolean isCadastroHistoricoCompleto(Long id) {
        Usuario usuario = findById(id);
        return usuario.getCadastroHistoricoCompleto();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Cria um UserDetails a partir das informações do usuário
        return User.builder()
                .username(usuario.getEmail())   // Email será usado como nome de usuário
                .password(usuario.getSenha())   // Senha criptografada
                .build();
    }

    // Método para realizar o login e verificar os cenários
    public UsuarioDto login(String email, String senha) {
        // Verificar se o usuário existe
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o email: " + email));

        // Verificar se a senha é válida
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RecursoNaoEncontradoException("Senha inválida");
        }

        
        usuario.getCadastroPessoalCompleto();
        usuario.getCadastroHistoricoCompleto();
        usuario.getPortariaAprovada();


        UsuarioDto usuarioDto = usuarioMapper.toUsuarioDto(usuario);

        return usuarioDto;
    }

    // public String getNomeUsuarioById(Long id) {
    //     Usuario usuario = usuarioRepository.findById(id)
    //         .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
    //     return usuario.getNome();
    // }
    
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o email: " + email));
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));
    }

    public UsuarioDto toUsuarioDto(Usuario usuario) {
        return usuarioMapper.toUsuarioDto(usuario);
    }

    public UsuarioDto update(Long id, UsuarioDto usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
        
        // Atualize os campos do usuário existente com os novos valores do DTO
        usuarioExistente.setNome(usuarioDto.nome());
        usuarioExistente.setEmail(usuarioDto.email());
        usuarioExistente.setTipo(usuarioDto.tipo());
        usuarioExistente.setPortariaAprovada(usuarioDto.portariaAprovada());
        // Adicione mais atualizações conforme necessário
    
        // Salve o usuário atualizado no repositório
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
    
        // Retorne o DTO atualizado
        return usuarioMapper.toUsuarioDto(usuarioAtualizado);
    }

    // Método para gerar e enviar o token de confirmação de e-mail
    public void enviarEmailConfirmacao(Usuario usuario) {
        if (usuario.getConfirmacaoEmailToken() == null) {
            String token = UUID.randomUUID().toString();
            usuario.setConfirmacaoEmailToken(token);
            usuario.setConfirmacaoEmailExpiracao(new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // expira em 24 horas
            usuarioRepository.save(usuario);
    
            // Para depuração: imprime o token gerado
            System.out.println("TEU TOKEN QUE TU TA MANDANDOA AE BIX : " + token);
        }
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(usuario.getEmail());
            mailMessage.setSubject("Confirme seu e-mail");
            mailMessage.setText("Para confirmar sua conta, clique no link: " +
                "http://localhost:4200/confirmacao-email?token=" + usuario.getConfirmacaoEmailToken());
            System.out.println("Enviando e-mail de confirmação para: " + usuario.getEmail());
            mailSender.send(mailMessage);
            System.out.println("E-mail enviado com sucesso.");
        }catch(Exception e){
            System.out.println("Erro ao enviar e-mail de confirmação: " + e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    // Método para confirmar o e-mail com o token recebido
    public boolean confirmarEmail(String token) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByConfirmacaoEmailToken(token);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Checa se a data de expiração ainda é válida
            if (usuario.getConfirmacaoEmailExpiracao().after(new Date())) {
                System.out.println(usuario.getConfirmacaoEmailToken());
                System.out.println(token);
                usuario.setEmailConfirmado(true);
                usuario.setConfirmacaoEmailToken(null); // Limpa o token após confirmação
                usuario.setConfirmacaoEmailExpiracao(null);
                usuarioRepository.save(usuario);
                return true;
            }
            else {
                System.out.println("Token de confirmação de e-mail expirado.");
                return false;
            }
        }
        if (usuarioOptional.isEmpty()) {
            return true;
        }
        else {
            System.out.println("Token de confirmação de e-mail inválido.");
            return false;
        }
    }
    
}
