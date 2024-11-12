package br.ufac.edgeneoapi.mapper;

import br.ufac.edgeneoapi.dto.TesteCoordenadorDto;
import br.ufac.edgeneoapi.model.TesteCoordenador;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TesteCoordenadorMapper {

    @Mappings({
            @Mapping(source = "coordenador.id", target = "coordenadorId"),
            @Mapping(source = "treinamento.id", target = "treinamentoId")
    })
    TesteCoordenadorDto toDto(TesteCoordenador testeCoordenador);

    @InheritInverseConfiguration
    TesteCoordenador toEntity(TesteCoordenadorDto testeCoordenadorDto);
}
