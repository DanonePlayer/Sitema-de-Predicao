package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.DadosBrutosDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.DadosBrutosMapper;
import br.ufac.edgeneoapi.model.DadosBrutos;
import br.ufac.edgeneoapi.repository.DadosBrutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosBrutosService implements IService<DadosBrutosDto> {

    @Autowired
    private DadosBrutosRepository dadosBrutosRepository;

    @Autowired
    private DadosBrutosMapper dadosBrutosMapper;

    @Override
    public List<DadosBrutosDto> getAll() {
        return dadosBrutosRepository.findAll()
                .stream()
                .map(dadosBrutosMapper::toDadosBrutosDto)
                .collect(Collectors.toList());
    }

    @Override
    public DadosBrutosDto getById(Long id) {
        DadosBrutos dadosBrutos = dadosBrutosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosBrutos não encontrado com ID: " + id));
        return dadosBrutosMapper.toDadosBrutosDto(dadosBrutos);
    }

    @Override
    public DadosBrutosDto save(DadosBrutosDto dadosBrutosDto) {
        DadosBrutos dadosBrutos = dadosBrutosMapper.toDadosBrutos(dadosBrutosDto);
        DadosBrutos savedDadosBrutos = dadosBrutosRepository.save(dadosBrutos);
        return dadosBrutosMapper.toDadosBrutosDto(savedDadosBrutos);
    }

    @Override
    public void delete(Long id) {
        DadosBrutos dadosBrutos = dadosBrutosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("DadosBrutos não encontrado com ID: " + id));
        dadosBrutosRepository.delete(dadosBrutos);
    }
}
