package com.lopes.beckers_delivery_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lopes.beckers_delivery_api.enums.EstadosEnum;

public record EnderecoResponseDto(
        @JsonProperty("Logradouro") String logradouro,
        @JsonProperty("Número") String numero,
        @JsonProperty("Complemento") String complemento,
        @JsonProperty("Bairro") String bairro,
        @JsonProperty("CEP") String cep,
        @JsonProperty("Cidade") String cidade,
        @JsonProperty("Estado")EstadosEnum estado
        ) {
}
