package br.ufac.edgeneoapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import br.ufac.edgeneoapi.model.Curso;
import br.ufac.edgeneoapi.model.DadosBrutos;
import br.ufac.edgeneoapi.model.Disciplina;
import br.ufac.edgeneoapi.repository.CursoRepository;
import br.ufac.edgeneoapi.repository.DadosBrutosRepository;
import br.ufac.edgeneoapi.repository.DisciplinaRepository;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Service
public class MineracaoDadosService {
    @Value("${documento.upload-dir}") // Reutilizando o diretório de upload
    private String uploadDir;

    private final String tokenApiNti = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzZWNyZXQiOiJBcGlVRkFDIiwidG9rZW4iOiJhbGl1bzI4OXBqOTg3MDg5ZGFzOWQ3YXM5OGRuYWRzNzBhZDc4YiIsInVzZXJuYW1lIjoibWFub2VsLmxpbWVpcmEiLCJpZHVzZXIiOjl9.IeGs7uxL420KozjptzWlM_ejRaI9eHvx0UFkEYYaSZE"; // Coloque seu token correto aqui
    private final RestTemplate restTemplate;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final DadosBrutosRepository dadosBrutosRepository;


    public MineracaoDadosService(CursoRepository cursoRepository, DisciplinaRepository disciplinaRepository, RestTemplate restTemplate, DadosBrutosRepository dadosBrutosRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.restTemplate = restTemplate;
        this.dadosBrutosRepository = dadosBrutosRepository;
    }

    @SuppressWarnings("unchecked")
    public String obterNomeCursoPorCodigo(String codCurso, Integer ano) {
        // URL base da API do NTI com pageSize=1
        String baseUrl = "https://sistemas.ufac.br/api/webacademy/dados-alunos/";
        
        // Criar os cabeçalhos da requisição, incluindo o token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenApiNti);
        
        // Construir a URL com os parâmetros
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("__pagesize", 1)
                .queryParam("__page", 1)
                .toUriString();

        // Criar o corpo da requisição JSON conforme o Postman
        String requestBody = "{ \"ANO_INGRESSO\": " + ano + ", \"COD_CURSO\": \"" + codCurso + "\" }";

        // Criar a entidade HTTP com os cabeçalhos e o corpo
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Fazer a requisição GET à API com o token e corpo
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

            // Processar a resposta
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("response")) {
                return "Curso não encontrado";
            }

            // Extrair a lista de cursos da resposta
            List<Map<String, Object>> cursos = (List<Map<String, Object>>) responseBody.get("response");

            if (cursos.isEmpty()) {
                return "Curso não encontrado";
            }

