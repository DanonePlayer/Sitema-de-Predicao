package br.ufac.edgeneoapi.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.DadosProcessadosDto;
import br.ufac.edgeneoapi.model.DadosProcessados;

@Mapper(componentModel = "spring")
public interface DadosProcessadosMapper {

    DadosProcessadosMapper INSTANCE = Mappers.getMapper(DadosProcessadosMapper.class);

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "dadosBrutos.id", target = "dadosBrutosId")
    DadosProcessadosDto toDadosProcessadosDto(DadosProcessados dadosProcessados);

    @InheritInverseConfiguration
    DadosProcessados toDadosProcessados(DadosProcessadosDto dadosProcessadosDto);
}
