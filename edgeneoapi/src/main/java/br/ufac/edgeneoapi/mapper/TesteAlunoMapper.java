package br.ufac.edgeneoapi.mapper;

import br.ufac.edgeneoapi.dto.TesteAlunoDto;
import br.ufac.edgeneoapi.model.TesteAluno;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TesteAlunoMapper {

    TesteAlunoMapper INSTANCE = Mappers.getMapper(TesteAlunoMapper.class);

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "treinamento.id", target = "treinamentoId")
    TesteAlunoDto toDto(TesteAluno testeAluno);

    @InheritInverseConfiguration
    TesteAluno toEntity(TesteAlunoDto dto);
}
