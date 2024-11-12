package br.ufac.edgeneoapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import br.ufac.edgeneoapi.dto.TreinamentoDto;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.service.TreinamentoService;

@RestController
@RequestMapping("/treinamento")
public class TreinamentoController implements IController<TreinamentoDto> {

    private final TreinamentoService servico;

    public TreinamentoController(TreinamentoService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<TreinamentoDto>> get(@RequestParam(required = false) String termoBusca) {
        List<TreinamentoDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TreinamentoDto> get(@PathVariable Long id) {
        TreinamentoDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<TreinamentoDto> insert(@RequestBody TreinamentoDto objeto) {
        TreinamentoDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<TreinamentoDto> update(@RequestBody TreinamentoDto objeto) {
        TreinamentoDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/weka/dados")
    public ResponseEntity<?> carregarDados() {
        try {
            return ResponseEntity.ok(servico.carregarDados());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao carregar o arquivo CSV: " + e.getMessage());
        }
    }

    @GetMapping("/weka/train")
    public ResponseEntity<?> treinarModelosParaTodosPeriodos() {
        try {
            return ResponseEntity.ok(servico.treinarModelosPorPeriodo());
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Erro ao treinar os modelos para todos os períodos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/weka/predict")
    public ResponseEntity<Map<String, String>> realizarPredicao(@RequestBody Aluno aluno) {
        try {
            Map<String, String> resultados = servico.preverSituacaoParaDisciplinasDoProximoPeriodo();
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao realizar predição: " + e.getMessage()));
        }
    }
    

    
    
    
}
