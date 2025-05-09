package com.lopes.beckers_delivery_api.mappers;

import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoResponseDto toDto(EnderecoModel enderecoModel);

    List<EnderecoResponseDto> toDtoList(List<EnderecoModel> enderecos);
}
