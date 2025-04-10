package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.constraints.NotNull;

public record UsuarioRecordDto(@NotNull String nome,
                               @NotNull String email,
                               @NotNull String senha,
                               @NotNull String cpf,
                               String cep,
                               String logradouro,
                               String numero,
                               String complemento,
                               String bairro,
                               String cidade,
                               String estado) {
}
