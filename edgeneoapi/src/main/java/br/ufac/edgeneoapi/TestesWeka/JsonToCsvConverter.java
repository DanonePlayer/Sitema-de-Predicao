// package br.ufac.edgeneoapi.TestesWeka;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.opencsv.CSVWriter;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Iterator;

// public class JsonToCsvConverter {

//     public static void main(String[] args) {
//         String jsonFile = "C:\\Users\\Eduardo de Assis\\Documents\\GitHub\\hands-on-t5-edge-neo\\edgeneoapi\\src\\main\\java\\br\\ufac\\edgeneoapi\\dados_alunos2023.json";  // Arquivo JSON de entrada
//         String csvFile = "dados_alunos2023.csv";    // Arquivo CSV de saída

//         try (BufferedReader br = new BufferedReader(new FileReader(jsonFile));
//              CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {

//             ObjectMapper objectMapper = new ObjectMapper();
//             String line;

//             // Escreve o cabeçalho no arquivo CSV
//             String[] header = {"Seq", "ID", "COD_CURSO", "NOME_CURSO", "NIVEL_CURSO", "TIPO_CURSO", 
//                                "MODALIDADE_CURSO", "TURNO_CURSO", "ESTADO_CIVIL", "GENERO", "DT_NASCIMENTO",
//                                "ANO_INGRESSO", "FORMA_INGRESSO", "ANO_EVASAO", "FORMA_EVASAO", "NATURALIDADE",
//                                "RACA_CENSO", "ETNIA", "DEFICIENCIAS", "BOLSAS", "BAIRRO", "MUNICIPIO", "ESTADO",
//                                "INFO_COTAS", "COD_DISCIPLINA", "NOME_DISCIPLINA", "ANO_DISCIPLINA",
//                                "PERIODO_DISCIPLINA", "SITUACAO_DISCIPLINA", "CH_TOTAL", "CREDITOS", 
//                                "MEDIA_FINAL", "NUM_FALTAS", "PERIODO_IDEAL"};
//             csvWriter.writeNext(header);

//             // Processa cada linha do arquivo JSON
//             while ((line = br.readLine()) != null) {
//                 try {
//                     // Converte a linha JSON em um objeto JsonNode
//                     JsonNode jsonNode = objectMapper.readTree(line);

//                     // Extrai os valores de cada campo no JSON e converte para string
//                     String[] record = {
//                         jsonNode.path("Seq").asText(null),
//                         jsonNode.path("ID").asText(null),
//                         jsonNode.path("COD_CURSO").asText(null),
//                         jsonNode.path("NOME_CURSO").asText(null),
//                         jsonNode.path("NIVEL_CURSO").asText(null),
//                         jsonNode.path("TIPO_CURSO").asText(null),
//                         jsonNode.path("MODALIDADE_CURSO").asText(null),
//                         jsonNode.path("TURNO_CURSO").asText(null),
//                         jsonNode.path("ESTADO_CIVIL").asText(null),
//                         jsonNode.path("GENERO").asText(null),
//                         jsonNode.path("DT_NASCIMENTO").asText(null),
//                         jsonNode.path("ANO_INGRESSO").asText(null),
//                         jsonNode.path("FORMA_INGRESSO").asText(null),
//                         jsonNode.path("ANO_EVASAO").asText(null),
//                         jsonNode.path("FORMA_EVASAO").asText(null),
//                         jsonNode.path("NATURALIDADE").asText(null),
//                         jsonNode.path("RACA_CENSO").asText(null),
//                         jsonNode.path("ETNIA").asText(null),
//                         jsonNode.path("DEFICIENCIAS").asText(null),
//                         jsonNode.path("BOLSAS").asText(null),
//                         jsonNode.path("BAIRRO").asText(null),
//                         jsonNode.path("MUNICIPIO").asText(null),
//                         jsonNode.path("ESTADO").asText(null),
//                         jsonNode.path("INFO_COTAS").asText(null),
//                         jsonNode.path("COD_DISCIPLINA").asText(null),
//                         jsonNode.path("NOME_DISCIPLINA").asText(null),
//                         jsonNode.path("ANO_DISCIPLINA").asText(null),
//                         jsonNode.path("PERIODO_DISCIPLINA").asText(null),
//                         jsonNode.path("SITUACAO_DISCIPLINA").asText(null),
//                         jsonNode.path("CH_TOTAL").asText(null),
//                         jsonNode.path("CREDITOS").asText(null),
//                         jsonNode.path("MEDIA_FINAL").asText(null),
//                         jsonNode.path("NUM_FALTAS").asText(null),
//                         jsonNode.path("PERIODO_IDEAL").asText(null)
//                     };

//                     // Escreve os registros no arquivo CSV
//                     csvWriter.writeNext(record);

//                 } catch (IOException e) {
//                     System.err.println("Erro ao processar linha JSON: " + e.getMessage());
//                 }
//             }

//             System.out.println("Conversão concluída! Arquivo CSV gerado: " + csvFile);

//         } catch (IOException e) {
//             System.err.println("Erro ao processar os arquivos: " + e.getMessage());
//         }
//     }
// }

