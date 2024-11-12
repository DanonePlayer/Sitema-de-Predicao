// package br.ufac.edgeneoapi.TestesWeka;
// import com.fasterxml.jackson.databind.JsonNode;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// public class Aluno {
//     // Declaração de Variáveis
//     String id;
//     List<JsonNode> disciplinas;

//     // Construtor
//     public Aluno(String id) {
//         this.id = id;
//         this.disciplinas = new ArrayList<>();
//     }

//     // Adiciona uma disciplina ao histórico do aluno
//     public void adicionarDisciplina(JsonNode disciplina) {
//         disciplinas.add(disciplina);
//     }

//     //Métodos de cálculo de período recente

//     // Calcula o CR do aluno (média ponderada das notas pelas disciplinas do período anterior)
//     public double calcularCR(String periodo, int ano) {
//         double somaPonderada = 0;
//         int totalCreditos = 0;
    
//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);
    
//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             double mediaFinal = disciplina.path("MEDIA_FINAL").asDouble();
//             int creditos = disciplina.path("CREDITOS").asInt();
    
//             somaPonderada += mediaFinal * creditos;
//             totalCreditos += creditos;
//         }
    
//         return totalCreditos > 0 ? somaPonderada / totalCreditos : 0;
//     }

//     // Calcula o percentual de frequência no período anterior
//     public double calcularPercentualFrequenciaPeriodoAnterior(String periodo, int ano) {
//         double somaFrequencia = 0;  
//         int totalDisciplinas = 0;
    
//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);
    
//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             somaFrequencia += Transformacao.calcularPercentualFrequencia(disciplina);
//             totalDisciplinas++;
//         }
    
//         return totalDisciplinas > 0 ? somaFrequencia / totalDisciplinas : 0;
//     }

//     // Calcula a média das disciplinas no período anterior
//     public double calcularMediaPeriodoAnterior(String periodo, int ano) {
//         double somaMedias = 0;
//         int totalDisciplinas = 0;

//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);

//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             if (isPeriodoAnterior(disciplina)) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalDisciplinas++;
//             }
//         }

//         return totalDisciplinas > 0 ? somaMedias / totalDisciplinas : 0;
//     }

//     // Conta a quantidade de disciplinas aprovadas no período anterior
//     public int calcularAprovadasQuantidadePeriodoAnterior(String periodo, int ano) {
//     int aprovadas = 0;

//     List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);

//     for (JsonNode disciplina : disciplinasPorPeriodo) {
//         if ("Aprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//             aprovadas++;
//         }
//     }

//     return aprovadas;
//     }


//     // Calcula a média das disciplinas aprovadas no período anterior
//     public double calcularAprovadasMediaPeriodoAnterior(String periodo, int ano) {
//         double somaMedias = 0;
//         int totalAprovadas = 0;
    
//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);
    
//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             if (isPeriodoAnterior(disciplina) && "Aprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalAprovadas++;
//             }
//         }

//         return totalAprovadas > 0 ? somaMedias / totalAprovadas : 0;
//     }

//     // Conta a quantidade de disciplinas reprovadas no período anterior
//     public int calcularReprovadasQuantidadePeriodoAnterior(String periodo, int ano) {
//         int reprovadas = 0;

//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);


//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             if (isPeriodoAnterior(disciplina) && "Reprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 reprovadas++;
//             }
//         }

//         return reprovadas;
//     }

//     // Calcula a média das disciplinas reprovadas no período anterior
//     public double calcularReprovadasMediaPeriodoAnterior(String periodo, int ano) {
//         double somaMedias = 0;
//         int totalReprovadas = 0;

//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);


//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             if (isPeriodoAnterior(disciplina) && "Reprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalReprovadas++;
//             }
//         }

//         return totalReprovadas > 0 ? somaMedias / totalReprovadas : 0;
//     }

//     // Calcula o total de disciplinas cursadas no período anterior
//     public int calcularCursadasPeriodoAnterior(String periodo, int ano) {
//         int cursadas = 0;

//         List<JsonNode> disciplinasPorPeriodo = getDisciplinasPorPeriodo(periodo, ano);

//         for (JsonNode disciplina : disciplinasPorPeriodo) {
//             if (isPeriodoAnterior(disciplina)) {
//                 cursadas++;
//             }
//         }

//         return cursadas;
//     }

//     // Categorização do rendimento: aprovado/total de disciplinas cursadas
//     public String classificarRendimentoPeriodoAnterior(String periodo, int ano) {

//         int totalCursadas = calcularCursadasPeriodoAnterior(periodo, ano);
//         int totalAprovadas = calcularAprovadasQuantidadePeriodoAnterior(periodo, ano);

//         if (totalCursadas == 0) {
//             return "Sem classificação";
//         }

//         double taxaAprovacao = (double) totalAprovadas / totalCursadas;

