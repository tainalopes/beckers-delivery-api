package com.lopes.beckers_delivery_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DadosUsuarioResponseDto(
        @JsonProperty("ID") Long id,
        @JsonProperty("Nome") String nome,
        @JsonProperty("E-mail") String email,
        @JsonProperty("CPF") String cpf,
        @JsonProperty("Endereços") List<EnderecoResponseDto> enderecos
) {
}
