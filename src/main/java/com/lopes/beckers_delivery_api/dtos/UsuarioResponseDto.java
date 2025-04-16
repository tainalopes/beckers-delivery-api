package com.lopes.beckers_delivery_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioResponseDto(
        @JsonProperty("Usuário") DadosUsuarioResponseDto usuario
) {
}
