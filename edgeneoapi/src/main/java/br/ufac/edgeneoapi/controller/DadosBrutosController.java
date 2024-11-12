package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.DadosBrutosDto;
import br.ufac.edgeneoapi.service.DadosBrutosService;

@RestController
@RequestMapping("/dados-brutos")
public class DadosBrutosController implements IController<DadosBrutosDto> {

    private final DadosBrutosService servico;

    public DadosBrutosController(DadosBrutosService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<DadosBrutosDto>> get(@RequestParam(required = false) String termoBusca) {
        List<DadosBrutosDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DadosBrutosDto> get(@PathVariable Long id) {
        DadosBrutosDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<DadosBrutosDto> insert(@RequestBody DadosBrutosDto objeto) {
        DadosBrutosDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<DadosBrutosDto> update(@RequestBody DadosBrutosDto objeto) {
        DadosBrutosDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
