package br.ufac.edgeneoapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.DisciplinaDto;
import br.ufac.edgeneoapi.model.AlunoDisciplinas;
import br.ufac.edgeneoapi.model.Disciplina;

@Mapper(componentModel = "spring")
public interface DisciplinaMapper {

    DisciplinaMapper INSTANCE = Mappers.getMapper(DisciplinaMapper.class);

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(target = "alunoIds", expression = "java(mapAlunoDisciplinaToIds(disciplina.getAlunoDisciplinas()))")
    DisciplinaDto toDisciplinaDto(Disciplina disciplina);

    @Mapping(source = "cursoId", target = "curso.id")
    @Mapping(target = "alunoDisciplinas", ignore = true)
    Disciplina toDisciplina(DisciplinaDto disciplinaDto);

    // Método para mapear de AlunoDisciplina para os IDs dos alunos
    default List<Long> mapAlunoDisciplinaToIds(List<AlunoDisciplinas> alunoDisciplinas) {
        return alunoDisciplinas.stream()
                               .map(alunoDisciplina -> alunoDisciplina.getAluno().getId())
                               .collect(Collectors.toList());
    }
}
