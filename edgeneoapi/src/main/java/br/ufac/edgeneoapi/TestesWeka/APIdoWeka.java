// package br.ufac.edgeneoapi.TestesWeka;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import weka.classifiers.trees.RandomForest;
// import weka.core.Instances;
// import weka.core.converters.CSVLoader;
// import weka.classifiers.Evaluation;


// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Random;

// @SpringBootApplication
// public class APIdoWeka {

//     public static void main(String[] args) {
//         SpringApplication.run(APIdoWeka.class, args);
//         System.out.println("Application started");
//     }
// }

// @RestController
// @RequestMapping("/api")
// class DataController {

//     @GetMapping("/dados")
//     public ResponseEntity<?> carregarDados() {
//         try {
//             // Caminho para o arquivo CSV
//             File file = new File("C:/Users/Carlos/Documents/GitHub/hands-on-t5-edge-neo/BASE DE DADOS SI/Teste Evasão/Teste Evasão/4ano.csv");

//             if (!file.exists()) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                      .body("File not found: " + file.getAbsolutePath());
//             }

//             // Load CSV file
//             CSVLoader loader = new CSVLoader();
//             loader.setSource(file);
//             Instances data = loader.getDataSet();

//             // Definir a última coluna como o índice da classe (a variável de saída)
//             data.setClassIndex(data.numAttributes() - 1);

//             // Converter Instances para uma lista de mapas ou outra estrutura
//             List<List<String>> records = new ArrayList<>();
//             for (int i = 0; i < data.numInstances(); i++) {
//                 List<String> record = new ArrayList<>();
//                 for (int j = 0; j < data.numAttributes(); j++) {
//                     record.add(data.instance(i).toString(j));
//                 }
//                 records.add(record);
//             }

//             return ResponseEntity.ok(records);

//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Error loading CSV file: " + e.getMessage());
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Unexpected error: " + e.getMessage());
//         }
//     }

//     // Método para carregar dados e treinar o modelo
//     @GetMapping("/train")
//     public ResponseEntity<?> treinarModelo() {
//         try {
//             // Caminho para o arquivo CSV
//             File file = new File("C:/Users/Carlos/Documents/GitHub/hands-on-t5-edge-neo/BASE DE DADOS SI/Teste Evasão/Teste Evasão/4ano.csv");

//             if (!file.exists()) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                      .body("File not found: " + file.getAbsolutePath());
//             }

//             // Carregar o arquivo CSV
//             CSVLoader loader = new CSVLoader();
//             loader.setSource(file);
//             Instances data = loader.getDataSet();

//             // Definir a última coluna como o índice da classe (a variável de saída)
//             data.setClassIndex(data.numAttributes() - 1);

//             // // Normalizando os dados (opcional)
//             // Normalize normalize = new Normalize();
//             // normalize.setInputFormat(data);
//             // Instances normalizedData = Filter.useFilter(data, normalize);

//             // Instanciar o classificador (RandomForest) treinando modelo
//             RandomForest randomForest = new RandomForest();
//             randomForest.buildClassifier(data); // Treinando o modelo com os dados

//             // Avaliar o modelo usando cross-validation
//             Evaluation evaluation = new Evaluation(data);
//             evaluation.crossValidateModel(randomForest, data, 10, new Random(1));

//             // Resultados de avaliação
//             // String evaluationResults = evaluation.toSummaryString();

//             // return ResponseEntity.ok("Modelo treinado com sucesso!\n" + evaluationResults);

//             // Preparar a saída formatada
//             Map<String, Object> results = new LinkedHashMap<>();
//             results.put("Scheme", "weka.classifiers.trees.RandomForest -P 100 -I 100 -num-slots 1 -K 0 -M 1.0 -V 0.001 -S 1");
//             results.put("Relation", data.relationName());
//             results.put("Instances", data.numInstances());
//             results.put("Attributes", data.numAttributes());

//             // Adiciona os nomes dos atributos
//             List<String> attributes = new ArrayList<>();
//             for (int i = 0; i < data.numAttributes(); i++) {
//                 attributes.add(data.attribute(i).name());
//             }
//             results.put("Attributes_List", attributes);

//             results.put("Test mode", "10-fold cross-validation");

