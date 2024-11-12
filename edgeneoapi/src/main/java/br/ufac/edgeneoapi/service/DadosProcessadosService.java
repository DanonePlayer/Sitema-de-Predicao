package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.DadosProcessadosDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.DadosProcessadosMapper;
import br.ufac.edgeneoapi.model.DadosProcessados;
import br.ufac.edgeneoapi.repository.DadosProcessadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosProcessadosService implements IService<DadosProcessadosDto> {

    @Autowired
    private DadosProcessadosRepository dadosProcessadosRepository;

    @Autowired
    private DadosProcessadosMapper dadosProcessadosMapper;

    @Override
    public List<DadosProcessadosDto> getAll() {
        return dadosProcessadosRepository.findAll()
                .stream()
                .map(dadosProcessadosMapper::toDadosProcessadosDto)
                .collect(Collectors.toList());
    }

    @Override
    public DadosProcessadosDto getById(Long id) {
        DadosProcessados dadosProcessados = dadosProcessadosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosProcessados não encontrado com ID: " + id));
        return dadosProcessadosMapper.toDadosProcessadosDto(dadosProcessados);
    }

    @Override
    public DadosProcessadosDto save(DadosProcessadosDto dadosProcessadosDto) {
        DadosProcessados dadosProcessados = dadosProcessadosMapper.toDadosProcessados(dadosProcessadosDto);
        DadosProcessados savedDadosProcessados = dadosProcessadosRepository.save(dadosProcessados);
        return dadosProcessadosMapper.toDadosProcessadosDto(savedDadosProcessados);
    }

    @Override
    public void delete(Long id) {
        DadosProcessados dadosProcessados = dadosProcessadosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosProcessados não encontrado com ID: " + id));
        dadosProcessadosRepository.delete(dadosProcessados);
    }
}
