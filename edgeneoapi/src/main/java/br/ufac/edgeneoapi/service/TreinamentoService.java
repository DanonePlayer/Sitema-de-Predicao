package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.TreinamentoDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.TreinamentoMapper;
import br.ufac.edgeneoapi.model.Treinamento;
import br.ufac.edgeneoapi.repository.CursoRepository;
import br.ufac.edgeneoapi.repository.DadosBrutosRepository;
import br.ufac.edgeneoapi.repository.DadosProcessadosRepository;
import br.ufac.edgeneoapi.repository.TreinamentoRepository;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.SerializationHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TreinamentoService implements IService<TreinamentoDto> {

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DadosBrutosRepository dadosBrutosRepository;

    @Autowired
    private DadosProcessadosRepository dadosProcessadosRepository;

    @Autowired
    private TreinamentoMapper treinamentoMapper;

    @Override
    public List<TreinamentoDto> getAll() {
        return treinamentoRepository.findAll()
                .stream()
                .map(treinamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TreinamentoDto getById(Long id) {
        Treinamento treinamento = treinamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Treinamento não encontrado com ID: " + id));
        return treinamentoMapper.toDto(treinamento);
    }

    @Override
    public TreinamentoDto save(TreinamentoDto treinamentoDto) {
        Treinamento treinamento = treinamentoMapper.toEntity(treinamentoDto);
        // Setando entidades relacionadas
        treinamento.setCurso(cursoRepository.findById(treinamentoDto.cursoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + treinamentoDto.cursoId())));
        treinamento.setDadosBrutos(dadosBrutosRepository.findById(treinamentoDto.dadosBrutosId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosBrutos não encontrados com ID: " + treinamentoDto.dadosBrutosId())));
        treinamento.setDadosProcessados(dadosProcessadosRepository.findById(treinamentoDto.dadosProcessadosId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosProcessados não encontrados com ID: " + treinamentoDto.dadosProcessadosId())));

        Treinamento savedTreinamento = treinamentoRepository.save(treinamento);
        return treinamentoMapper.toDto(savedTreinamento);
    }

    @Override
    public void delete(Long id) {
        Treinamento treinamento = treinamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Treinamento não encontrado com ID: " + id));
        treinamentoRepository.delete(treinamento);
    }



    // Método para identificar os períodos únicos no arquivo CSV
    public Set<Integer> buscarPeriodosDistintosNoArquivo() throws IOException {
        File file = new File("src/main/resources/4_ano_sem_media.csv");
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();

        // Obter índice da coluna "disciplinaPeriodo"
        int periodoIndex = data.attribute("PERIODO_IDEAL").index();

        // Armazenar períodos distintos
        Set<Integer> periodos = new HashSet<>();
        for (int i = 0; i < data.numInstances(); i++) {
            periodos.add((int) data.instance(i).value(periodoIndex));
        }
        // System.out.println("Períodos encontrados: " + periodos);
        return periodos;
    }

    // Método para treinar modelos separados para cada período encontrado no CSV
    public Map<String, Object> treinarModelosPorPeriodo() throws Exception {
        Set<Integer> periodos = buscarPeriodosDistintosNoArquivo();

        if (periodos.isEmpty()) {
            throw new Exception("Nenhum período encontrado no arquivo CSV.");
        }

        Map<String, Object> resultadosTreinamento = new LinkedHashMap<>();

        // Itera sobre cada período para treinar e salvar um modelo
        for (int periodo : periodos) {
            // Carregar e filtrar os dados para o período específico
            Instances dadosPorPeriodo = carregarDadosFiltradosPorPeriodo(periodo);

            if (dadosPorPeriodo.isEmpty()) {
                System.out.println("Nenhum dado encontrado para o período " + periodo);
                continue;
            }

            RandomForest randomForest = new RandomForest();
            randomForest.buildClassifier(dadosPorPeriodo);

            // Avaliar o modelo com validação cruzada de 10-fold
            Evaluation evaluation = new Evaluation(dadosPorPeriodo);
            evaluation.crossValidateModel(randomForest, dadosPorPeriodo, 10, new Random(1));

            // Log das propriedades do modelo
            // System.out.println("Período: " + periodo);
            // System.out.println("Número de Árvores: " + randomForest.getNumIterations());
            // System.out.println("Profundidade Máxima das Árvores: " + randomForest.getMaxDepth());
            // System.out.println("Número de Atributos utilizados pelo Modelo: " + randomForest.getNumFeatures());
            // System.out.println("Número de Instâncias de Treinamento: " + dadosPorPeriodo.numInstances());
            // System.out.println("Número de Atributos no Dataset: " + dadosPorPeriodo.numAttributes());
            // System.out.println("Nome do Atributo Classe: " + dadosPorPeriodo.classAttribute().name());

            // // Log das métricas de avaliação
            // System.out.println("Instâncias Corretamente Classificadas: " + evaluation.correct());
            // System.out.println("Instâncias Incorretamente Classificadas: " + evaluation.incorrect());
            // System.out.println("Estatística Kappa: " + evaluation.kappa());
            // System.out.println("Erro Absoluto Médio: " + evaluation.meanAbsoluteError());
            // System.out.println("Erro Médio Quadrático: " + evaluation.rootMeanSquaredError());
            // System.out.println("Resumo: " + evaluation.toSummaryString());

            // Salva o modelo treinado com um nome específico para o período
            String modelPath = "modeloTreinado_periodo_" + periodo + ".model";
            SerializationHelper.write(modelPath, randomForest);

            // Armazena os resultados do treinamento para cada período
            Map<String, Object> resultPeriodo = new LinkedHashMap<>();
            resultPeriodo.put("Instâncias Corretamente Classificadas", evaluation.correct());
            resultPeriodo.put("Instâncias Incorretamente Classificadas", evaluation.incorrect());
            resultPeriodo.put("Estatística Kappa", evaluation.kappa());
            resultPeriodo.put("Erro Absoluto Médio", evaluation.meanAbsoluteError());
            resultPeriodo.put("Erro Médio Quadrático", evaluation.rootMeanSquaredError());
            resultPeriodo.put("Resumo", evaluation.toSummaryString());
            resultPeriodo.put("Modelo salvo em", modelPath);

            resultadosTreinamento.put("Período " + periodo, resultPeriodo);
        }

        return resultadosTreinamento;
    }


    // Método auxiliar para carregar e filtrar dados de um período específico
    public Instances carregarDadosFiltradosPorPeriodo(int periodo) throws IOException {
        File file = new File("src/main/resources/4_ano_sem_media.csv");
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();
        // data.setClassIndex(data.numAttributes() - 1);
        int situacaoIndex = data.attribute("SITUACAO").index();
        data.setClassIndex(situacaoIndex);
        
        // Filtrar dados para o período especificado
        Instances filteredData = new Instances(data, 0);  // Inicializa um dataset vazio com a mesma estrutura
        int periodoIndex = data.attribute("PERIODO_IDEAL").index();
        
        for (int i = 0; i < data.numInstances(); i++) {
            if (data.instance(i).value(periodoIndex) == periodo) {
                filteredData.add(data.instance(i));
            }
        }

        for (int i = 0; i < data.numAttributes(); i++) {
            if (data.attribute(i).isNominal()) {
                System.out.println("Atributo: " + data.attribute(i).name() + ", valores permitidos: ");
                for (int j = 0; j < data.attribute(i).numValues(); j++) {
                    System.out.println(" - " + data.attribute(i).value(j));
                }
            }
        }
        
        return filteredData;
    }














    











    public Map<String, String> preverSituacaoParaDisciplinasDoProximoPeriodo() throws Exception {
        int periodoDesejado = 8;
        // Carregar o modelo treinado para o período desejado
        String modelPath = "modeloTreinado_periodo_" + periodoDesejado + ".model";
        RandomForest modelo = (RandomForest) SerializationHelper.read(modelPath);
    
        // Carregar o dataset original para configurar as instâncias de teste
        File file = new File("src/main/resources/4_ano_sem_media.csv");
        if (!file.exists()) {
            throw new IOException("Arquivo CSV não encontrado.");
        }
    
        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances dataset = loader.getDataSet();
    
        int situacaoIndex = dataset.attribute("SITUACAO").index();
        dataset.setClassIndex(situacaoIndex);
    
        // Obter a lista dinâmica de disciplinas para o período desejado
        Set<String> disciplinasParaPeriodo = buscarDisciplinasPorPeriodo(dataset, periodoDesejado);
    
        // Mapa para armazenar os resultados das predições para cada disciplina do período
        Map<String, String> resultadosPredicao = new LinkedHashMap<>();
    
        // Realizar a previsão para cada disciplina do período
        for (String disciplina : disciplinasParaPeriodo) {
            // Cria a instância de teste com os atributos gerais do aluno e a disciplina específica
            Instance instanciaDeTeste = criarInstanciaDeTesteParaDisciplina(dataset, disciplina, periodoDesejado);
    
            // Realiza a previsão
            double predicao = modelo.classifyInstance(instanciaDeTeste);
            String resultado = dataset.classAttribute().value((int) predicao);
    
            // Armazena o resultado da previsão
            resultadosPredicao.put(disciplina, resultado);
            System.out.println("Resultado da predição para " + disciplina + ": " + resultado);
            System.out.println("--------------------------------------------");
        }
    
        return resultadosPredicao;
    }
    
    // Método para buscar disciplinas específicas para um determinado período
    public Set<String> buscarDisciplinasPorPeriodo(Instances dataset, int periodo) {
        Set<String> disciplinas = new HashSet<>();
        int periodoIndex = dataset.attribute("PERIODO_IDEAL").index();
        int nomeAtivCurricIndex = dataset.attribute("NOME_ATIV_CURRIC").index();
    
        for (int i = 0; i < dataset.numInstances(); i++) {
            Instance instancia = dataset.instance(i);
            if ((int) instancia.value(periodoIndex) == periodo) {
                String disciplina = instancia.stringValue(nomeAtivCurricIndex);
                disciplinas.add(disciplina);
            }
        }
    
        return disciplinas;
    }
    
    // Método para criar uma instância de teste sem definir MEDIA_FINAL ou SITUACAO
    public Instance criarInstanciaDeTesteParaDisciplina(Instances dataset, String disciplina, int periodo) {
        Instance exemplo = new DenseInstance(dataset.numAttributes());
        exemplo.setDataset(dataset);
    
        // Atribuir valores aos atributos gerais do aluno
        exemplo.setValue(dataset.attribute("PERIODO_IDEAL"), periodo);
        exemplo.setValue(dataset.attribute("GENERO"), "F");
        exemplo.setValue(dataset.attribute("ESTADO_CIVIL"), "Solteiro(a)");
        exemplo.setValue(dataset.attribute("FORMA_INGRESSO"), "Vestibular");
        exemplo.setValue(dataset.attribute("NOME_ATIV_CURRIC"), disciplina);
        exemplo.setValue(dataset.attribute("CH_TOTAL"), 60.0);
        exemplo.setValue(dataset.attribute("NUM_FALTAS"), 4.0);
        // Outros atributos gerais
        // exemplo.setValue(dataset.attribute("MEDIA_FINAL"), 5.0);
        exemplo.setValue(dataset.attribute("IDADE_PRIMEIRO_ANO"), 33);
        exemplo.setValue(dataset.attribute("DURACAO_CURSO"), 60.0);
        exemplo.setValue(dataset.attribute("PERCENTUAL_FREQUENCIA_PERIODO"), 58.79);
        exemplo.setValue(dataset.attribute("PERCENTUAL_FREQUENCIA_DISCIPLINA"), 74.44);
        exemplo.setValue(dataset.attribute("MEDIA_PERIODO"), 1.04);
        exemplo.setValue(dataset.attribute("MEDIA_SEMESTRE"), 3.87);
        exemplo.setValue(dataset.attribute("FORMA_EVASAO"), "Sem Evaso");
    
        return exemplo;
    }
    





















    public List<List<String>> carregarDados() throws IOException {
        File file = new File("src/main/resources/4ano.csv");
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();
        // data.setClassIndex(data.numAttributes() - 1);
        int situacaoIndex = data.attribute("SITUACAO").index();
        data.setClassIndex(situacaoIndex);
        List<List<String>> records = new ArrayList<>();
        for (int i = 0; i < data.numInstances(); i++) {
            List<String> record = new ArrayList<>();
            for (int j = 0; j < data.numAttributes(); j++) {
                record.add(data.instance(i).toString(j));
            }
            records.add(record);
        }

        return records;
    }

}