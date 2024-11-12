package br.ufac.edgeneoapi.controller;

import br.ufac.edgeneoapi.dto.TesteCoordenadorDto;
import br.ufac.edgeneoapi.service.TesteCoordenadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testeCoordenador")
public class TesteCoordenadorController implements IController<TesteCoordenadorDto> {

    private final TesteCoordenadorService servico;

    public TesteCoordenadorController(TesteCoordenadorService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<TesteCoordenadorDto>> get(@RequestParam(required = false) String termoBusca) {
        List<TesteCoordenadorDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TesteCoordenadorDto> get(@PathVariable Long id) {
        TesteCoordenadorDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<TesteCoordenadorDto> insert(@RequestBody TesteCoordenadorDto objeto) {
        TesteCoordenadorDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<TesteCoordenadorDto> update(@RequestBody TesteCoordenadorDto objeto) {
        TesteCoordenadorDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
