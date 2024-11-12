package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.CursoDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.CursoMapper;
import br.ufac.edgeneoapi.model.Curso;
import br.ufac.edgeneoapi.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService implements IService<CursoDto> {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoMapper cursoMapper;

    @Override
    public List<CursoDto> getAll() {
        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::toCursoDto)
                .collect(Collectors.toList());
    }

    @Override
    public CursoDto getById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id));
        return cursoMapper.toCursoDto(curso);
    }

    @Override
    public CursoDto save(CursoDto cursoDto) {
        Curso curso = cursoMapper.toCurso(cursoDto);
        Curso savedCurso = cursoRepository.save(curso);
        return cursoMapper.toCursoDto(savedCurso);
    }

    @Override
    public void delete(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id));
        cursoRepository.delete(curso);
    }
}
