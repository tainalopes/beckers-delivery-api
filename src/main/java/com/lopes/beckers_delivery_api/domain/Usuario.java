package com.lopes.beckers_delivery_api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;

    private List<Endereco> endereco;
}
