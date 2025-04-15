package com.lopes.beckers_delivery_api.services;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired // injetor do repository na service
    UsuarioRepository usuarioRepository;

    @Transactional // caso algo dê errado, ele faz o rollback
    public UsuarioModel saveUsuarioService(DadosUsuarioRecordDto dadosUsuarioRecordDto) {
        if (usuarioRepository.findByEmail(dadosUsuarioRecordDto.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com o email " + dadosUsuarioRecordDto.email() + ".");
        }

        if (usuarioRepository.findByCpf(dadosUsuarioRecordDto.cpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com o CPF " + dadosUsuarioRecordDto.cpf() + ".");
        }

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(dadosUsuarioRecordDto, usuarioModel);

        return usuarioRepository.save(usuarioModel);
    }

    public List<UsuarioResponseDto> getAllUsuariosService(int page, int itens){
        List<UsuarioModel> usuarios = usuarioRepository
                .findAll(PageRequest.of(page, itens, Sort.by("nome").ascending()))
                .getContent();

        return usuarios.stream()
                .map(usuario -> {
                    EnderecoResponseDto enderecoResponseDto = new EnderecoResponseDto(
                            usuario.getLogradouro(),
                            usuario.getNumero(),
                            usuario.getComplemento(),
                            usuario.getBairro(),
                            usuario.getCep(),
                            usuario.getCidade(),
                            usuario.getEstado()
                    );
                    DadosUsuarioResponseDto dadosUsuarioResponseDto = new DadosUsuarioResponseDto(
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCpf(),
                        enderecoResponseDto);
                    return new UsuarioResponseDto(dadosUsuarioResponseDto);
                })
                .collect(Collectors.toList());
    }

    public Object getUsuarioByIdService(@PathVariable(value = "id") UUID id){
    Optional<UsuarioModel> usuario = usuarioRepository.findById(id);

    if(usuario.isPresent()){
        UsuarioModel usuarioModel = usuario.get();

        EnderecoResponseDto enderecoResponseDto = new EnderecoResponseDto(
                usuarioModel.getLogradouro(),
                usuarioModel.getNumero(),
                usuarioModel.getComplemento(),
                usuarioModel.getBairro(),
                usuarioModel.getCep(),
                usuarioModel.getCidade(),
                usuarioModel.getEstado());

        DadosUsuarioResponseDto dadosUsuarioResponseDto = new DadosUsuarioResponseDto(
                usuarioModel.getIdUsuario(),
                usuarioModel.getNome(),
                usuarioModel.getEmail(),
                usuarioModel.getCpf(),
                enderecoResponseDto);

        return new UsuarioResponseDto(dadosUsuarioResponseDto);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
    }
}
