package br.ufac.edgeneoapi.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.AlunoDto;
import br.ufac.edgeneoapi.model.Aluno;
import br.ufac.edgeneoapi.model.AlunoDisciplinas;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);


    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(target = "alunoDisciplinas", ignore = true)
    AlunoDto toAlunoDto(Aluno aluno);

    @InheritInverseConfiguration
    @Mapping(source = "usuarioId", target = "usuario.id")
    @Mapping(source = "cursoId", target = "curso.id")
    @Mapping(target = "alunoDisciplinas", ignore = true)
    Aluno toAluno(AlunoDto alunoDto);

    @Named("mapAlunoDisciplinasToDisciplinaIds")
    default List<Long> mapAlunoDisciplinasToDisciplinaIds(List<AlunoDisciplinas> alunoDisciplinas) {
        return alunoDisciplinas != null ? alunoDisciplinas.stream()
                .map(alunoDisciplina -> alunoDisciplina.getDisciplina().getId())
                .collect(Collectors.toList()) : new ArrayList<>();
    }
}
