package com.lopes.beckers_delivery_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ListaEnderecosDto {
    @NotEmpty(message = "Deve conter ao menos um endereço.")
    @Valid
    private List<EnderecoRecordDto> enderecos;

    public List<EnderecoRecordDto> getEnderecos() {
        return enderecos;
    }
}
