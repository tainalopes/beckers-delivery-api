package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.constraints.Pattern;

public record EnderecoRecordDto(
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP informado deve estar no formato 00000-000.") String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
) {
}
