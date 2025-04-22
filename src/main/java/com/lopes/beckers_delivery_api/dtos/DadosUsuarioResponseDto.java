package com.lopes.beckers_delivery_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record DadosUsuarioResponseDto(
        @JsonProperty("ID") UUID id,
        @JsonProperty("Nome") String nome,
        @JsonProperty("E-mail") String email,
        @JsonProperty("CPF") String cpf,
        @JsonProperty("Endereços") List<EnderecoResponseDto> enderecos
) {
}
