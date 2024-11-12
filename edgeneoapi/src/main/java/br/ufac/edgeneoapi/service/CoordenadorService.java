package br.ufac.edgeneoapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufac.edgeneoapi.dto.CoordenadorDto;
import br.ufac.edgeneoapi.enums.EStatus;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.CoordenadorMapper;
import br.ufac.edgeneoapi.model.Coordenador;
import br.ufac.edgeneoapi.repository.CoordenadorRepository;
import br.ufac.edgeneoapi.repository.CursoRepository;
import br.ufac.edgeneoapi.repository.UsuarioRepository;

@Service
public class CoordenadorService implements IService<CoordenadorDto> {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordenadorMapper coordenadorMapper;

    @Override
    public List<CoordenadorDto> getAll() {
        return coordenadorRepository.findAll()
                .stream()
                .map(coordenadorMapper::toCoordenadorDto)
                .collect(Collectors.toList());
    }

    @Override
    public CoordenadorDto getById(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + id));
        return coordenadorMapper.toCoordenadorDto(coordenador);
    }

    @Override

    public CoordenadorDto save(CoordenadorDto coordenadorDto) {
        // System.out.println(coordenadorDto.portaria());
        Coordenador coordenador = coordenadorMapper.toCoordenador(coordenadorDto);
        // System.out.println(coordenador.getId());
        if (!cursoRepository.existsById(coordenador.getCurso().getId())) {
            throw new RecursoNaoEncontradoException("Curso não encontrado com ID: " + coordenadorDto.curso_id());
        }
        if (!usuarioRepository.existsById(coordenador.getUsuario().getId())) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + coordenadorDto.usuario_id());
        }
        // Salvar no repositório e retornar
        return coordenadorMapper.toCoordenadorDto(coordenadorRepository.save(coordenador));
    }

    @Override
    public void delete(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + id));
        coordenadorRepository.delete(coordenador);
    }

    public void aprovarCoordenador(Long coordenadorId) {
        Coordenador coordenador = coordenadorRepository.findById(coordenadorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + coordenadorId));
        
        coordenador.setStatus(EStatus.ATIVO);
        coordenadorRepository.save(coordenador);
    }

    public void associarDocumentoPortaria(Long coordenadorId, String caminhoDocumento) {
        Coordenador coordenador = coordenadorRepository.findById(coordenadorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com ID: " + coordenadorId));
        
        coordenador.setPortaria(caminhoDocumento);
        coordenadorRepository.save(coordenador);
    }

    public CoordenadorDto update(Long id, CoordenadorDto coordenadorDto) {
        // Busca o coordenador existente pelo ID
        Coordenador coordenadorExistente = coordenadorRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Coordenador não encontrado com o ID: " + id));
    
        // Atualiza os campos do coordenador com os valores do DTO
        coordenadorExistente.setCurso(cursoRepository.findById(coordenadorDto.curso_id())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com o ID: " + coordenadorDto.curso_id())));
        coordenadorExistente.setPortaria(coordenadorDto.portaria());
        coordenadorExistente.setStatus(coordenadorDto.status());
    
        // Salva as alterações no banco de dados
        Coordenador coordenadorAtualizado = coordenadorRepository.save(coordenadorExistente);
    
        // Converte para DTO e retorna
        return coordenadorMapper.toCoordenadorDto(coordenadorAtualizado);
    }
    
    
}
