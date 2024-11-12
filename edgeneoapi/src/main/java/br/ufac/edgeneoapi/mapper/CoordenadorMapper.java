package br.ufac.edgeneoapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.CoordenadorDto;
import br.ufac.edgeneoapi.model.Coordenador;

@Mapper(componentModel = "spring")
public interface CoordenadorMapper {

    CoordenadorMapper INSTANCE = Mappers.getMapper(CoordenadorMapper.class);

    @Mapping(source = "usuario.id", target = "usuario_id")
    @Mapping(source = "curso.id", target = "curso_id")
    CoordenadorDto toCoordenadorDto(Coordenador coordenador);

    @Mapping(source = "usuario_id", target = "usuario.id")
    @Mapping(source = "curso_id", target = "curso.id")
    Coordenador toCoordenador(CoordenadorDto coordenadorDto);
}