//         if (taxaAprovacao >= 0.9) {
//             return "Excelente";
//         } else if (taxaAprovacao >= 0.7) {
//             return "Bom";
//         } else if (taxaAprovacao >= 0.5) {
//             return "Regular";
//         } else {
//             return "Insuficiente";
//         }
//     }

//     // Categorização do desempenho com base na média das disciplinas cursadas
//     public String classificarDesempenhoPeriodoAnterior(String periodo, int ano) {
//         double media = calcularMediaPeriodoAnterior(periodo, ano);

//         if (media >= 9.0) {
//             return "Excelente";
//         } else if (media >= 7.0) {
//             return "Bom";
//         } else if (media >= 5.0) {
//             return "Regular";
//         } else {
//             return "Insuficiente";
//         }
//     }

//     // Categorização da quantidade média de disciplinas cursadas por período
//     public String classificarQuantidadeDisciplinasCursadas(String periodo, int ano) {
//         int totalCursadas = calcularCursadasPeriodoAnterior(periodo, ano);
//         if (totalCursadas >= 6) {
//             return "Carga Alta";
//         } else if (totalCursadas >= 4) {
//             return "Carga Moderada";
//         } else {
//             return "Carga Baixa";
//         }
//     }


//     //Métodos de cálculo geral

//     public double calcularCRGeral() {
//         double somaPonderada = 0;
//         int totalCreditos = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina)) {
//                 double mediaFinal = disciplina.path("MEDIA_FINAL").asDouble();
//                 int creditos = disciplina.path("CREDITOS").asInt();
//                 somaPonderada += mediaFinal * creditos;
//                 totalCreditos += creditos;
//             }
//         }
    
//         return totalCreditos > 0 ? somaPonderada / totalCreditos : 0;
//     }
    
//     public double calcularPercentualFrequenciaGeral() {
//         double somaFrequencia = 0;
//         int totalDisciplinas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina)) {
//                 somaFrequencia += Transformacao.calcularPercentualFrequencia(disciplina);
//                 totalDisciplinas++;
//             }
//         }
    
//         return totalDisciplinas > 0 ? somaFrequencia / totalDisciplinas : 0;
//     }

//     public double calcularMediaGeral() {
//         double somaMedias = 0;
//         int totalDisciplinas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina)) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalDisciplinas++;
//             }
//         }
    
//         return totalDisciplinas > 0 ? somaMedias / totalDisciplinas : 0;
//     }

//     public int calcularAprovadasQuantidadeGeral() {
//         int aprovadas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina) && "Aprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 aprovadas++;
//             }
//         }
    
//         return aprovadas;
//     }

//     public double calcularAprovadasMediaGeral() {
//         double somaMedias = 0;
//         int totalAprovadas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina) && "Aprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalAprovadas++;
//             }
//         }
    
//         return totalAprovadas > 0 ? somaMedias / totalAprovadas : 0;
//     }

//     public int calcularReprovadasQuantidadeGeral() {
//         int reprovadas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina) && "Reprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 reprovadas++;
//             }
//         }
    
//         return reprovadas;
//     }

//     public double calcularReprovadasMediaGeral() {
//         double somaMedias = 0;
//         int totalReprovadas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina) && "Reprovado".equals(disciplina.path("SITUACAO_DISCIPLINA").asText())) {
//                 somaMedias += disciplina.path("MEDIA_FINAL").asDouble();
//                 totalReprovadas++;
//             }
//         }
    
//         return totalReprovadas > 0 ? somaMedias / totalReprovadas : 0;
//     }

//     public int calcularCursadasGeral() {
//         int cursadas = 0;
    
//         for (JsonNode disciplina : disciplinas) {
//             if (isPeriodoAnterior(disciplina)) {
//                 cursadas++;
//             }
//         }
    
//         return cursadas;
//     }

//     //Métodos que auxiliam o cálculo de outros métodos

//     public List<JsonNode> getDisciplinasPorPeriodo(String periodo, int ano) {
//         List<JsonNode> disciplinasPorPeriodo = new ArrayList<>();
    
//         for (JsonNode disciplina : disciplinas) {
//             String periodoDisciplina = disciplina.path("PERIODO_DISCIPLINA").asText();
//             int anoDisciplina = disciplina.path("ANO_DISCIPLINA").asInt();


//             if (periodoDisciplina.equals(periodo) && anoDisciplina == ano) {
//                 disciplinasPorPeriodo.add(disciplina);
//             }
//         }
    
//         return disciplinasPorPeriodo;
//     }

//     // Verifica se a disciplina é de um período anterior (ajustado para pegar qualquer ano antes do atual)
//     public boolean isPeriodoAnterior(JsonNode disciplina) {
//         int anoDisciplina = disciplina.path("ANO_DISCIPLINA").asInt();
//         int anoAtual = LocalDate.now().getYear();
//         return anoDisciplina < anoAtual; // Modificado para pegar qualquer ano anterior ao atual
//     }
    
// }
