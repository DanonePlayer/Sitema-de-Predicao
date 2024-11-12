package br.ufac.edgeneoapi.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import br.ufac.edgeneoapi.dto.CoordenadorDto;
import br.ufac.edgeneoapi.dto.UsuarioDto;
import br.ufac.edgeneoapi.service.CoordenadorService;
import br.ufac.edgeneoapi.service.DocumentoService;
import br.ufac.edgeneoapi.service.UsuarioService;

@RestController
@RequestMapping("/coordenador")
public class CoordenadorController implements IController<CoordenadorDto> {

    private final CoordenadorService servico;
    private final DocumentoService documentoService;
    private final UsuarioService usuarioService;

    public CoordenadorController(CoordenadorService servico, DocumentoService documentoService, UsuarioService usuarioService) {
        this.servico = servico;
        this.documentoService = documentoService;
        this.usuarioService = usuarioService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<CoordenadorDto>> get(@RequestParam(required = false) String termoBusca) {
        List<CoordenadorDto> registros = servico.getAll();
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDto> get(@PathVariable Long id) {
        CoordenadorDto registro = servico.getById(id);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping()
    public ResponseEntity<CoordenadorDto> insert(@RequestBody CoordenadorDto objeto) {
        CoordenadorDto registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<CoordenadorDto> update(@RequestBody CoordenadorDto objeto) {
        CoordenadorDto registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDto> update(@PathVariable Long id, @RequestBody CoordenadorDto objeto) {

        // Atualiza o coordenador
        CoordenadorDto registroAtualizado = servico.update(id, objeto);  // O método update no serviço deve aceitar o ID e o objeto CoordenadorDto
        return ResponseEntity.ok(registroAtualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovarCoordenador(@PathVariable Long id) {
        servico.aprovarCoordenador(id);
        return ResponseEntity.ok("Coordenador aprovado com sucesso");
    }

    @PostMapping("/{id}/upload-portaria")
    public ResponseEntity<?> uploadPortaria(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Salvar o documento no diretório e obter o caminho do arquivo
            String caminhoDocumento = documentoService.salvarDocumentoCoordenador(id, file);
            
            // Associar o caminho do documento ao coordenador
            servico.associarDocumentoPortaria(id, caminhoDocumento);

            // Retornar um JSON válido ao invés de uma string simples
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Documento salvo com sucesso e associado ao Coordenador.");
            response.put("caminhoDocumento", caminhoDocumento);

            return ResponseEntity.ok(response);  // Retorna a resposta como JSON
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/downloadPortaria")
    public ResponseEntity<Resource> downloadPortaria(@PathVariable Long id) {
        try {
            // Obtém o coordenador e o caminho da portaria
            CoordenadorDto coordenadorDto = servico.getById(id);
            String caminhoPortaria = coordenadorDto.portaria();

            if (caminhoPortaria == null || caminhoPortaria.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource arquivo = documentoService.buscarDocumentoCoordenador(caminhoPortaria);

            Long usuarioCoordenador = coordenadorDto.usuario_id();  // Assumindo que `getUsuario()` retorna o objeto Usuario com o nome

            UsuarioDto usuarioDto = usuarioService.getById(usuarioCoordenador);

            String nomeCoordenador = usuarioDto.nome();

            String nomeArquivo = "portaria_" + nomeCoordenador.replaceAll("\\s+", "_") + ".pdf";  // Substitui espaços por underlines


            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                    .body(arquivo);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
