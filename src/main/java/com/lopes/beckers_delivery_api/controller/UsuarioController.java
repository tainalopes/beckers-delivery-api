package com.lopes.beckers_delivery_api.controller;

import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.exceptions.ErrorResponse;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/usuario")
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid DadosUsuarioRecordDto dadosUsuarioRecordDto) {
        try {
            // verifica se já existe um usuário com o mesmo email
            if (usuarioRepository.findByEmail(dadosUsuarioRecordDto.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Já existe um usuário com o email " + dadosUsuarioRecordDto.email() + "."));
            }

            // verifica se já existe um usuário com o mesmo CPF
            if (usuarioRepository.findByCpf(dadosUsuarioRecordDto.cpf()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Já existe um usuário com o CPF " + dadosUsuarioRecordDto.cpf() + "."));
            }

            var usuarioModel = new UsuarioModel();
            BeanUtils.copyProperties(dadosUsuarioRecordDto, usuarioModel);

            // salva o novo usuário
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
        } catch (Exception e) {
            // captura qualquer exceção inesperada e retorna um erro 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/usuario")
    // lista todos os usuários cadastrados
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDto> usuarioResponseDtos = usuarios.stream()
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

        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDtos);
    }

    @GetMapping("/usuario/{id}")
    // lista um usuário buscando por id
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "id")UUID id){
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

            UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto(dadosUsuarioResponseDto);

            return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuário não encontrado!"));
        }
    }
}
