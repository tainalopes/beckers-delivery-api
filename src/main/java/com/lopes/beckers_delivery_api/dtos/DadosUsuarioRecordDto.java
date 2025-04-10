package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosUsuarioRecordDto(@NotNull(message = "O campo nome não pode ser nulo.") String nome,
                                    @NotNull(message = "O campo email não pode ser nulo.") String email,
                                    @NotNull(message = "O campo senha não pode ser nulo.") String senha,
                                    @NotNull(message = "O campo cpf não pode ser nulo.") String cpf,
                                    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP informado deve estar no formato 00000-000.") String cep,
                                    String logradouro,
                                    String numero,
                                    String complemento,
                                    String bairro,
                                    String cidade,
                                    String estado) {
}
