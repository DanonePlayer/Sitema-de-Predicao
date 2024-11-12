package br.ufac.edgeneoapi.TestesWeka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ModeloVerificacaoController {

    // Variável para definir o período manualmente
    private int periodo = 7; // Altere o valor para o período desejado

    @GetMapping("/verificar-modelo")
    public Map<String, Object> verificarModelo() {
        Map<String, Object> resultado = new HashMap<>();
        try {
            // Caminho para o modelo e dados específicos ao período
            String modelPath = "src/main/resources/modeloTreinado_periodo_" + periodo + ".model";
            String dataPath = "src/main/resources/4ano_sem_creditos.csv";

            // Carregar o modelo específico do período
            RandomForest modeloTreinado = (RandomForest) SerializationHelper.read(new File(modelPath).getAbsolutePath());

            // Carregar e filtrar os dados de treinamento para o período específico
            Instances dadosTreinamento = carregarDadosFiltradosPorPeriodo(periodo, dataPath);

            // Informações do modelo
            resultado.put("NumeroDeArvores", modeloTreinado.getNumIterations());
            resultado.put("ProfundidadeMaximaDasArvores", modeloTreinado.getMaxDepth());
            resultado.put("DetalhesDoModelo", modeloTreinado.toString());
            resultado.put("NumeroDeAtributosmodelotreinado", modeloTreinado.getNumFeatures());

            // Detalhes dos dados de treinamento
            resultado.put("NumeroDeAtributos", dadosTreinamento.numAttributes());
            resultado.put("NumeroDeInstancias", dadosTreinamento.numInstances());
            resultado.put("NomeAtributoClasse", dadosTreinamento.classAttribute().name());
            

            // Verificar consistência do modelo
            if (dadosTreinamento.numAttributes() - 1 != modeloTreinado.getNumFeatures()) {
                resultado.put("Consistencia", "O modelo pode ter sido treinado com um número diferente de atributos.");
            } else {
                resultado.put("Consistencia", "O modelo está consistente com os dados de treinamento para o período " + periodo);
            }
        } catch (Exception e) {
            resultado.put("Erro", "Erro ao carregar e verificar o modelo: " + e.getMessage());
        }
        return resultado;
    }

    // Método para carregar e filtrar dados do CSV para o período especificado
    private Instances carregarDadosFiltradosPorPeriodo(int periodo, String dataPath) throws Exception {
        File file = new File(dataPath);
        if (!file.exists()) {
            throw new IOException("Arquivo CSV não encontrado: " + file.getAbsolutePath());
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);

        // Filtrar os dados para o período especificado
        Instances dadosFiltrados = new Instances(data, 0); // Cria uma estrutura vazia com a mesma estrutura do CSV
        int indicePeriodo = data.attribute("PERIODO_IDEAL ").index();

        for (int i = 0; i < data.numInstances(); i++) {
            if ((int) data.instance(i).value(indicePeriodo) == periodo) {
                dadosFiltrados.add(data.instance(i));
            }
        }

        return dadosFiltrados;
    }
}
