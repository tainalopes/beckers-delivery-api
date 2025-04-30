package com.lopes.beckers_delivery_api.services;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.exceptions.NotFoundException;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired // injetor do repository na service
    UsuarioRepository usuarioRepository;

    @Transactional // caso algo dê errado, ele faz o rollback
    public UsuarioModel saveUsuarioService(DadosUsuarioRecordDto dadosUsuarioRecordDto) {

        usuarioRepository.findByEmail(dadosUsuarioRecordDto.email())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Já existe um usuário com o email " + dadosUsuarioRecordDto.email() + ".");
                });

        usuarioRepository.findByCpf(dadosUsuarioRecordDto.cpf())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Já existe um usuário com o CPF " + dadosUsuarioRecordDto.cpf() + ".");
                });

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setNome(dadosUsuarioRecordDto.nome());
        usuarioModel.setEmail(dadosUsuarioRecordDto.email());
        usuarioModel.setSenha(dadosUsuarioRecordDto.senha());
        usuarioModel.setCpf(dadosUsuarioRecordDto.cpf());

        List<EnderecoModel> enderecos = dadosUsuarioRecordDto.enderecos().stream().map(endereco -> {
            EnderecoModel enderecoModel = new EnderecoModel();
            enderecoModel.setLogradouro(endereco.logradouro());
            enderecoModel.setNumero(endereco.numero());
            enderecoModel.setComplemento(endereco.complemento());
            enderecoModel.setBairro(endereco.bairro());
            enderecoModel.setCep(endereco.cep());
            enderecoModel.setCidade(endereco.cidade());
            enderecoModel.setEstado(endereco.estado());
            enderecoModel.setUsuario(usuarioModel); // seta o usuário para manter o relacionamento
            return enderecoModel;
        }).collect(Collectors.toList());

        usuarioModel.setEnderecos(enderecos);

        return usuarioRepository.save(usuarioModel);
    }

    public List<UsuarioResponseDto> getAllUsuariosService(int page, int itens) {
        List<UsuarioModel> usuarios = usuarioRepository
                .findAll(PageRequest.of(page, itens, Sort.by("nome").ascending()))
                .getContent();

        return usuarios.stream()
                .map(usuario -> {
                    List<EnderecoResponseDto> enderecoResponseDto = usuario.getEnderecos().stream()
                            .map(enderecoModel -> new EnderecoResponseDto(
                                    enderecoModel.getLogradouro(),
                                    enderecoModel.getNumero(),
                                    enderecoModel.getComplemento(),
                                    enderecoModel.getBairro(),
                                    enderecoModel.getCep(),
                                    enderecoModel.getCidade(),
                                    enderecoModel.getEstado()
                            )).collect(Collectors.toList());

                    DadosUsuarioResponseDto dadosUsuarioResponseDto = new DadosUsuarioResponseDto(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getEmail(),
                            usuario.getCpf(),
                            enderecoResponseDto);
                    return new UsuarioResponseDto(dadosUsuarioResponseDto);
                })
                .collect(Collectors.toList());
    }

    public Object getUsuarioByIdService(@PathVariable(value = "id") Long id) {
        UsuarioModel usuarioModel = findById(id);

        List<EnderecoResponseDto> enderecos = usuarioModel.getEnderecos().stream()
                .map(endereco -> new EnderecoResponseDto(
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getBairro(),
                        endereco.getCep(),
                        endereco.getCidade(),
                        endereco.getEstado()
                ))
                .collect(Collectors.toList());

        DadosUsuarioResponseDto dadosUsuario = new DadosUsuarioResponseDto(
                usuarioModel.getId(),
                usuarioModel.getNome(),
                usuarioModel.getEmail(),
                usuarioModel.getCpf(),
                enderecos
        );

        return new UsuarioResponseDto(dadosUsuario);
    }

    public UsuarioModel findById(Long id){
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }
}
