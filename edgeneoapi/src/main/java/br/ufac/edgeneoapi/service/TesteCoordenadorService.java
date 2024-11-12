package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.TesteCoordenadorDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.TesteCoordenadorMapper;
import br.ufac.edgeneoapi.model.TesteCoordenador;
import br.ufac.edgeneoapi.repository.CoordenadorRepository;
import br.ufac.edgeneoapi.repository.TesteCoordenadorRepository;
import br.ufac.edgeneoapi.repository.TreinamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TesteCoordenadorService implements IService<TesteCoordenadorDto> {

    @Autowired
    private TesteCoordenadorRepository testeCoordenadorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private TreinamentoRepository treinamentoRepository;

    @Autowired
    private TesteCoordenadorMapper testeCoordenadorMapper;

    @Override
    public List<TesteCoordenadorDto> getAll() {
        return testeCoordenadorRepository.findAll()
                .stream()
                .map(testeCoordenadorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TesteCoordenadorDto getById(Long id) {
        TesteCoordenador testeCoordenador = testeCoordenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TesteCoordenador não encontrado com ID: " + id));
        return testeCoordenadorMapper.toDto(testeCoordenador);
    }

    @Override
    public TesteCoordenadorDto save(TesteCoordenadorDto testeCoordenadorDto) {
        TesteCoordenador testeCoordenador = testeCoordenadorMapper.toEntity(testeCoordenadorDto);
        // Verificando e setando as entidades relacionadas
        testeCoordenador.setCoordenador(coordenadorRepository.findById(testeCoordenadorDto.coordenadorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + testeCoordenadorDto.coordenadorId())));
        testeCoordenador.setTreinamento(treinamentoRepository.findById(testeCoordenadorDto.treinamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Treinamento não encontrado com ID: " + testeCoordenadorDto.treinamentoId())));

        TesteCoordenador savedTesteCoordenador = testeCoordenadorRepository.save(testeCoordenador);
        return testeCoordenadorMapper.toDto(savedTesteCoordenador);
    }

    @Override
    public void delete(Long id) {
        TesteCoordenador testeCoordenador = testeCoordenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TesteCoordenador não encontrado com ID: " + id));
        testeCoordenadorRepository.delete(testeCoordenador);
    }
}
