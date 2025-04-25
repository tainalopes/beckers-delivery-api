package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoRecordDto(
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP informado deve estar no formato 00000-000.")
        @NotNull(message = "O campo cep não pode ser nulo.") String cep,
        @NotNull(message = "O campo logradouro não pode ser nulo.") String logradouro,
        @NotNull(message = "O campo numero não pode ser nulo.") String numero,
        String complemento,
        @NotNull(message = "O campo bairro não pode ser nulo.") String bairro,
        @NotNull(message = "O campo cidade não pode ser nulo.") String cidade,
        @NotNull(message = "O campo estado não pode ser nulo.") String estado
        ) {
}
