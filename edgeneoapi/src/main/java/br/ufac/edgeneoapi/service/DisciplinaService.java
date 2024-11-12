package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.DisciplinaDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.DisciplinaMapper;
import br.ufac.edgeneoapi.model.Disciplina;
import br.ufac.edgeneoapi.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinaService implements IService<DisciplinaDto> {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Override
    public List<DisciplinaDto> getAll() {
        return disciplinaRepository.findAll()
                .stream()
                .map(disciplinaMapper::toDisciplinaDto)
                .collect(Collectors.toList());
    }

    @Override
    public DisciplinaDto getById(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina não encontrada com ID: " + id));
        return disciplinaMapper.toDisciplinaDto(disciplina);
    }

    @Override
    public DisciplinaDto save(DisciplinaDto disciplinaDto) {
        Disciplina disciplina = disciplinaMapper.toDisciplina(disciplinaDto);
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDisciplinaDto(savedDisciplina);
    }

    @Override
    public void delete(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina não encontrada com ID: " + id));
        disciplinaRepository.delete(disciplina);
    }
}
