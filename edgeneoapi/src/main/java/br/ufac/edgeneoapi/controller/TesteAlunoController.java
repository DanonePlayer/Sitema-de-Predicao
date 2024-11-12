package br.ufac.edgeneoapi.controller;

import br.ufac.edgeneoapi.dto.TesteAlunoDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.service.TesteAlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testeAluno")
public class TesteAlunoController implements IController<TesteAlunoDto> {

    private final TesteAlunoService servico;

    public TesteAlunoController(TesteAlunoService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<TesteAlunoDto>> get(@RequestParam(required = false) String termoBusca) {
        List<TesteAlunoDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TesteAlunoDto> get(@PathVariable Long id) {
        TesteAlunoDto registro = servico.getById(id);
        if (registro == null) {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<TesteAlunoDto> insert(@RequestBody TesteAlunoDto objeto) {
        TesteAlunoDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<TesteAlunoDto> update(@RequestBody TesteAlunoDto objeto) {
        TesteAlunoDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
