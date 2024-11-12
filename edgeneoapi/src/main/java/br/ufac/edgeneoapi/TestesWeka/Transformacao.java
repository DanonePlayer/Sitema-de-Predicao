// package br.ufac.edgeneoapi.TestesWeka;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.node.ObjectNode;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileWriter;
// import java.io.InputStreamReader;
// import java.nio.charset.StandardCharsets;
// import java.time.LocalDate;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class Transformacao {

//     public static void main(String[] args) throws Exception {
//         // Criando um ObjectMapper para manipular JSON
//         ObjectMapper mapper = new ObjectMapper();

//         // Lendo o JSON a partir de um arquivo com codificação UTF-8
//         BufferedReader reader = new BufferedReader(
//             new InputStreamReader(new FileInputStream("C:\\Users\\Eduar\\OneDrive\\Eduardo\\Documents\\GitHub\\hands-on-t5-edge-neo\\dados_alunos2008_2010.json"), StandardCharsets.UTF_8)
//         );

//         // Criar um FileWriter para gravar o JSON modificado em um novo arquivo
//         FileWriter writer = new FileWriter(new File("data-modificado.json"), StandardCharsets.UTF_8);

//         // Mapa para armazenar os dados dos alunos por ID
//         Map<String, Aluno> alunos = new HashMap<>();

//         // Lendo o arquivo JSON linha por linha
//         String line;
//         while ((line = reader.readLine()) != null) {
//             // Ignorando linhas vazias ou com erro de formatação
//             if (line.trim().isEmpty()) {
//                 continue;
//             }

//             // Lendo cada linha como um objeto JSON
//             JsonNode rootNode = mapper.readTree(line);
//             String alunoId = rootNode.path("ID").asText();

//             // Se o aluno já existir no mapa, adicionamos mais disciplinas
//             Aluno aluno = alunos.getOrDefault(alunoId, new Aluno(alunoId));
//             aluno.adicionarDisciplina(rootNode);

//             // Colocando o aluno atualizado no mapa
//             alunos.put(alunoId, aluno);

//             // Calculando a idade no início do curso
//             int idadeInicioCurso = calcularIdadeInicioCurso(rootNode);
//             ((ObjectNode) rootNode).put("idade_inicio_curso", idadeInicioCurso);

//             // Calculando a duração do curso
//             int duracaoCurso = calcularDuracaoCurso(rootNode);
//             ((ObjectNode) rootNode).put("duracao_curso", duracaoCurso);

//             // Calculando a porcentagem de conclusão do período
//             double periodoPorcentagem = calcularPeriodoPorcentagem(rootNode);
//             ((ObjectNode) rootNode).put("periodo_porcentagem", periodoPorcentagem);
//         }

//         // Processar os alunos e calcular as métricas desejadas
//         for (Aluno aluno : alunos.values()) {
//             for (JsonNode disciplina : aluno.disciplinas) {
//                 ObjectNode disciplinaNode = (ObjectNode) disciplina;
        
//                 // Calculando o percentual de frequência
//                 double percentualFrequencia = calcularPercentualFrequencia(disciplina);
//                 disciplinaNode.put("percentual_frequencia_periodo", arredondarDuasCasas(percentualFrequencia));

//                 // Recuperando o período da disciplina atual
//                 String periodoDisciplina = disciplina.path("PERIODO_DISCIPLINA").asText();
//                 int anoDisciplina = disciplina.path("ANO_DISCIPLINA").asInt();
        
