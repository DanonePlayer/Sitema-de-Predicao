package br.ufac.edgeneoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufac.edgeneoapi.service.MineracaoDadosService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/nti-api")
public class NtiApiController {

    private final MineracaoDadosService mineracaoDadosService;



    public NtiApiController(MineracaoDadosService mineracaoDadosService) {
        this.mineracaoDadosService = mineracaoDadosService;
    }

    // Endpoint para obter dados de alunos por ano e curso via GET
    @PostMapping("/dados-alunos")
    public ResponseEntity<Map<String, Object>> obterDadosAlunos(@RequestBody Map<String, Object> requestBody) {
        // Extrair valores do corpo da requisição
        int anoIngresso = (int) requestBody.get("ANO_INGRESSO");
        String codCurso = (String) requestBody.get("COD_CURSO");

        // Processar todas as páginas e salvar dados
        mineracaoDadosService.processarDadosDeAlunos(anoIngresso, codCurso);

        // Cria o objeto JSON para a resposta
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Dados processados com sucesso.");
        response.put("status", "sucesso");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar-nome-curso")
    public ResponseEntity<String> buscarNomeCurso(@RequestParam String codCurso, @RequestParam Integer anoIngresso) {
        // Chama o serviço para obter o nome do curso
        String nomeCurso = mineracaoDadosService.obterNomeCursoPorCodigo(codCurso, anoIngresso);
        return ResponseEntity.ok(nomeCurso);
    }
}
