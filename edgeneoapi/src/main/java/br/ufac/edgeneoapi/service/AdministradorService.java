package br.ufac.edgeneoapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.ufac.edgeneoapi.dto.AdministradorDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.AdministradorMapper;
import br.ufac.edgeneoapi.model.Administrador;
import br.ufac.edgeneoapi.repository.AdministradorRepository;

@Service
public class AdministradorService implements IService<AdministradorDto> {

    private final AdministradorRepository repo;
    private final AdministradorMapper mapper;

    public AdministradorService(AdministradorRepository repo, AdministradorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<AdministradorDto> getAll() {
        List<Administrador> administradores = repo.findAll();
        return administradores.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdministradorDto getById(Long id) {
        Administrador administrador = repo.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado com ID: " + id));
        return mapper.toDto(administrador);
    }

    @Override
    public AdministradorDto save(AdministradorDto administradorDto) {
        Administrador administrador = mapper.toEntity(administradorDto);
        Administrador administradorSalvo= repo.save(administrador);
        return mapper.toDto(administradorSalvo);
    }

    public AdministradorDto update(Long id, AdministradorDto administradorDto) {
        Administrador administradorExistente = repo.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado com ID: " + id));
        
        administradorExistente.setNome(administradorDto.nome());
        administradorExistente.setCentro(administradorDto.centro());
        administradorExistente.setUsuario(mapper.toEntity(administradorDto).getUsuario());

        Administrador updatedAdministrador = repo.save(administradorExistente);
        return mapper.toDto(updatedAdministrador);
    }

    @Override
    public void delete(Long id) {
        Administrador administrador = repo.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado com ID: " + id));
        repo.delete(administrador);
    }
}