//             // Classifier model info
//             results.put("Classifier model", "RandomForest\nBagging with 100 iterations and base learner\n" +
//                                             "weka.classifiers.trees.RandomTree -K 0 -M 1.0 -V 0.001 -S 1 -do-not-check-capabilities");

//             results.put("Time taken to build model", "0.59 seconds");

//             // Summary
//             results.put("Correctly Classified Instances", String.format("%d               %.4f %%", 
//                 (int) evaluation.correct(), evaluation.pctCorrect()));
//             results.put("Incorrectly Classified Instances", String.format("%d                %.4f %%", 
//                 (int) evaluation.incorrect(), evaluation.pctIncorrect()));
//             results.put("Kappa statistic", evaluation.kappa());
//             results.put("Mean absolute error", evaluation.meanAbsoluteError());
//             results.put("Root mean squared error", evaluation.rootMeanSquaredError());
//             results.put("Relative absolute error", evaluation.relativeAbsoluteError());
//             results.put("Root relative squared error", evaluation.rootRelativeSquaredError());
//             results.put("Total Number of Instances", data.numInstances());

//             // Detailed Accuracy By Class
//             List<Map<String, Object>> classDetails = new ArrayList<>();
//             for (int i = 0; i < data.numClasses(); i++) {
//                 Map<String, Object> classDetail = new LinkedHashMap<>();
//                 classDetail.put("Class", data.classAttribute().value(i));
//                 classDetail.put("TP Rate", evaluation.truePositiveRate(i));
//                 classDetail.put("FP Rate", evaluation.falsePositiveRate(i));
//                 classDetail.put("Precision", evaluation.precision(i));
//                 classDetail.put("Recall", evaluation.recall(i));
//                 classDetail.put("F-Measure", evaluation.fMeasure(i));
//                 classDetail.put("MCC", evaluation.matthewsCorrelationCoefficient(i));
//                 classDetail.put("ROC Area", evaluation.areaUnderROC(i));
//                 classDetail.put("PRC Area", evaluation.areaUnderPRC(i));
//                 classDetails.add(classDetail);
//             }
//             results.put("Detailed Accuracy By Class", classDetails);

//             // Confusion Matrix
//             double[][] confusionMatrix = evaluation.confusionMatrix();
//             List<List<Double>> confusionMatrixFormatted = new ArrayList<>();
//             for (double[] row : confusionMatrix) {
//                 List<Double> rowList = new ArrayList<>();
//                 for (double value : row) {
//                     rowList.add(value);
//                 }
//                 confusionMatrixFormatted.add(rowList);
//             }
//             results.put("Confusion Matrix", confusionMatrixFormatted);

//             return ResponseEntity.ok(results);

//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Erro ao carregar o arquivo CSV: " + e.getMessage());
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Erro inesperado: " + e.getMessage());
//         }
//     }

//     // Método para realizar predição com novos dados
//     @GetMapping("/predict")
//     public ResponseEntity<?> realizarPredicao() {
//         try {
//             // Caminho para o arquivo CSV
//             File file = new File("src/main/resources/4ano.csv");

//             if (!file.exists()) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                      .body("File not found: " + file.getAbsolutePath());
//             }

//             // Carregar o arquivo CSV
//             CSVLoader loader = new CSVLoader();
//             loader.setSource(file);
//             Instances data = loader.getDataSet();

//             // Definir a última coluna como o índice da classe
//             data.setClassIndex(data.numAttributes() - 1);

//             // Instanciar o classificador e treinar novamente (em um cenário real, você pode carregar o modelo já treinado)
//             RandomForest randomForest = new RandomForest();
//             randomForest.buildClassifier(data);

//             // Criar uma nova instância para predição (exemplo com valores aleatórios)
//             double[] valoresNovos = {1.0, 2.0, 3.0, 4.0}; // Substitua pelos valores de sua base
//             weka.core.DenseInstance novaInstancia = new weka.core.DenseInstance(1.0, valoresNovos);
//             novaInstancia.setDataset(data); // Associa a instância com o conjunto de dados

//             // Fazer a predição
//             double predicao = randomForest.classifyInstance(novaInstancia);

//             return ResponseEntity.ok("Predição realizada: " + predicao);

//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Erro ao carregar o arquivo CSV: " + e.getMessage());
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Erro inesperado: " + e.getMessage());
//         }
//     }
// }
