// package br.ufac.edgeneoapi.TestesWeka;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.client.ClientHttpResponse;
// import org.springframework.web.client.DefaultResponseErrorHandler;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.util.UriComponentsBuilder;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// @SpringBootApplication
// public class ObtencaoDados implements CommandLineRunner {

//     private static final String BASE_API_URL = "https://sistemas.ufac.br/api/webacademy/dados-alunos/";
//     private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzZWNyZXQiOiJBcGlVRkFDIiwidG9rZW4iOiJhbGl1bzI4OXBqOTg3MDg5ZGFzOWQ3YXM5OGRuYWRzNzBhZDc4YiIsInVzZXJuYW1lIjoibWFub2VsLmxpbWVpcmEiLCJpZHVzZXIiOjl9.IeGs7uxL420KozjptzWlM_ejRaI9eHvx0UFkEYYaSZE";

//     public static void main(String[] args) {
//         SpringApplication.run(ObtencaoDados.class, args);
//         System.out.println("hello world");
//     }

//     @Override
//     public void run(String... args) {
//         RestTemplate restTemplate = new RestTemplate();

//         // Desativar o tratamento automático de erros para capturar respostas de erro
//         restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
//             @Override
//             public boolean hasError(ClientHttpResponse response) throws IOException {
//                 return false; // Desativa o tratamento automático de erros
//             }
//         });

//         List<Map<String, Object>> allRecords = new ArrayList<>();
//         int pageSize = 500; // O valor padrão de página definido na collection

//         // Iterar pelos anos de interesse
//         int[] anos = {2008, 2009, 2010};

//         for (int ano : anos) {
//             int currentPage = 1;
//             boolean hasMoreData = true;

//             while (hasMoreData) {
//                 // Montar o corpo da requisição para o ano atual
//                 String body = String.format("{\"ANO_INGRESSO\": %d}", ano);

//                 // Montar a URL
//                 String url = UriComponentsBuilder.fromHttpUrl(BASE_API_URL)
//                         .queryParam("__pagesize", pageSize)
//                         .queryParam("__page", currentPage)
//                         .toUriString();

//                 System.out.println("URL da requisição: " + url);  // Log da URL

//                 // Criar o cabeçalho com o Bearer Token
//                 HttpHeaders headers = new HttpHeaders();
//                 headers.setContentType(MediaType.APPLICATION_JSON); // Define o tipo do conteúdo como JSON
//                 headers.setBearerAuth(BEARER_TOKEN);

//                 // Criar a entidade HttpEntity com o corpo e os cabeçalhos
//                 HttpEntity<String> entity = new HttpEntity<>(body, headers);

//                 try {
//                     // Fazer a requisição POST
//                     ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

//                     // Processar a resposta
//                     Map<String, Object> responseBody = response.getBody();
//                     System.out.println("Resposta: " + responseBody);

//                     // Verificar se a resposta contém a chave "response" e se ela é uma lista
//                     Object responseObject = responseBody.get("response");
//                     if (responseObject instanceof List) {
//                         List<Map<String, Object>> records = (List<Map<String, Object>>) responseObject;

//                         if (records == null || records.isEmpty()) {
//                             hasMoreData = false; // Encerra o loop se não houver mais dados
//                         } else {
//                             // Aplicar o filtro para "NOME_CURSO: Bacharelado em Sistemas de Informação"
//                             List<Map<String, Object>> filteredRecords = records.stream()
//                                     .filter(record -> "Bacharelado em Sistemas de Informação"
//                                             .equals(record.get("NOME_CURSO")))
//                                     .collect(Collectors.toList());

//                             // Adicionar registros filtrados à lista final
//                             allRecords.addAll(filteredRecords);
//                             currentPage++; // Próxima página

//                             // Verifica se ainda há mais páginas com base no número de registros retornados
//                             if (records.size() < pageSize) {
//                                 hasMoreData = false;
//                             }
//                         }
//                     } else if (responseObject instanceof Map) {
//                         Map<String, Object> record = (Map<String, Object>) responseObject;
//                         // Verificar se o registro corresponde ao filtro "NOME_CURSO"
//                         if ("Bacharelado em Sistemas de Informação".equals(record.get("NOME_CURSO"))) {
//                             allRecords.add(record);
//                         }
//                         hasMoreData = false; // Finaliza se houver apenas um objeto
//                     } else {
//                         hasMoreData = false; // Finaliza se não for uma lista nem um mapa
//                     }

//                 } catch (HttpClientErrorException e) {
//                     // Captura erros HTTP, como 404 ou 500, e imprime a resposta de erro
//                     System.err.println("Erro HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
//                     hasMoreData = false; // Se ocorrer um erro, interrompe o loop
//                 }
//             }
//         }

//         // Salvar todos os dados coletados em um arquivo
//         saveDataToFile(allRecords);
//     }

//     // Método para salvar os dados em um arquivo JSON, com um registro por linha
//     private void saveDataToFile(List<Map<String, Object>> allRecords) {
//         ObjectMapper objectMapper = new ObjectMapper(); // Biblioteca para manipulação de JSON

//         try (FileWriter fileWriter = new FileWriter("dados_alunosSI4.json")) { // Tenta abrir o arquivo para escrita
//             for (Map<String, Object> record : allRecords) {
//                 // Converte cada registro para JSON e escreve no arquivo seguido de uma nova linha
//                 String jsonRecord = objectMapper.writeValueAsString(record);
//                 fileWriter.write(jsonRecord + System.lineSeparator());
//             }
//             System.out.println("Dados salvos com sucesso em dados_alunos.json, separados por linha.");
//         } catch (IOException e) {
//             System.err.println("Erro ao salvar dados: " + e.getMessage());
//         }
//     }

// }
