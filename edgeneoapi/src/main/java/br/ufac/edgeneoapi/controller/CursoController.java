package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.CursoDto;
import br.ufac.edgeneoapi.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController implements IController<CursoDto> {

    private final CursoService servico;

    public CursoController(CursoService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<CursoDto>> get(@RequestParam(required = false) String termoBusca) {
        List<CursoDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> get(@PathVariable Long id) {
        CursoDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<CursoDto> insert(@RequestBody CursoDto objeto) {
        CursoDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<CursoDto> update(@RequestBody CursoDto objeto) {
        CursoDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
