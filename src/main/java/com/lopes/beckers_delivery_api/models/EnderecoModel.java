package com.lopes.beckers_delivery_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity // mostrando que essa classe é uma entidade JPA
@Table(name = "TB_ENDERECO") // nome da tabela
public class EnderecoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
