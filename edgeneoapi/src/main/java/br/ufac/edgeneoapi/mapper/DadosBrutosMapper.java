package br.ufac.edgeneoapi.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.DadosBrutosDto;
import br.ufac.edgeneoapi.model.DadosBrutos;

@Mapper(componentModel = "spring")
public interface DadosBrutosMapper {

    DadosBrutosMapper INSTANCE = Mappers.getMapper(DadosBrutosMapper.class);

    DadosBrutosDto toDadosBrutosDto(DadosBrutos dadosBrutos);

    @InheritInverseConfiguration
    DadosBrutos toDadosBrutos(DadosBrutosDto dadosBrutosDto);
}