//                 // Histórico recente
//                 if (aluno.isPeriodoAnterior(disciplina)) {
//                     System.out.println("Processando histórico recente para aluno: " + aluno.id);
//                     disciplinaNode.put("historico_recente_cr", arredondarDuasCasas(aluno.calcularCR(periodoDisciplina, anoDisciplina)));
//                     disciplinaNode.put("historico_recente_percentual_frequencia", arredondarDuasCasas(aluno.calcularPercentualFrequenciaPeriodoAnterior(periodoDisciplina, anoDisciplina)));
//                     disciplinaNode.put("historico_recente_media", arredondarDuasCasas(aluno.calcularMediaPeriodoAnterior(periodoDisciplina, anoDisciplina)));
//                     disciplinaNode.put("historico_recente_aprovadas_quantidade", aluno.calcularAprovadasQuantidadePeriodoAnterior(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_recente_aprovadas_media", arredondarDuasCasas(aluno.calcularAprovadasMediaPeriodoAnterior(periodoDisciplina, anoDisciplina)));
//                     disciplinaNode.put("historico_recente_reprovadas_quantidade", aluno.calcularReprovadasQuantidadePeriodoAnterior(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_recente_reprovadas_media", arredondarDuasCasas(aluno.calcularReprovadasMediaPeriodoAnterior(periodoDisciplina, anoDisciplina)));
//                     disciplinaNode.put("historico_recente_cursadas", aluno.calcularCursadasPeriodoAnterior(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_recente_classe_rendimento", aluno.classificarRendimentoPeriodoAnterior(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_recente_classe_desempenho", aluno.classificarDesempenhoPeriodoAnterior(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_recente_classe_disciplinas_cursadas", aluno.classificarQuantidadeDisciplinasCursadas(periodoDisciplina, anoDisciplina));
//                     disciplinaNode.put("historico_geral_cr", arredondarDuasCasas(aluno.calcularCRGeral()));
//                     disciplinaNode.put("historico_geral_percentual_frequencia", arredondarDuasCasas(aluno.calcularPercentualFrequenciaGeral()));
//                     disciplinaNode.put("historico_geral_media", arredondarDuasCasas(aluno.calcularMediaGeral()));
//                     disciplinaNode.put("historico_geral_aprovadas_quantidade", aluno.calcularAprovadasQuantidadeGeral());
//                     disciplinaNode.put("historico_geral_aprovadas_media", arredondarDuasCasas(aluno.calcularAprovadasMediaGeral()));
//                     disciplinaNode.put("historico_geral_reprovadas_quantidade", aluno.calcularReprovadasQuantidadeGeral());
//                     disciplinaNode.put("historico_geral_reprovadas_media", arredondarDuasCasas(aluno.calcularReprovadasMediaGeral()));
//                     disciplinaNode.put("historico_geral_cursadas", aluno.calcularCursadasGeral());
        
//                 }

//                 // Escrevendo o objeto JSON modificado no novo arquivo
//                 writer.write(disciplinaNode.toString() + "\n");
//             }
//         }
        

//         // Fechando os leitores e escritores
//         reader.close();
//         writer.close();

//         System.out.println("JSON modificado com sucesso!");
//     }

//     // Função para calcular a idade no início do curso
//     public static int calcularIdadeInicioCurso(JsonNode rootNode) {
//         String dtNascimento = rootNode.path("DT_NASCIMENTO").asText();
//         int anoNascimento = Integer.parseInt(dtNascimento.split("-")[0]);
//         int anoIngresso = rootNode.path("ANO_INGRESSO").asInt();

//         // Calculando a idade no início do curso
//         return anoIngresso - anoNascimento;
//     }

//     // Função para calcular a duração do curso
//     public static int calcularDuracaoCurso(JsonNode rootNode) {
//         int anoIngresso = rootNode.path("ANO_INGRESSO").asInt();
//         JsonNode anoEvasaoNode = rootNode.path("ANO_EVASAO");

//         int anoEvasao;

//         // Se o ano de evasão for null, assumimos o ano atual
//         if (anoEvasaoNode.isNull()) {
//             anoEvasao = LocalDate.now().getYear();
//         } else {
//             anoEvasao = anoEvasaoNode.asInt();
//         }

//         // Calculando a duração do curso
//         return anoEvasao - anoIngresso;
//     }

//     // Função para calcular a porcentagem de conclusão do período
//     public static double calcularPeriodoPorcentagem(JsonNode rootNode) {
//         int periodoIdeal = rootNode.path("PERIODO_IDEAL").asInt();
//         int creditosTotais = rootNode.path("CREDITOS").asInt();

//         // Exemplo simples: considerar que o número de créditos reflete a conclusão de disciplinas
//         // Aqui, a fórmula pode ser ajustada conforme o critério desejado
//         double porcentagemConclusao = (double) periodosConcluidos(rootNode) / periodoIdeal * 100;

//         return Math.min(porcentagemConclusao, 100); // Limita a porcentagem a 100%
//     }

//     // Função auxiliar para contar quantas disciplinas o estudante já aprovou
//     public static int periodosConcluidos(JsonNode rootNode) {
//         String situacaoDisciplina = rootNode.path("SITUACAO_DISCIPLINA").asText();
//         if ("Aprovado".equals(situacaoDisciplina)) {
//             return rootNode.path("PERIODO_IDEAL").asInt();
//         }
//         return 0; // Caso não esteja aprovado, considerar zero períodos
//     }

//     // Função estática para calcular o percentual de frequência do período
//     public static double calcularPercentualFrequencia(JsonNode disciplina) {
//         int numFaltas = disciplina.path("NUM_FALTAS").asInt();
//         double cargaHoraria = disciplina.path("CH_TOTAL").asDouble();

//         return (1 - (double) numFaltas / cargaHoraria) * 100;
//     }

//     // Função para arredondar valores para duas casas decimais
//     public static double arredondarDuasCasas(double valor) {
//         return Math.round(valor * 100.0) / 100.0;
//     }
// }
