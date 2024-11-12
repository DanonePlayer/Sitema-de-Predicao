package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.dto.LogsDto;
import br.ufac.edgeneoapi.service.LogsService;

@RestController
@RequestMapping("/logs")
public class LogsController implements IController<LogsDto> {

    private final LogsService servico;

    public LogsController(LogsService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<LogsDto>> get(@RequestParam(required = false) String termoBusca) {
        List<LogsDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LogsDto> get(@PathVariable Long id) {
        LogsDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<LogsDto> insert(@RequestBody LogsDto objeto) {
        LogsDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<LogsDto> update(@RequestBody LogsDto objeto) {
        LogsDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
