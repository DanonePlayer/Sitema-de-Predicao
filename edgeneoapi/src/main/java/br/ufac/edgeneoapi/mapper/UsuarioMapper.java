package br.ufac.edgeneoapi.mapper;

import br.ufac.edgeneoapi.dto.UsuarioDto;
import br.ufac.edgeneoapi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "coordenador", ignore = true)
    UsuarioDto toUsuarioDto(Usuario usuario);

    Usuario toUsuario(UsuarioDto usuarioDto);
}
