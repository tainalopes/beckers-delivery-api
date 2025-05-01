package com.lopes.beckers_delivery_api.mappers;

import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    EnderecoResponseDto toDto(EnderecoModel enderecoModel);

    List<EnderecoResponseDto> toDtoList(List<EnderecoModel> enderecos);
}
