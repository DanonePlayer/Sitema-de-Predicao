package br.ufac.edgeneoapi.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.AdministradorDto;
import br.ufac.edgeneoapi.model.Administrador;

@Mapper(componentModel = "spring")
public interface AdministradorMapper {
    AdministradorMapper INSTANCE = Mappers.getMapper(AdministradorMapper.class);

    @Mapping(target = "usuarioId", source = "usuario.id")
    AdministradorDto toDto(Administrador administrador);

    @InheritInverseConfiguration
    Administrador toEntity(AdministradorDto administradorDto);
}
