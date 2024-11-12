package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.DisciplinaDto;
import br.ufac.edgeneoapi.service.DisciplinaService;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController implements IController<DisciplinaDto> {

    private final DisciplinaService servico;

    public DisciplinaController(DisciplinaService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<DisciplinaDto>> get(@RequestParam(required = false) String termoBusca) {
        List<DisciplinaDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDto> get(@PathVariable Long id) {
        DisciplinaDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<DisciplinaDto> insert(@RequestBody DisciplinaDto objeto) {
        DisciplinaDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<DisciplinaDto> update(@RequestBody DisciplinaDto objeto) {
        DisciplinaDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
