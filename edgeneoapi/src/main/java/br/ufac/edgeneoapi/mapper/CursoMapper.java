package br.ufac.edgeneoapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.CursoDto;
import br.ufac.edgeneoapi.model.Curso;

@Mapper(componentModel = "spring", uses = {AlunoMapper.class, DisciplinaMapper.class})
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    @Mapping(target = "alunos", source = "alunos")
    @Mapping(target = "disciplinas", source = "disciplinas")
    CursoDto toCursoDto(Curso curso);

    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "disciplinas", ignore = true)
    Curso toCurso(CursoDto cursoDto);
}
