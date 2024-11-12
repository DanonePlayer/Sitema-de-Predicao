package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufac.edgeneoapi.dto.AdministradorDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.service.AdministradorService;

@RestController
@RequestMapping("/administrador")
public class AdministradorController implements IController<AdministradorDto> {

    private final AdministradorService servico;

    public AdministradorController(AdministradorService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<AdministradorDto>> get(@RequestParam(required = false) String termoBusca) {
        List<AdministradorDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorDto> get(@PathVariable Long id) {
        AdministradorDto registro = servico.getById(id);
        if (registro == null) {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<AdministradorDto> insert(@RequestBody AdministradorDto objeto) {
        AdministradorDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<AdministradorDto> update(@RequestBody AdministradorDto objeto) {
        AdministradorDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
