package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.TesteAlunoDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.TesteAlunoMapper;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.model.TesteAluno;
import br.ufac.edgeneoapi.repository.AlunoRepository;
import br.ufac.edgeneoapi.repository.TesteAlunoRepository;
import br.ufac.edgeneoapi.repository.TreinamentoRepository;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TesteAlunoService implements IService<TesteAlunoDto> {

    @Autowired
    private TesteAlunoRepository testeAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private TesteAlunoMapper testeAlunoMapper;

    @Override
    public List<TesteAlunoDto> getAll() {
        return testeAlunoRepository.findAll()
                .stream()
                .map(testeAlunoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TesteAlunoDto getById(Long id) {
        TesteAluno testeAluno = testeAlunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TesteAluno não encontrado com ID: " + id));
        return testeAlunoMapper.toDto(testeAluno);
    }

    @Override
    public TesteAlunoDto save(TesteAlunoDto testeAlunoDto) {
        TesteAluno testeAluno = testeAlunoMapper.toEntity(testeAlunoDto);
        // Verificando e setando as entidades relacionadas
        testeAluno.setAluno(alunoRepository.findById(testeAlunoDto.alunoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + testeAlunoDto.alunoId())));
        testeAluno.setTreinamento(treinamentoRepository.findById(testeAlunoDto.treinamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Treinamento não encontrado com ID: " + testeAlunoDto.treinamentoId())));
        
        // Salvando a entidade
        TesteAluno savedTesteAluno = testeAlunoRepository.save(testeAluno);
        return testeAlunoMapper.toDto(savedTesteAluno);
    }

    @Override
    public void delete(Long id) {
        TesteAluno testeAluno = testeAlunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TesteAluno não encontrado com ID: " + id));
        testeAlunoRepository.delete(testeAluno);
    }
    // Carregar os dados do aluno.
    // Gerar um CSV com os dados do aluno (para que o modelo Weka possa utilizá-los).
    // Carregar o modelo treinado.
    // Fazer a predição utilizando o modelo e os dados do aluno.
    // Retornar o resultado da predição.


    // 1. Gerar o CSV com dados do aluno e rodar o modelo treinado
    public double realizarPredicao(Long alunoId) throws Exception {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + alunoId));

        // Criar CSV temporário com dados do aluno
        File csvFile = criarCsvComDadosDoAluno(aluno);

        // Carregar dados do CSV para o formato Weka
        Instances data = carregarDadosCsv(csvFile);

        // Carregar o modelo treinado
        RandomForest randomForest = carregarModeloTreinado();

        // Criar nova instância para predição
        DenseInstance novaInstancia = criarInstanciaDeTeste(data, aluno);

        // Fazer a predição
        return randomForest.classifyInstance(novaInstancia);
    }

    // Função auxiliar para criar o CSV com os dados do aluno
    private File criarCsvComDadosDoAluno(Aluno aluno) throws IOException {
        File tempFile = File.createTempFile("dados_aluno", ".csv");

        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("id,curso,genero"); // Cabeçalho do CSV
            writer.println(aluno.getId() + "," + aluno.getCurso().getId() + "," +
                           (aluno.getGenero().equals("Masculino") ? 1 : 0)); // Exemplo com os atributos do aluno
        }

        return tempFile;
    }

    // Função auxiliar para carregar dados do CSV para Weka
    private Instances carregarDadosCsv(File file) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // Definir a última coluna como a classe
        return data;
    }

    // Função auxiliar para carregar o modelo treinado
    private RandomForest carregarModeloTreinado() {
        try {
            return (RandomForest) SerializationHelper.read("src/main/resources/modeloTreinado.model");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o modelo: " + e.getMessage(), e);
        }
    }

    // Função auxiliar para criar uma nova instância de teste
    private DenseInstance criarInstanciaDeTeste(Instances data, Aluno aluno) {
        double[] valoresNovos = new double[] {
            aluno.getId(),
            aluno.getCurso().getId(),
            aluno.getGenero().equals("Masculino") ? 1.0 : 0.0
        };

        DenseInstance novaInstancia = new DenseInstance(1.0, valoresNovos);
        novaInstancia.setDataset(data); // Associar ao dataset
        return novaInstancia;
    }
}