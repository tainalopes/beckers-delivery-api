package com.lopes.beckers_delivery_api.mappers;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DadosUsuarioMapper {

    DadosUsuarioResponseDto toDto(UsuarioModel usuarioModel);

}
