package com.lopes.beckers_delivery_api.services;

import com.lopes.beckers_delivery_api.dtos.EnderecoRecordDto;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.EnderecoRepository;
import com.lopes.beckers_delivery_api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public List<EnderecoModel> saveEnderecoService(@PathVariable(value = "id") Long id,
                                                   List<EnderecoRecordDto> enderecoRecordDto){
        System.out.println("CHEGUEI NO SERVICE! ID = " + id);
        UsuarioModel usuarioModel = usuarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        List<EnderecoModel> enderecos = enderecoRecordDto
                .stream()
                .map(endereco -> {
                    EnderecoModel enderecoModel = new EnderecoModel();

            enderecoModel.setLogradouro(endereco.logradouro());
            enderecoModel.setNumero(endereco.numero());
            enderecoModel.setComplemento(endereco.complemento());
            enderecoModel.setLogradouro(endereco.logradouro());
            enderecoModel.setBairro(endereco.bairro());
            enderecoModel.setCep(endereco.cep());
            enderecoModel.setCidade(endereco.cidade());
            enderecoModel.setEstado(endereco.estado());
            enderecoModel.setUsuario(usuarioModel); // seta o usuário para manter o relacionamento
                    return enderecoModel;
        }).collect(Collectors.toList());

        return enderecoRepository.saveAll(enderecos);
    }
}
