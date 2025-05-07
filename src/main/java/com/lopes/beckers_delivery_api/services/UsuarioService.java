package com.lopes.beckers_delivery_api.services;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.exceptions.NotFoundException;
import com.lopes.beckers_delivery_api.mappers.DadosUsuarioMapper;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DadosUsuarioMapper dadosUsuarioMapper;

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

        return usuarioRepository.save(usuarioModel);
    }

    public List<UsuarioResponseDto> getAllUsuariosService(int page, int itens) {
        List<UsuarioModel> usuarios = usuarioRepository
                .findAll(PageRequest.of(page, itens, Sort.by("nome").ascending()))
                .getContent();

        return usuarios.stream()
                .map(usuario -> {

                    DadosUsuarioResponseDto dadosUsuarioResponseDto = dadosUsuarioMapper.toDto(usuario);

                    return new UsuarioResponseDto(dadosUsuarioResponseDto);
                })
                .collect(Collectors.toList());
    }

    public Object getUsuarioByIdService(@PathVariable(value = "id") Long id) {
        UsuarioModel usuarioModel = findById(id);

        DadosUsuarioResponseDto dadosUsuarioResponseDto = dadosUsuarioMapper.toDto(usuarioModel);

        return new UsuarioResponseDto(dadosUsuarioResponseDto);
    }

    public UsuarioModel findById(Long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }
}
