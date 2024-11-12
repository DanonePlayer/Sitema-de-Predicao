package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.DadosProcessadosDto;
import br.ufac.edgeneoapi.service.DadosProcessadosService;

@RestController
@RequestMapping("/dados-processados")
public class DadosProcessadosController implements IController<DadosProcessadosDto> {

    private final DadosProcessadosService servico;

    public DadosProcessadosController(DadosProcessadosService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<DadosProcessadosDto>> get(@RequestParam(required = false) String termoBusca) {
        List<DadosProcessadosDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DadosProcessadosDto> get(@PathVariable Long id) {
        DadosProcessadosDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<DadosProcessadosDto> insert(@RequestBody DadosProcessadosDto objeto) {
        DadosProcessadosDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<DadosProcessadosDto> update(@RequestBody DadosProcessadosDto objeto) {
        DadosProcessadosDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
