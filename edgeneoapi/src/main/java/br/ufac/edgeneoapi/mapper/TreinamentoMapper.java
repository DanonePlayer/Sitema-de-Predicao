package br.ufac.edgeneoapi.mapper;

import br.ufac.edgeneoapi.dto.TreinamentoDto;
import br.ufac.edgeneoapi.model.Treinamento;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TreinamentoMapper {
    
    @Mappings({
            @Mapping(source = "curso.id", target = "cursoId"),
            @Mapping(source = "dadosBrutos.id", target = "dadosBrutosId"),
            @Mapping(source = "dadosProcessados.id", target = "dadosProcessadosId"),
            @Mapping(source = "administrador.id", target = "administradorId")
    })
    TreinamentoDto toDto(Treinamento treinamento);

    @InheritInverseConfiguration
    Treinamento toEntity(TreinamentoDto treinamentoDto);
}