            // Pegar o primeiro curso da resposta
            Map<String, Object> curso = cursos.get(0);
            return (String) curso.get("NOME_CURSO"); // Retorna o nome do curso

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao buscar curso";
        }
    }

    // Método para processar e salvar os dados da API
    @SuppressWarnings("unchecked")
    public void processarDados(Map<String, Object> dados, List<Map<String, Object>> allRecords) {
        // Extrair a lista da chave "response"
        Object response = dados.get("response");
        List<Map<String, Object>> listaDeDados = (List<Map<String, Object>>) response;

        // Verificar se a lista contém dados
        if (listaDeDados == null || listaDeDados.isEmpty()) {
            throw new IllegalArgumentException("A resposta da API não contém dados.");
        }

        // Iterar sobre os dados da lista (se houver mais de um registro)
        for (Map<String, Object> registro : listaDeDados) {
            // Log para verificar o registro atual
            System.out.println("Processando registro: " + registro);

            // Processar curso
            String codCurso = (String) registro.get("COD_CURSO");
            String nomeCurso = (String) registro.get("NOME_CURSO");
            String modalidadeCurso = (String) registro.get("MODALIDADE_CURSO");
            String turnoCurso = (String) registro.get("TURNO_CURSO");
            String codCursoOferta = (String) registro.get("COD_CURSO_OFERTA_DISC");

            // Verificar se o valor de `codCurso` é nulo ou vazio
            if (codCurso == null || codCurso.isEmpty() || codCurso == codCursoOferta) {
                System.out.println("Registro ignorado devido a COD_CURSO nulo ou não correspondente ao curso solicitado.");
                continue; // Pular este registro e continuar com o próximo
            }

            // Verificar se o curso já existe no banco de registro
            Curso curso = cursoRepository.findByCodCurso(codCurso)
                    .orElseGet(() -> {
                        Curso novoCurso = new Curso();
                        novoCurso.setCodCurso(codCurso);
                        novoCurso.setCursoNome(nomeCurso);
                        novoCurso.setCursoModalidade(modalidadeCurso);
                        novoCurso.setCursoTurno(turnoCurso);
                        // Definir outros campos do curso, se houver
                        return cursoRepository.save(novoCurso); // Salva o curso se não existir
                    });


            // Processar disciplina
            String codDisciplina = (String) registro.get("COD_DISCIPLINA");
            String nomeDisciplina = (String) registro.get("NOME_DISCIPLINA");
            Integer periodoDisciplina = (Integer) registro.get("PERIODO_IDEAL");
            Integer creditosDisciplina = (Integer) registro.get("CREDITOS");

            // Verificar se o valor de `codDisciplina` é nulo ou vazio
            if (codDisciplina == null || codDisciplina.isEmpty()) {
                System.out.println("Registro ignorado devido a COD_DISCIPLINA nulo ou vazio.");
                continue; // Pular este registro e continuar com o próximo
            }

            // Verificar se a disciplina já existe no banco de registro
            @SuppressWarnings("unused")
            Disciplina disciplina = disciplinaRepository.findByDisciplinaCodigo(codDisciplina)
                .orElseGet(() -> {
                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setDisciplinaCodigo(codDisciplina);
                    novaDisciplina.setDisciplinaNome(nomeDisciplina);
                    novaDisciplina.setDisciplinaPeriodo(periodoDisciplina);
                    novaDisciplina.setDisciplinaCreditos(creditosDisciplina);
                    novaDisciplina.setCurso(curso);

                    return disciplinaRepository.save(novaDisciplina); // Salva a disciplina se não existir
                });
                
            allRecords.add(registro);


            // Processar e salvar Dados Brutos
            // DadosBrutos dadosBrutos = new DadosBrutos();
            // dadosBrutos.setCodCurso(codCurso);
            // dadosBrutos.setNomeCurso(nomeCurso);
            // dadosBrutos.setAnoInicioPesquisa(anoIngresso);
            // dadosBrutos.setStatus("Em andamento");
            // dadosBrutos.setLocalArquivo();

            // dadosBrutosRepository.save(dadosBrutos);


        }
    }


    // Modifique aqui para usar o diretório correto ao salvar os dados em CSV e JSON
    public String saveDataToCsvFile(List<Map<String, Object>> allRecords, String csvFileName) {
        try {
            // Definir o caminho da subpasta "dadosBrutos"
            Path subPastaPath = Paths.get(uploadDir, "dadosBrutos");
            if (!Files.exists(subPastaPath)) {
                Files.createDirectories(subPastaPath);
            }
    
            // Definir o caminho completo para o arquivo CSV
            Path arquivoCsvPath = subPastaPath.resolve(csvFileName);
    
            // Escrever os dados no arquivo CSV
            try (FileWriter fileWriter = new FileWriter(arquivoCsvPath.toFile())) {
                if (!allRecords.isEmpty()) {
                    // Obtenha as chaves do primeiro registro para escrever o cabeçalho do CSV
                    Map<String, Object> firstRecord = allRecords.get(0);
                    String header = String.join(",", firstRecord.keySet());
                    fileWriter.write(header + System.lineSeparator());
    
                    // Escrever os registros
                    for (Map<String, Object> record : allRecords) {
                        // Para cada registro, extraímos os valores e os formatamos corretamente
                        String csvLine = record.values().stream()
                                .map(value -> value == null ? "" : value.toString())  // Evitar valores null
                                .collect(Collectors.joining(","));  // Unir com vírgulas
                        fileWriter.write(csvLine + System.lineSeparator());
                    }
                } else {
                    System.out.println("Nenhum dado para salvar.");
                }
    
                System.out.println("Dados salvos com sucesso em " + arquivoCsvPath.toString());
                return arquivoCsvPath.toString();  // Retornar o caminho do arquivo salvo
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em CSV: " + e.getMessage());
            return null;
        }
    }
    

    // public void saveDataToJsonFile(List<Map<String, Object>> allRecords, String jsonFileName) {
    //     ObjectMapper objectMapper = new ObjectMapper();

    //     try {
    //         // Definir o caminho da subpasta "dadosBrutos"
    //         Path subPastaPath = Paths.get(uploadDir, "dadosBrutos");
    //         if (!Files.exists(subPastaPath)) {
    //             Files.createDirectories(subPastaPath);
    //         }

    //         // Definir o caminho completo para o arquivo JSON
    //         Path arquivoJsonPath = subPastaPath.resolve(jsonFileName);

    //         // Escrever os dados no arquivo JSON
    //         try (FileWriter fileWriter = new FileWriter(arquivoJsonPath.toFile())) {
    //             for (Map<String, Object> record : allRecords) {
    //                 String jsonRecord = objectMapper.writeValueAsString(record);
    //                 fileWriter.write(jsonRecord + System.lineSeparator());
    //             }

    //             System.out.println("Dados salvos com sucesso em " + arquivoJsonPath.toString());
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Erro ao salvar dados em JSON: " + e.getMessage());
    //     }
    // }

    public void processarDadosDeAlunos(Integer anoIngresso, String codCurso) {
        // Cabeçalhos da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenApiNti);  // Definindo o token do NTI no cabeçalho
    
        // URL base da API do NTI
        String baseUrl = "https://sistemas.ufac.br/api/webacademy/dados-alunos/";
    
        // Página inicial e tamanho da página
        int pageSize = 500;
        int[] anos = {anoIngresso};
    
        // Processar todas as páginas e salvar dados
        List<Map<String, Object>> allRecords = new ArrayList<>();
        processarTodasAsPaginas(headers, baseUrl, pageSize, anos, codCurso, allRecords);
    
        // Nome do arquivo CSV
        String csvFileName = codCurso + "_dadosBrutos.csv";
    
        // Salvar os dados no formato CSV e obter o caminho completo do arquivo
        String caminhoDoArquivo = saveDataToCsvFile(allRecords, csvFileName);
    
        // Se o arquivo foi salvo corretamente, salvar os dados na tabela DadosBrutos
        if (caminhoDoArquivo != null) {
            DadosBrutos dadosBrutos = new DadosBrutos();
            dadosBrutos.setCodCurso(codCurso);
            dadosBrutos.setNomeCurso(obterNomeCursoPorCodigo(codCurso, anoIngresso));
            dadosBrutos.setAnoInicioPesquisa(anoIngresso);
            dadosBrutos.setStatus("Pronto");
            dadosBrutos.setLocalArquivo(caminhoDoArquivo);
    
            // Salvar no banco de dados
            dadosBrutosRepository.save(dadosBrutos);
        } else {
            System.err.println("Erro ao salvar o arquivo CSV, não será salvo no banco de dados.");
        }
    }
    


    // Método para fazer a requisição e processar todas as páginas da API usando GET
    private void processarTodasAsPaginas(HttpHeaders headers, String baseUrl, int pageSize, int[] anos, String codCurso, List<Map<String, Object>> allRecords) {
        for (int ano : anos) {
            int currentPage = 1;
            boolean hasMoreData = true;
    
            while (hasMoreData) {
                // Construir a URL com parâmetros de paginação
                String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("__pagesize", pageSize)
                        .queryParam("__page", currentPage)
                        .toUriString();
    
                System.out.println("URL gerada: " + url); // Verificar a URL
    
                // Criar o corpo da requisição JSON conforme o Postman
                String requestBody = "{ \"ANO_INGRESSO\": " + ano + ", \"COD_CURSO\": \"" + codCurso + "\" }";
    
                // Logar o corpo da requisição para depuração
                System.out.println("Body Gerado: " + requestBody);
    
                // Criar a entidade HTTP com os cabeçalhos e o corpo
                HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
                try {
                    // Fazer a requisição POST à API com o token e corpo
                    ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
    
                    Map<String, Object> responseBody = response.getBody();
    
                    if (responseBody == null) {
                        break;
                    }
    
                    processarDados(responseBody, allRecords);
    
                    // Verifica se ainda há mais páginas
                    if (responseBody.containsKey("response") && ((List<?>) responseBody.get("response")).size() < pageSize) {
                        hasMoreData = false;
                    } else {
                        currentPage++;
                    }
                } catch (HttpClientErrorException e) {
                    System.err.println("Erro HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    hasMoreData = false;
                }
            }
        }
    }
    
    
}