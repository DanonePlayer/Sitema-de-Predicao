package br.ufac.edgeneoapi.service;

// import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
// import br.ufac.edgeneoapi.model.Coordenador;
// import br.ufac.edgeneoapi.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Arrays;



@Service
public class DocumentoService {

    @Autowired
    // private CoordenadorRepository coordenadorRepository;

    @Value("${documento.upload-dir}")
    private String uploadDir;

    public String salvarDocumentoCoordenador(Long coordenadorId, MultipartFile file) throws IOException {
        validarArquivo(file); {
        try {
            // Criar a subpasta "portaria" dentro do diretório de upload
            Path diretorioPortariaPath = Paths.get(uploadDir, "portaria");
            if (!Files.exists(diretorioPortariaPath)) {
                Files.createDirectories(diretorioPortariaPath);
            }
            
            // Verificar se o coordenador existe no banco de dados
            // Coordenador coordenador = coordenadorRepository.findById(coordenadorId)
            //         .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + coordenadorId));
    
            // Log para verificar nome do arquivo e tamanho
            System.out.println("Recebendo arquivo: " + file.getOriginalFilename() + " | Tamanho: " + file.getSize());

    
            // Definir o nome do arquivo (pode ser ID do coordenador + nome original para evitar duplicidade)
            String nomeArquivo = coordenadorId + "_" + file.getOriginalFilename();
            Path arquivoPath = diretorioPortariaPath.resolve(nomeArquivo);
    
            // Transferir o arquivo para o diretório de upload
            file.transferTo(arquivoPath.toFile());
    
            // Log para verificar caminho do arquivo salvo
            System.out.println("Arquivo salvo em: " + arquivoPath.toString());
    
            // Retornar o caminho relativo do arquivo salvo
            return arquivoPath.toString();
    
        } catch (IOException e) {
            // Log mais detalhado para capturar a exceção
            System.err.println("Erro ao salvar o documento: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao salvar o documento. Verifique o log para mais detalhes.";
        } catch (Exception e) {
            // Log para capturar outras exceções que possam ocorrer
            System.err.println("Erro ao processar o documento: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao processar o documento. Verifique o log para mais detalhes.";
        }
    }
    }

    // Valida o tipo e tamanho do arquivo
    private void validarArquivo(MultipartFile arquivo) {
        List<String> tiposPermitidos = Arrays.asList("application/pdf");
        long tamanhoMaximoPermitido = 5 * 1024 * 1024; // 5 MB

        if (!tiposPermitidos.contains(arquivo.getContentType())) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado. Apenas PDF é permitido.");
        }

        if (arquivo.getSize() > tamanhoMaximoPermitido) {
            throw new IllegalArgumentException("Arquivo excede o tamanho máximo permitido de 5MB.");
        }
    }

    // Busca o documento pelo nome
    public Resource buscarDocumentoCoordenador(String nomeArquivo) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "portaria"); // Converte o diretório de upload em um Path
        Path caminhoArquivo = uploadPath.resolve(nomeArquivo);

        if (!Files.exists(caminhoArquivo)) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        }

        return new UrlResource(caminhoArquivo.toUri());
    }
    
}
