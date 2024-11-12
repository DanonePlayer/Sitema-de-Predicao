package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.AlunoDisciplinasDto;
import br.ufac.edgeneoapi.dto.AlunoDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.AlunoMapper;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.model.AlunoDisciplinas;
import br.ufac.edgeneoapi.model.Curso;
import br.ufac.edgeneoapi.model.Usuario;
import br.ufac.edgeneoapi.repository.AlunoDisciplinasRepository;
import br.ufac.edgeneoapi.repository.AlunoRepository;
import br.ufac.edgeneoapi.repository.CursoRepository;
import br.ufac.edgeneoapi.repository.DisciplinaRepository;
import br.ufac.edgeneoapi.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AlunoService implements IService<AlunoDto> {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoDisciplinasRepository alunoDisciplinasRepository;

    @Override
    public List<AlunoDto> getAll() {
        return alunoRepository.findAll()
                .stream()
                .map(alunoMapper::toAlunoDto)
                .collect(Collectors.toList());
    }

    public AlunoDto save(AlunoDto alunoDto) {
        Aluno aluno = alunoMapper.toAluno(alunoDto);

        Curso curso = cursoRepository.findById(alunoDto.cursoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + alunoDto.cursoId()));

        Usuario usuario = usuarioRepository.findById(alunoDto.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + alunoDto.usuarioId()));
        
        usuario.setCadastroPessoalCompleto(true);
        usuarioRepository.save(usuario);
        aluno.setCurso(curso);
        aluno.setUsuario(usuario);

        return alunoMapper.toAlunoDto(alunoRepository.save(aluno));
    }

    @Override
    public AlunoDto getById(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id));
        return alunoMapper.toAlunoDto(aluno);
    }

    @Override
    public void delete(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id));
        alunoRepository.delete(aluno);
    }

    public Aluno adicionarDisciplinasAoAluno(AlunoDisciplinasDto alunoDisciplinasDto, Long usuarioId) {
        // Buscar o aluno pelo ID do usuário
        Aluno aluno = alunoRepository.findByUsuarioId(usuarioId).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado para o usuário com ID: " + usuarioId));

        if (alunoDisciplinasDto.disciplinas().size() != alunoDisciplinasDto.notas().size()) {
          throw new IllegalArgumentException("O número de disciplinas deve ser igual ao número de notas.");
      }
      
        List<AlunoDisciplinas> alunoDisciplinas = IntStream.range(0, alunoDisciplinasDto.disciplinas().size())
        .mapToObj(i -> new AlunoDisciplinas(
            aluno,
            disciplinaRepository.findById(alunoDisciplinasDto.disciplinas().get(i).id())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina não encontrada")),
            alunoDisciplinasDto.notas().get(i), // Associa a nota à disciplina
            alunoDisciplinasDto.anoIngresso(),
            alunoDisciplinasDto.formaIngresso(),
            alunoDisciplinasDto.periodoAtual(),
            alunoDisciplinasDto.totalFaltas().get(i), // Associa o total de faltas à disciplina
            alunoDisciplinasDto.situacao().get(i) // Associa a situação à disciplina
        ))
        .collect(Collectors.toList());
        // Associar as disciplinas ao aluno
        alunoDisciplinasRepository.saveAll(alunoDisciplinas);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId));
        usuario.setCadastroHistoricoCompleto(true);
        usuarioRepository.save(usuario);
        // Salvar o aluno e as associações
        return alunoRepository.save(aluno);
    }
}
