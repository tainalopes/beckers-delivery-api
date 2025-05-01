package com.lopes.beckers_delivery_api.models;

import com.lopes.beckers_delivery_api.enums.EstadosEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity // mostrando que essa classe é uma entidade JPA
@Table(name = "TB_ENDERECO") // nome da tabela
public class EnderecoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;

    // sempre que que tiver um campo que é um enum, é necessário essas 2 anotações.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadosEnum Estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
