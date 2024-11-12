package br.ufac.edgeneoapi.controller;

// import java.util.HashMap;
import java.util.List;
// import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.AlunoDisciplinasDto;
import br.ufac.edgeneoapi.dto.AlunoDto;
import br.ufac.edgeneoapi.mapper.AlunoMapper;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.service.AlunoService;
import br.ufac.edgeneoapi.service.UsuarioService;

@RestController
@RequestMapping("/aluno")
public class AlunoController implements IController<AlunoDto> {

    private final AlunoService servico;

    // private final UsuarioService usuarioService;

    public AlunoController(AlunoService servico, UsuarioService usuarioService) {
        this.servico = servico;
        // this.usuarioService = usuarioService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<AlunoDto>> get(@RequestParam(required = false) String termoBusca) {
        List<AlunoDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> get(@PathVariable Long id) {
        AlunoDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<AlunoDto> insert(@RequestBody AlunoDto objeto) {
        AlunoDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<AlunoDto> update(@RequestBody AlunoDto objeto) {
        AlunoDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @PutMapping("/{alunoId}/disciplinas")
    public ResponseEntity<AlunoDto> associarDisciplinas(
            @RequestBody AlunoDisciplinasDto alunoDisciplinasDto,
            @PathVariable Long alunoId) {
                
        Aluno alunoAtualizado = servico.adicionarDisciplinasAoAluno(alunoDisciplinasDto, alunoId);

        // Converte o aluno atualizado para DTO e retorna como resposta
        AlunoDto alunoDto = AlunoMapper.INSTANCE.toAlunoDto(alunoAtualizado);
        return ResponseEntity.ok(alunoDto);
    }

}
