package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.constraints.NotNull;

// necessário sempre adicionar o @Valid para os campos externos de cada dto
public record DadosUsuarioRecordDto(
        @NotNull(message = "O campo nome não pode ser nulo.") String nome,
        @NotNull(message = "O campo email não pode ser nulo.") String email,
        @NotNull(message = "O campo senha não pode ser nulo.") String senha,
        @NotNull(message = "O campo cpf não pode ser nulo.") String cpf
) {
}
