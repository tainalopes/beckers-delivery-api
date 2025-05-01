package com.lopes.beckers_delivery_api.services;

import com.lopes.beckers_delivery_api.dtos.EnderecoRecordDto;
import com.lopes.beckers_delivery_api.exceptions.NotFoundException;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public List<EnderecoModel> saveEnderecoService(Long id, List<EnderecoRecordDto> enderecoRecordDto) {
        UsuarioModel usuarioModel = usuarioService.findById(id);

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

    @Transactional
    public void deleteEnderecoService(Long idUsuario, Long idEndereco) {
        UsuarioModel usuarioModel = usuarioService.findById(idUsuario);
        EnderecoModel enderecoModel = findById(idEndereco);

        if (!enderecoModel.getUsuario().getId().equals(usuarioModel.getId())) {
            throw new IllegalArgumentException("Endereço não pertence ao usuário.");
        }

        enderecoRepository.delete(enderecoModel);
    }

    public EnderecoModel findById(Long id) {
        return enderecoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado!"));
    }
}
