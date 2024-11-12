package br.ufac.edgeneoapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.ufac.edgeneoapi.dto.LogsDto;
import br.ufac.edgeneoapi.model.Logs;

@Mapper(componentModel = "spring")
public interface LogsMapper {

    LogsMapper INSTANCE = Mappers.getMapper(LogsMapper.class);

    @Mapping(source = "usuario.id", target = "usuarioId")
    LogsDto toLogsDto(Logs logs);

    @Mapping(source = "usuarioId", target = "usuario.id")
    Logs toLogs(LogsDto logsDto);
}
